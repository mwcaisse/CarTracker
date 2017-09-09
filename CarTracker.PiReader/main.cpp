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
#include "OBDDevice.h"

int main(int argc, char* argv[])
{

	if (argc != 2)
	{
		std::cout << "Please specify the serial port to open.\n";
		return 2;
	}

	int fd = open(argv[1], O_RDWR | O_NOCTTY | O_NDELAY);

	OBDDevice device(115200, argv[1]);

	device.Connect();
	printf("Opened and Initialized serial port \n");

	printf("Writing command \n");
	char readBuffer[1024];
	int read = device.SendCommand("010C", readBuffer, 2014);
	printf("Wrote: 01 0C. Read Bytes: %d\n", read);
	
	if (read > 0)
	{
		printf("Read: %s\n", readBuffer);
	}
	else
	{
		printf("Read nothing\n");
	}
	
	//disconnect from the device
	device.Disconnect();

}

