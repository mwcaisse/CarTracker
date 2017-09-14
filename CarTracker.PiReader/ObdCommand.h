#pragma once

#define RAW_OUTPUT_LENGTH 1024
#define NO_DATA_RESPONSE "NODATA"
#define INVALID_RESPONSE "?"

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

	/*
	 * Determines if the command has data
	 * 
	 * True if it has data, false otherwise
	 */
	bool HasData() const;

	/*
	 * Returns an array containing the byte responses for the command.
	 * 
	 * Each byte is converted to an integer
	 *
	 */
	int* GetBytes(int* arrayLength) const;
};

