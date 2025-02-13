# ParkingLR

ParkingLR is a JavaFX application that allows users to manage and visualize parking data. The application provides functionalities to import parking data from a CSV file, filter and sort the data, and display it on a map.

## Prerequisites

- Java 8 or higher
- Maven

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/KyozuFR/ParkingLR.git
    cd ParkingLR
    ```

2. Update the path of the JRE in the **pom.xml** file to match the JRE path on your system (line 136).

3. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Usage

1. Run the application:
    ```sh
    mvn javafx:run
    ```

2. Use the GUI to import a CSV file containing parking data.

3. Filter and sort the parking data using the provided text field and radio buttons.

4. View the parking data on the map and in the table.

## Dependencies

- JavaFX
- Maven Compiler Plugin
- Maven Assembly Plugin
- Launch4J Plugin

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.
