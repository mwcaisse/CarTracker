#include "ObdCommand.h"
#include <cstring>
#include <cstdlib>

ObdCommand::ObdCommand(const char* command)
{
	this->command = command;
	memset(this->rawOutput, '\0', RAW_OUTPUT_LENGTH);
}


ObdCommand::~ObdCommand()
{
}

bool ObdCommand::HasData() const
{
	return this->rawOutputRead > 0 &&
		strncmp(NO_DATA_RESPONSE, rawOutput, RAW_OUTPUT_LENGTH) != 0 &&
		strncmp(INVALID_RESPONSE, rawOutput, RAW_OUTPUT_LENGTH) != 0;
}

int* ObdCommand::GetBytes(int* arrayLength) const
{
	int outputLength = strnlen(this->rawOutput, RAW_OUTPUT_LENGTH);
	if (outputLength % 2 != 0)
	{
		//output isn't divisible by 2, wierd stuff matey.
		*arrayLength = 0;
		return nullptr;
	}
	int* bytes = static_cast<int*>(malloc(sizeof(int) * outputLength / 2));
	for (int i = 0; i < outputLength; i += 2)
	{
		char byteSubstring[3];
		strncpy(byteSubstring, this->rawOutput + i, 2);
		*(bytes + i / 2) = strtol(byteSubstring, nullptr, 16);
	}

	*arrayLength = outputLength / 2;
	return bytes;
}
