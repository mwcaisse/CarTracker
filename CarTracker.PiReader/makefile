all: pireader

pireader: %.o
	g++ %.o -o pireader



%.o: %.cpp %.h
	g++ -c  %.cpp

clean:
	rm *.o
	rm pireader