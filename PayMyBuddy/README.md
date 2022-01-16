# PayMyBoddy

## Description
![homepage](https://github.com/Am-BOUK/paymybuddy/blob/main/PayMyBuddy/images/homepage.png)

Pay my Buddy is an app that allows clients to transfer money to manage their finances or pay their connections (friends)

### UML Diagram
![diagrammeUML](https://github.com/Am-BOUK/paymybuddy/blob/main/PayMyBuddy/images/P6_02_diagrammeUML.png)

### Physical data model
![Physical data model](https://github.com/Am-BOUK/paymybuddy/blob/main/PayMyBuddy/images/P6_03_Modèle_PhysiqueDonnées.png)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.


### Prerequisites

What things you need to install the software and how to install them

Java 1.8

Maven 3.6.3

MySQL 5.7.31

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3. Install MySQL:

https://dev.mysql.com/downloads/installer/


### Clone repo
git clone https://github.com/Am-BOUK/paymybuddy.git


### Running App

* SQL scripts is present under the resources folder in the code base :
  https://github.com/Am-BOUK/paymybuddy/blob/main/PayMyBuddy/src/main/resources/date.sql

* To have the jar file in the target folder :
	-mvn clean package

* launch the application with the java -jar "path to the .jar" command
	-java -jar "path to the .jar"

### Testing

The app has unit tests written. The existing tests need to be triggered from maven-surefire plugin while we try to generate the final executable jar file. 

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command:

JUNIT Test
- mvn test


Reports:
Surefire report:
- mvn site

Jacoco report:
- mvn jacoco:report


### Access to the application
Open your browser, go to http://localhost:8080