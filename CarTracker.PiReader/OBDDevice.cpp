#include "OBDDevice.h"
#include <cstdlib>
#include <cstring>
#include <fcntl.h>
#include <cstdio>
#include <termios.h>
#include <unistd.h>
#include <errno.h>


OBDDevice::OBDDevice(int baudRate, char* portName)
{
	this->baudRate = baudRate;
	this->fd = -1;

	//Copy port_name into class instance variable
	int portNameLen = strlen(portName);
	this->portName = static_cast<char*>(malloc(portNameLen + 1));
	memset(this->portName, '\0', portNameLen + 1);
	strncpy(this->portName, portName, portNameLen);

}

OBDDevice::~OBDDevice()
{
	free(portName);	
}

int OBDDevice::Connect()
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

int OBDDevice::Disconnect()
{
	close(this->fd);

	return 0;
}


int OBDDevice::InitializeSerialPort()
{
	struct termios tty;

	memset(&tty, 0, sizeof tty);

	if (tcgetattr(fd, &tty) != 0)
	{
		perror("tcgetattr");
		return -1;
	}

	//set the baud rate
	cfsetospeed(&tty, this->baudRate);
	cfsetispeed(&tty, this->baudRate);

	tty.c_cflag |= (CLOCAL | CREAD);
	tty.c_lflag &= !(ICANON | ECHO | ECHOE | ISIG);
	tty.c_oflag &= !(OPOST);
	tty.c_cc[VMIN] = 0; // read doesn't block
	tty.c_cc[VTIME] = 100; // 10 second time out

	if (tcsetattr(fd, TCSANOW, &tty) != 0)
	{
		perror("tcsetattr");
		return -1;
	}

	return 0;
}

int OBDDevice::InitializeOBDDevice()
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
	//resend for good measure
	this->SendBlindCommand("0100");

	return 0;
}

void OBDDevice::SendBlindCommand(const char* command)
{
	this->WriteCommand(command);
	sleep(1); // sleep for a second to wait for device to respond
	this->ReadToEnd();
}

int OBDDevice::SendCommand(const char* command, char* buffer, int bufferSize)
{
	this->WriteCommand(command);
	return this->ReadData(buffer, bufferSize);
}

int OBDDevice::WriteCommand(const char* command)
{
	char buf[1024];
	snprintf(buf, sizeof(buf), "%s%s\0", command, "\r");
	return write(this->fd, buf, strlen(buf));
}



int OBDDevice::ReadData(char* buffer, int bufferSize)
{
	int totalBytesRead = 0;
	int bytesRead = 0;

	memset(buffer, '\0', bufferSize);
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
	}
	//loop while we haven't read anything, we haven't seen the > char yet, and we haven't filled
	// our buffer
	//TODO: Might want to add a time out here, incase we never see a > character, we don't
	//		loop infitivly.
	while ((totalBytesRead == 0 || *(buffer - 1) != '>') && totalBytesRead < bufferSize);

	return totalBytesRead;
}

void OBDDevice::ReadToEnd()
{
	char buffer[4096];
	this->ReadData(buffer, 4096);
}




