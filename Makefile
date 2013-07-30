BIN_DIR = bin/
SRC_DIR = src/


all: build
build:
	mkdir -p $(BIN_DIR)
	find $(SRC_DIR) -name "*.java" -exec javac -cp $(SRC_DIR) -d $(BIN_DIR) {} \;
clean:
	rm -rf $(BIN_DIR)
