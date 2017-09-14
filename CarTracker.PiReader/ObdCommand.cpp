#include "ObdCommand.h"
#include <cstring>

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
