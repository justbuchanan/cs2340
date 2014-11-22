all:
	gradle build

run: all
	java -jar build/libs/proj.jar

clean:
	rm -rf build

