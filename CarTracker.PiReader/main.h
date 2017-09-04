#pragma once


/**
* Initializes the Serial Port. Sets the baud rate and configures
*	any needed settings
*
*	@param fd The file descriptor
*	@param speed The baud rate
*
*/
int initializeSerialPort(int fd, int speed)