all: pireader

pireader: main.o
	g++ main.o -o pireader

main.o: main.cpp main.h
	g++ -c main.cpp

clean:
	rm *.o
	rm pireader