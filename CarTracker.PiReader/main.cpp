#include <cstdio>
#include <iostream>

#include "main.h"
#include "ObdDevice.h"

int main(int argc, char* argv[])
{

	if (argc != 2)
	{
		std::cout << "Please specify the serial port to open.\n";
		return 2;
	}

	ObdDevice device(115200, argv[1]);

	int res = device.Connect();
	if (res < 0)
	{
		perror("connect to device");
		return -1;
	}
	printf("Opened and Initialized serial port \n");

	printf("Writing command \n");
	char readBuffer[1024];
	int read = device.SendCommand("010C", readBuffer, 1024);
	printf("Wrote: 01 0C. Read Bytes: %d\n", read);
	
	if (read > 0)
	{
		printf("Read: |%s|\n", readBuffer);
	}
	else
	{
		printf("Read nothing\n");
	}
	
	//disconnect from the device
	device.Disconnect();

}

