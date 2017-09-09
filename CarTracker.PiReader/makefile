all: pireader

pireader: main.o OBDDevice.o
	g++ main.o OBDDevice.o -o pireader

main.o: main.cpp main.h
	g++ -c main.cpp

OBDDevice.o: OBDDevice.cpp OBDDevice.h
	g++ -c  OBDDevice.cpp

clean:
	rm *.o
	rm pireader