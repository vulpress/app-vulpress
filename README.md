# Vulpress

Where docs become blogs.

## Introduction

Vulpress is a straightforward blogging application, allowing content owners to upload articles in
various mimetypes and have them appear with a uniform style.

## Getting Started

### Prerequisites

Running the project requires the following to be installed:

- Java 17+
- Node 16+

### Building the application

1. Fork and clone the project
2. In the project route directory issue the command
   - On Linux/macOS:
     ```bash
     ./gradlew vulpress-app-server:build
     ```
   - On Windows:
     ```shell
     gradlew vulpress-app-server:build
     ```
3. The packaged JAR can be found in the `./app/build/libs` directory (starting from the project
   root).
4. In the above directory, you can start the application server with:
   ```bash
    java -jar -Dspring.profiles.active=bootstrap,h2 vulpress-app-server-x.x.x.jar
    ```
   Setting the `bootstrap` profile will drop the contents of the file system H2 database used for
   local development, and initialise its schema. After the first run, you can omit this profile to
   preserve the database contents between restarts.

## Contributing

All contributions are welcome! For details on how to contribute, please refer to our [Contribution 
Guidelines](./CONTRIBUTING.md)

## Live Demo

A live demo is available [here](https://aestallon.com), running the latest tagged version of the
application. Please be advised, that the demo instance is provided as is, without any warranties and
no privacy policy is provided. Be mindful of your choice of login credentials, if you register an
account on the demo. 

Post and profile data may be lost when the application is deployed.
