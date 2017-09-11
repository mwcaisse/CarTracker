#pragma once
class ObdDevice
{

	int baudRate;
	char* portName;

	int fd;

	/*
	 * Initializes the connection to the serial port.
	 * 
	 *	Sets the Baud Rate and read flags
	 */
	int InitializeSerialPort();

	/*
	 * Sets up the OBDDevice for reading. Clears any previous settings
	 *	and sets the settings expected for reading.
	 */
	int InitializeOBDDevice();

	/*
	 * Reads all of the output of the previously executed command and discards it. Clearing
	 *	the buffer for the next command.
	 */
	void ReadToEnd();

	/* Reads all of the output of the previously executed command into the given buffer
	 * 
	 * @return The number of bytes read
	 */

	int ReadData(char* buffer, int bufferSize);

	/*
	 * Writes the given command to the OBDDevice
	 * 
	 * @param command cstring containing the command to send
	 * @return the number of bytes written
	 */
	int WriteCommand(const char* command);	

public:
	ObdDevice(int baudRate, char* portName);
	~ObdDevice();

	/**
	 * Opens the serial port and initilizes the connection to the OBD Device
	 * 
	 * @return 0 if successful, -1 if not. ERR_NO will be set to the error the occured
	 *		while opening the device.
	 */
	int Connect();

	/*
	 * Closes the serial port connection
	 */
	int Disconnect();

	/*
	 * Sends a command to the OBD Device and ignores the output
	 * 
	 * @param command cstring containing the command to send
	 */
	void SendBlindCommand(const char* command);

	/*
	 * Sends a command to the OBD Device and reads the output into the given buffer
	 * 
	 * @param command cstring containing the command to send
	 * @param buffer The buffer to write the output to
	 * @param bufferSize the size of the buffer
	 * @return The number of bytes read
	 */
	int SendCommand(const char* command, char* buffer, int bufferSize);

	/*
	* Trims any leading and/or training white space in the given string
	*
	* @param str cstring to trim
	* @param len the max length of the string
	*/
	static void StringTrim(char* str, int maxlen);

};

