source := $(wildcard *.cpp)
objects := $(patsubst %.cpp, %.o, $(source))

all: pireader

pireader: $(objects)
	g++ $(objects) -o pireader

%.o: %.cpp
	g++ -c $< -o $@

clean:
	rm *.o
	rm pireader
