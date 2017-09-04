#include <cstdio>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>
#include <cstring>
#include <cstdlib>
#include <iostream>
#include <termios.h>

#include "main.h"

int main(int argc, char* argv[])
{

	if (argc != 2)
	{
		std::cout << "Please specify the serial port to open.\n";
		return 2;
	}

	int fd = open(argv[1], O_RDWR | O_NOCTTY | O_NDELAY);

	if (fd < 0)
	{
		printf("Failed to open serial port: %s error: %s\n", argv[1], strerror(errno));
		return 0;
	}

	printf("Opened serial port \n");

	initializeSerialPort(fd, 115200);

	printf("Initialized serial port \n");

	//write the OBD command to get engine RPMs
	ssize_t written = write(fd, "010C\r", 6);

	printf("Wrote: 01 0C Bytes: %d\n", written);

	char* readBuf = (char*)(malloc(sizeof(char) * 1024));

	printf("About to read a byte \n");
	int res = readSerialData(fd, readBuf, 1024);

	printf("Read Bytes: %d \n", res);
	if (res > 0)
	{
		printf("Read: %s\n", readBuf);
	}
	else
	{
		printf("Read nothing");
	}	

	free(readBuf);

	// close the serial port after we are done
	close(fd);
}

int initializeSerialPort(int fd, int speed)
{
	struct termios tty;
	memset(&tty, 0, sizeof tty);

	if (tcgetattr(fd, &tty) != 0)
	{
		perror("tcgetattr");
		return -1;
	}	

	//set the baud rate
	cfsetospeed(&tty, speed);
	cfsetispeed(&tty, speed);

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

int readSerialData(int fd, char* buf, int bufSize)
{
	int totalBytesRead = 0;
	int bytesRead = 0;
	memset(buf, '\0', bufSize);

	do
	{
		bytesRead = read(fd, buf, bufSize - totalBytesRead);
		if (bytesRead == -1 && errno != EAGAIN)
		{
			perror("readSerialData");
		}
		if (bytesRead != 1)
		{
			totalBytesRead += bytesRead;
			buf += bytesRead; // advance the head of our buffer
		}
	} 
	//loop while we haven't read anything, we haven't seen the > char yet, and we haven't filled
	// our buffer
	while ((totalBytesRead == 0 || *(buf-1) != '>') && totalBytesRead < bufSize);

	return totalBytesRead;
}
