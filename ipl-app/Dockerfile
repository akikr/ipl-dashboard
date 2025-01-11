# Set the base-image for build stage
FROM maven:3-eclipse-temurin-17 AS build
# Set up working directory
RUN mkdir -p /usr/app
COPY . /usr/app
WORKDIR /usr/app
# Build the application
RUN --mount=type=cache,target=/root/.m2 mvn clean package -B -DskipTests
# Build the application specific JRE
RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 17 \
    --print-module-deps \
    --class-path 'target/dependencies/*' \
    target/*.jar > modules.info
# Add 'jdk.management' module for JDK-specific management interfaces for the JVM while building application specific JRE
RUN jlink --add-modules $(cat modules.info) \
    --no-header-files \
    --no-man-pages \
    --output /app-jre

# Set the base-image for final stage
FROM ubuntu:22.04
# Set JAVA_HOME using application specific JRE from build-stage
ENV JAVA_HOME=/usr/lib/java/jre
ENV PATH=$JAVA_HOME/bin:$PATH
COPY --from=build /app-jre $JAVA_HOME
# Copy the artifact from build-stage
RUN mkdir -p /usr/webapp
COPY --from=build /usr/app/target/*.jar /usr/webapp/ipl-app.jar
WORKDIR /usr/webapp
# Define environment variables for java-options and application-arguments
ENV JAVA_OPTS=""
ENV APP_ARGS=""
# Build the application start-up script
RUN echo 'java ${JAVA_OPTS} -jar ipl-app.jar ${APP_ARGS}' > ./start-app.sh
RUN chmod +x ./start-app.sh
# Set a non-root user: Add a system group 'appgroup' and a system user 'appuser' in this group
RUN groupadd -r appgroup && useradd -r -m -s /bin/bash -G appgroup appuser
RUN chown -R appuser:appgroup /usr/webapp
USER appuser
# Expose the application port
EXPOSE 8081
# Run using start-up script
CMD ["sh", "-c", "./start-app.sh"]
