# Training-Managing-Desktop-App-With-JavaFx-MySQL
![Project Logo](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/Logo.jpg)

## Project Description
  "Training Management System" is a comprehensive JavaFX application for efficient management of training programs. It offers three distinct roles: admin, trainer, and participant, allowing seamless coordination and collaboration. Powered by MySQL database, it facilitates robust data storage and retrieval. The application empowers administrators to organize courses, trainers to create and deliver engaging content, and participants to access and track their learning progress. With its intuitive interface and versatile features, the Training Management System simplifies the administration and enhances the learning experience in training environments.

### Project Architecture :
Before going into the application, we need to understand correctly the architecture that the developer adapted to build his project & what are the tools needed, which is in this case:
![Project Architecture](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/Architecture.png)

### Users & roles :

The application is designed for 3 types of users , and each of them has a specific roles:

![Users & Roles](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/Roles.png)

## Installation & Usage

### Prerequisites

[![Java Development Kit (JDK)](https://img.shields.io/badge/JDK-%3E%3D%20v11-orange)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

    Java Development Kit (JDK): The JDK is required to compile and run Java programs. It includes the Java compiler (javac), Java Virtual Machine (JVM), and other tools necessary for Java development.

[![Eclipse IDE](https://img.shields.io/badge/Eclipse%20IDE-2021--09-blue)](https://www.eclipse.org/downloads/)

    Integrated Development Environment (IDE): An IDE provides a comprehensive development environment with features such as code editing, debugging, and project management. Some popular choices for Java development with JavaFX like Eclipse.

[![JavaFX SDK](https://img.shields.io/badge/JavaFX%20SDK-OpenJFX%2016.0.2-orange)](https://openjfx.io/)

    JavaFX SDK: The JavaFX SDK contains the libraries, runtime environment, and tools required for developing JavaFX applications. It provides classes and APIs for creating user interfaces and multimedia-rich applications.

[![MySQL Database](https://img.shields.io/badge/MySQL%20Database-8.0-blue)](https://www.mysql.com/)

    MySQL Database: MySQL is an open-source relational database management system. You need to install and set up a MySQL server to store and manage your application's data.

[![MySQL Connector](https://img.shields.io/badge/MySQL%20Connector-8.0-blue)](https://dev.mysql.com/downloads/connector/j/)

    MySQL Connector/J: It is the JDBC (Java Database Connectivity) driver for MySQL. The Connector/J library enables Java programs to connect to and interact with the MySQL database.

[![GUI Designer Tool](https://img.shields.io/badge/GUI%20Designer%20Tool-Scene%20Builder-brightgreen)](https://gluonhq.com/products/scene-builder/)

    GUI Designer Tool: Some IDEs provide visual GUI designers that allow you to create JavaFX user interfaces using a drag-and-drop interface, simplifying the UI design process.

### Installation

 1- Install Java Development Kit (JDK): Download and install the latest version of JDK from the official Oracle website or through your package manager.

 2- Set up the IDE: Install an IDE of your choice, such as Eclipse, IntelliJ IDEA, or NetBeans. Configure the IDE by specifying the JDK installation directory.

 3- Set up MySQL: Install MySQL server on your machine and configure it. Create a new database and set up the necessary tables for your project.

 4- Download the project: clone it from this repository Git.

 5- Import the project: Open your IDE and import the project into your workspace. Depending on the IDE, you may need to select the appropriate project type (e.g., Maven, Gradle) and configure the project settings.

 6- Resolve dependencies: If the project uses a build automation tool like Maven or Gradle, the dependencies should be automatically resolved. Otherwise, you may need to manually import the required libraries and dependencies.

 7- Configure the database connection: Locate the configuration files or source code sections responsible for establishing a connection to the MySQL database. Update the connection details to match your MySQL server configuration.

 8- Build the project: Build the project using the IDE's build feature or the build automation tool (e.g., Maven or Gradle). This step compiles the code and resolves dependencies.

 9- Run the project: Once the project is built successfully, you can run it from within the IDE. Look for the main class or entry point of the application and execute it. Alternatively, you can generate an executable JAR file and run it from the command line using the java command.

 10- Test the application: Interact with the application to ensure it functions as expected. Perform various operations and verify that data is correctly stored and retrieved from the MySQL database.

### Usage
Let's have a look on the project with some screenshoots from inside :
At the begining, you will see the login page :
 . A common authentication interface for all types of users (Admin, Trainer, Participant) is offered, with redirection to the appropriate interface after authentication.
 . In case there is a new registration from a new trainer or participant, you can click on the buttons indicated.
 . The admin cannot be registered, he has his authentication data from the developer as soon as the application is launched.

![Login](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/Login.png)

And this is the trainer inscription interface :

![Trainer Inscription](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/TrainerInscription.png)

The admin have a statistique dashboard resuming the different activities in the training center:

![Admin Dash](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/AdminDash.png)

And he can also manage the trainers, participant and courses : 

![AdminCourses](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/AdminCourses.png)

The trainer can also manage the participant to his courses:

![Trainer Participant Managing](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/Trainerparticipantmanaging.png)

The participant can have dashboard to configure his courses: 

![Participant Courses](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/ParticipantCourses.png)

And any user can update his profile :

![Modify Info](https://github.com/SBJ2000/Training-Managing-Desktop-App-With-JavaFx-MySQL/blob/main/Images/ModifyInfo.png)

## Conclusion

In conclusion, the "Training Management System" is a powerful JavaFX application that provides efficient management of training programs. It offers a user-friendly interface and a range of features for administrators, trainers, and participants. The application utilizes MySQL database for secure and reliable data storage.

During the development of the project, several technical prerequisites need to be fulfilled. These include installing the Java Development Kit (JDK) for Java programming, setting up an Integrated Development Environment (IDE) such as Eclipse, acquiring the JavaFX SDK for building JavaFX applications, configuring a MySQL database, and installing the MySQL Connector/J for Java-MySQL connectivity. Additionally, a GUI Designer Tool like Scene Builder can be used to simplify the design of user interfaces.

To install and run the project, you need to follow a series of steps, including installing the necessary software components, importing the project into your IDE, configuring the database connection, building the project, and finally running and testing the application.

Once the application is up and running, users can benefit from its various functionalities. The admin can manage courses, trainers, and participants, while trainers can create and deliver content and manage participant enrollments. Participants, on the other hand, can access and track their courses and update their profiles as needed.

The project's architecture provides a clear understanding of its components and the tools used. The application leverages JavaFX and MySQL to create a robust and user-friendly solution for training management.

Overall, the "Training Management System" simplifies the administration and enhances the learning experience in training environments. It offers a comprehensive solution for organizing, delivering, and tracking training programs effectively.


