# base build image
FROM maven:3.8.4-eclipse-temurin-17 as maven

# copy the pom.xml
COPY ./pom.xml ./pom.xml

# copy the source code
COPY ./src ./src

# build the project
RUN mvn package -DskipTests=true

# final base image
FROM eclipse-temurin:17.0.1_12-jdk

# copy the built artifact from the maven image
COPY --from=maven target/diet-logs-rev.jar diet-logs-rev.jar

# copy the script to wait for the database
COPY wait-for-it.sh wait-for-it.sh

# run permission for the script
RUN chmod +x wait-for-it.sh

# run the artifact after it waits for the database container
ENTRYPOINT ["./wait-for-it.sh", "diet-logs-rev-mariadb-container:3307", "--","java","-jar","diet-logs-rev.jar"]