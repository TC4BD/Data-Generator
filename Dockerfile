FROM maven:3.6.3-openjdk-17 AS build
COPY ./pom.xml /app/pom.xml
WORKDIR /app
RUN mvn dependency:go-offline -B
COPY ./src /app/src

RUN mvn --show-version --update-snapshots --batch-mode clean package

FROM amazoncorretto:17

RUN mkdir /app
WORKDIR /app
RUN ls
COPY --from=build ./app/target/DataGenerator-*.jar /app
RUN cd /app
EXPOSE 8080
CMD ["java", "-jar", "DataGenerator-0.0.1-SNAPSHOT.jar"]