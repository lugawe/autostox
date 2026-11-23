# BUILD

FROM maven:3-eclipse-temurin-25-alpine AS build

WORKDIR /app-build

COPY pom.xml pom.xml
COPY autostox-core/pom.xml autostox-core/pom.xml
COPY autostox-server/pom.xml autostox-server/pom.xml

RUN --mount=type=cache,target=/root/.m2 mvn -B quarkus:go-offline

COPY autostox-core/src autostox-core/src
COPY autostox-server/src autostox-server/src

RUN --mount=type=cache,target=/root/.m2 mvn -B -DskipTests clean package

# RUNTIME

FROM eclipse-temurin:25-jre-alpine AS runtime

RUN addgroup -S quarkus && adduser -S -G quarkus quarkus

WORKDIR /app

COPY --from=build /app-build/autostox-server/target/quarkus-app /app

RUN chown -R quarkus:quarkus /app

USER quarkus

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]
