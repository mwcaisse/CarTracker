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
