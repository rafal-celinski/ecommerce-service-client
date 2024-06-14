FROM eclipse-temurin:17 AS build
WORKDIR /api
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17
WORKDIR /api
COPY --from=build /api/target/*.jar api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]