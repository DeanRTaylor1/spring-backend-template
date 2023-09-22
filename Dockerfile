FROM maven:3.9.4-amazoncorretto-17 AS build

WORKDIR /app

COPY ./pom.xml ./

RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=build /app/target/api.jar ./

CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "./api.jar"]