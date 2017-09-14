#include <cstdio>
#include <iostream>

#include "main.h"
#include "ObdDevice.h"
#include "ObdCommand.h"

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

	const char* const commands[] = {
		"010F", // Air Intake Temperature
		"0146", // Ambient Air Temperature
		"0105", // Engineer Coolant Temperature
		"015C", // Oil Temp
		"010C", // Engine RPM
		"010D", // Speed
		"0110", // MAF
		"0111", // Throttle Position
		"0151", // Fuel Type
		"012F", // Fuel Leve
		"0902"  // VIN
	};

	//for fun execute a bunch of commands
	for (int i = 0; i < sizeof(commands) / sizeof(char*); i ++)
	{
		ObdCommand command(commands[i]);		
		device.ExecuteCommand(&command);

		printf("Executed command: %s \n", command.command);
		if (command.rawOutputRead > 0)
		{
			printf("Read: |%s|\n", command.rawOutput);
			printf("Has Data: %s\n", command.HasData() ? "Yes" : "No");
		}
		else
		{
			printf("Read nothing\n");
		}
	}
	
	//disconnect from the device
	device.Disconnect();

}

