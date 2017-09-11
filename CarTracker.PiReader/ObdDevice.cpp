#include "ObdDevice.h"
#include <cstdlib>
#include <cstring>
#include <fcntl.h>
#include <cstdio>
#include <termios.h>
#include <unistd.h>
#include <errno.h>
#include <ctime>
#include <cctype>


ObdDevice::ObdDevice(int baudRate, char* portName)
{
	this->baudRate = baudRate;
	this->fd = -1;

	//Copy port_name into class instance variable
	int portNameLen = strlen(portName);
	this->portName = static_cast<char*>(malloc(portNameLen + 1));
	memset(this->portName, '\0', portNameLen + 1);
	strncpy(this->portName, portName, portNameLen);

}

ObdDevice::~ObdDevice()
{
	free(portName);	
}

int ObdDevice::Connect()
{
	this->fd = open(this->portName, O_RDWR | O_NOCTTY | O_NDELAY);

	if (fd < 0)
	{
		return fd;
	}
	if(this->InitializeSerialPort() < 0) 
	{
		return -1;
	}
	if (this->InitializeOBDDevice() < 0)
	{
		return -1;
	}
	return 0;
}

int ObdDevice::Disconnect()
{
	close(this->fd);

	return 0;
}


int ObdDevice::InitializeSerialPort()
{
	struct termios tty;

	memset(&tty, 0, sizeof tty);

	if (tcgetattr(fd, &tty) != 0)
	{
		perror("tcgetattr");
		return -1;
	}

	tty.c_cflag |= (CLOCAL | CREAD);

	tty.c_lflag &= ~(ICANON | ECHO | ECHOE | ECHOK | ECHONL | ISIG | IEXTEN);
	tty.c_lflag &= ~(ECHOCTL);
	tty.c_lflag &= ~(ECHOKE);

	tty.c_oflag &= ~(OPOST);

	tty.c_iflag &= ~(INLCR | IGNCR | ICRNL | IGNBRK);
	tty.c_iflag &= ~(IUCLC);
	tty.c_iflag &= ~(PARMRK);

	tty.c_cflag &= ~CSIZE;
	tty.c_cflag |= CS8;

	tty.c_cflag &= ~(CSTOPB);
	tty.c_iflag &= ~(INPCK | ISTRIP);
	tty.c_cflag &= ~(PARENB | PARODD);
	tty.c_iflag &= ~(IXON | IXOFF | IXANY);
	tty.c_cflag &= ~(CRTSCTS);

	tty.c_cc[VMIN] = 0;
	tty.c_cc[VTIME] = 0;

	//set the baud rate
	cfsetospeed(&tty, this->baudRate);
	cfsetispeed(&tty, this->baudRate);
	

	if (tcsetattr(fd, TCSANOW, &tty) != 0)
	{
		perror("tcsetattr");
		return -1;
	}

	return 0;
}

int ObdDevice::InitializeOBDDevice()
{
	//reset device
	this->SendBlindCommand("ATZ");
	//clear
	this->SendBlindCommand("0100");
	//echo off
	this->SendBlindCommand("ATE0");
	//disable linefeeds
	this->SendBlindCommand("ATL0");
	//don't insert spaces
	this->SendBlindCommand("ATS0");
	//Setprotocol to 0
	this->SendBlindCommand("ATSP00");
	//Get Engine RPM just for fun
	this->SendBlindCommand("010C");

	return 0;
}

void ObdDevice::SendBlindCommand(const char* command)
{
	this->WriteCommand(command);
	sleep(1); // sleep for a second to wait for device to respond
	this->ReadToEnd();
}

int ObdDevice::SendCommand(const char* command, char* buffer, int bufferSize)
{
	this->WriteCommand(command);
	return this->ReadData(buffer, bufferSize);
}

int ObdDevice::WriteCommand(const char* command)
{
	char buf[1024];
	snprintf(buf, sizeof(buf), "%s%s", command, "\r\n");
	return write(this->fd, buf, strlen(buf));
}



int ObdDevice::ReadData(char* buffer, int bufferSize)
{
	int totalBytesRead = 0;
	int bytesRead = 0;
	char* bufferStart = buffer;

	memset(buffer, '\0', bufferSize);

	time_t startTime = time(nullptr);

	do
	{
		bytesRead = read(this->fd, buffer, bufferSize - totalBytesRead);
		if (bytesRead == -1 && errno != EAGAIN)
		{
			perror("readSerialData");
		}
		if (bytesRead != -1)
		{
			totalBytesRead += bytesRead;
			buffer += bytesRead; // advance the head of our buffer	
		}
		
		//check if we have timed out
		time_t currentTime = time(nullptr);
		if (currentTime - startTime > 30)
		{
			printf("Read timed out!\n");
			return -1; // timed out
		}
	}
	//loop while we haven't read anything, we haven't seen the > char yet, and we haven't filled
	// our buffer
	while ((totalBytesRead == 0 || *(buffer - 1) != '>') && totalBytesRead < bufferSize);
	
	//if buffer -1 equals the ending char, replace it with null byte
	if ( *(buffer -1) == '>')
	{
		*(buffer - 1) = '\0'; 
	}

	StringTrim(bufferStart, bufferSize);

	printf("ObdDevice Read:|%s|\n", bufferStart);

	return totalBytesRead;
}

void ObdDevice::ReadToEnd()
{
	char buffer[4096];
	this->ReadData(buffer, 4096);
}

void ObdDevice::StringTrim(char* str, int maxlen)
{
	//if string is empty return
	if (*(str) == '\0')
	{
		return;
	}

	char* start = str;
	char* end;

	while (isspace(*start) && start - str < maxlen)
	{
		start++;
	}

	//the string was all space, set first character to string terminator and return
	if (start - str == maxlen)
	{
		*str = '\0';
		return;
	}

	end = str + strnlen(str, maxlen) - 1;
	if (end == str + maxlen)
	{
		return; // we overflowed, can't trim right
	}

	while (end > start && isspace(*end))
	{
		end--;
	}

	while (start <= end)
	{
		*str = *start;
		str++;
		start++;
	}
	*str = '\0';	
}




