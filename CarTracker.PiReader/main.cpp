#include <cstdio>
#include <iostream>
#include <list>

#include "main.h"
#include "ObdDevice.h"
#include "ObdCommand.h"
#include "ObdCommandTypes.h"
#include <cstring>

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
	

	ObdCommand command("010C");
	device.ExecuteCommand(&command);

	if (command.HasData()) {
		printf("Command Data: %s\n", command.rawOutput);
		int arrayLength = 0;
		int* commandBytes = command.GetBytes(&arrayLength);
		if (arrayLength != 4)
		{
			printf("Array Length was less than 3.. No data?\n");
		}
		else
		{
			printf("command bytes: ");
			for (int i=2; i < arrayLength;i++)
			{
				if (i > 2)
				{
					printf(", ");
				}
				printf("%d", commandBytes[i]);
			}
			printf("\n");

			int engineRpm = (256 * commandBytes[2] + commandBytes[4]) / 4;
			printf("Engine RPM: %d\n", engineRpm);
		}
	}

	ObdCommand commandModeOneAvailable("0100");
	device.ExecuteCommand(&commandModeOneAvailable);

	if (commandModeOneAvailable.HasData())
	{
		int arrayLength = 0;
		int* commandBytes = commandModeOneAvailable.GetBytes(&arrayLength);
		if (arrayLength != 6)
		{
			printf("Array Length wasn't 6, No Data?: Was: %d \n", arrayLength);
			printf("Outputlength: %d\n", strlen(commandModeOneAvailable.rawOutput));
			printf("Output: |%s|", commandModeOneAvailable.rawOutput);
		}
		else
		{
			int availableCommands = 0;
			for (int i=2; i < arrayLength; i ++)
			{
				availableCommands = availableCommands << 8;
				availableCommands += commandBytes[i];
			}

			std::list<ObdCommands> supportedCommands = {};

			printf("Available Commands: %d\n", availableCommands);

			if (availableCommands & 0x20000000)
			{
				//PID 03 is supported
				printf("PID 03, INTAKE_AIR_TEMPERATURE SUPPORTED \n");
			}

			if (availableCommands & 0x10000000)
			{
				//PID 04 is supported
				printf("PID 04, CALCULATED_ENGINE_LOAD SUPPORTED \n");
			}
			if (availableCommands & 0x8000000)
			{
				//PID 05 is supported
				printf("PID 05, ENGINE_COOLANT_TEMPERATURE SUPPORTED \n");
			}
			if (availableCommands & 0x400000)
			{
				//PID 0A is supported
				printf("PID 0A, FUEL_PRESSURE SUPPORTED \n");
			}
			if (availableCommands & 0x200000)
			{
				//PID 0B is supported
				printf("PID 0B, INTAKE_MANIFOLD_PRESSURE SUPPORTED \n");
			}
			if (availableCommands & 0x100000)
			{
				//PID 0C is supported
				printf("PID 0C, ENGINE_RPM SUPPORTED \n");
			}
			if (availableCommands & 0x80000)
			{
				//PID 0D is supported
				printf("PID 0D, VEHICLE_SPEED SUPPORTED \n");
			}
			if (availableCommands & 0x40000)
			{
				//PID 0E is supported
				printf("PID 0E, TIMING_ADVANCE SUPPORTED \n");
			}
			if (availableCommands & 0x20000)
			{
				//PID 0F is supported
				printf("PID 0F, INTAKE_AIR_TEMPERATURE SUPPORTED \n");
			}
		}
	}

	//disconnect from the device
	device.Disconnect();

	

}

