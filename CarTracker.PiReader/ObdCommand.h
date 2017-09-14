#pragma once

#define RAW_OUTPUT_LENGTH 1024

class ObdCommand
{

	

public:

	const char* command;
	char rawOutput[RAW_OUTPUT_LENGTH];
	int rawOutputRead;

	ObdCommand(const char* command);
	~ObdCommand();

	static int GetRawOutputLength()
	{
		return RAW_OUTPUT_LENGTH;
	}
};

