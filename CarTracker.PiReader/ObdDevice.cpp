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

#include "ObdCommand.h"


ObdDevice::ObdDevice(int baudRate, char* portName)
{
	this->baudRate = baudRate;
	this->fd = -1;

	//Copy port_name into class instance variable
	int portNameLen = strlen(portName);
	this->portName = static_cast<char*>(malloc(portNameLen + 1));
	memset(this->portName, '\0', portNameLen + 1);
	strncpy(this->portName, portName, portNameLen);

	retryAttempts = 1;
	timeout = 10;

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
	//echo off
	this->SendBlindCommand("ATE0");
	//twice just to be safe
	this->SendBlindCommand("ATE0");
	//disable linefeeds
	this->SendBlindCommand("ATL0");
	//don't insert spaces
	this->SendBlindCommand("ATS0");
	//timeout command, 62 * 4 milliseconds (62 is 3E)
	this->SendBlindCommand("AT ST 3E");
	//Setprotocol to 0
	this->SendBlindCommand("ATSP00");
	//Get Engine RPM, this will set the protocol, and clear "SEARCHING..." output
	//	from ATSP00 command, as it waits until next command to find protocol
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

void ObdDevice::ExecuteCommand(ObdCommand* command)
{
	this->WriteCommand(command->command);
	command->rawOutputRead = this->ReadData(command->rawOutput, command->GetRawOutputLength());
}

int ObdDevice::WriteCommand(const char* command)
{
	ssize_t bytesWritten = 0;
	ssize_t retry = 0;
	ssize_t index;
	int res = -1;

	struct timeval tv;
	tv.tv_sec = this->timeout;
	tv.tv_usec = 0;

	fd_set fdWrite;
	FD_ZERO(&fdWrite);
	FD_SET(this->fd, &fdWrite);

	while (retry < this->retryAttempts)
	{
		res = select(this->fd + 1, nullptr, &fdWrite, nullptr, &tv);

		if (res > 0)
		{
			tcflush(this->fd, TCIFLUSH);
			tcflush(this->fd, TCOFLUSH);

			int commandLength = strlen(command);

			//write the command + terminating characters
			bytesWritten += write(this->fd, command, sizeof(char) * commandLength);
			const char returnCharacter = 0x0D; // carriage return "\r"
			bytesWritten += write(this->fd, &returnCharacter, sizeof(char));

			if (bytesWritten == sizeof(char) * (commandLength + 1))
			{
				return bytesWritten;
			}
		}
		else if (res == 0)
		{
			retry++;
			printf("No data written\n");
		}
		else
		{
			retry++;
			perror("Write Command");
		}
	}

	return -1;
}



int ObdDevice::ReadData(char* buffer, int bufferSize)
{
	ssize_t totalBytesRead = 0;
	ssize_t index = 0;

	ssize_t retry = 0;

	int res = 0;

	struct timeval tv;
	tv.tv_sec = this->timeout;
	tv.tv_usec = 0;

	fd_set fdRead;
	FD_ZERO(&fdRead);
	FD_SET(this->fd, &fdRead);

	memset(buffer, '\0', bufferSize);

	while (retry < this->retryAttempts)
	{		
		res = select(this->fd + 1, &fdRead, nullptr, nullptr, &tv);

		if (res > 0)
		{
			totalBytesRead += read(this->fd, buffer + index, sizeof(char));
			if (totalBytesRead > 0 && buffer[index] == '>' )
			{				
				break;
			}
			if (buffer[index] != '>' && !isspace(buffer[index]))
			{
				index++ % (bufferSize - 1);
			}
		}
		else if (res == 0)
		{
			return 0; // no data to read
		}
		else
		{
			perror("Read Data");
		}
	}

	//always null terminate our string
	buffer[index] = '\0';

	StringTrim(buffer, bufferSize);

	printf("ObdDevice Read:|%s|\n", buffer);

	return totalBytesRead;
}

void ObdDevice::ReadToEnd()
{
	char buffer[4096];
	this->ReadData(buffer, 4096);
}

void ObdDevice::ReadAll()
{
	char buffer[2];
	memset(buffer, '\0', 2);

	struct timeval tv;
	tv.tv_sec = this->timeout;
	tv.tv_usec = 0;

	fd_set fdRead;
	FD_ZERO(&fdRead);
	FD_SET(this->fd, &fdRead);	

	//while there is data available read it into our buffer
	while(select(this->fd + 1, &fdRead, nullptr, nullptr, &tv) > 0)
	{
		read(this->fd, buffer, sizeof(char));
	}	
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




