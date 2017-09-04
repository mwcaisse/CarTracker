all: pireader

pireader: main.o
	g++ main.o -o pireader

main.o:
	g++ -c main.cpp

clean:
	rm *.o
	rm pireader