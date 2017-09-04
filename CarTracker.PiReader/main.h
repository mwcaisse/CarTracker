#pragma once


/**
* Initializes the Serial Port. Sets the baud rate and configures
*	any needed settings
*
*	@param fd The file descriptor
*	@param speed The baud rate
*
*/
int initializeSerialPort(int fd, int speed);

/**
 * Reads data from the serial port, until the > is encountered or buffer fills up
 * 
 * @param fd The file descriptor
 * @param buf the buffer
 * @param bufSize the size of the buffer
 */
int readSerialData(int fd, char* buf, int bufSize);