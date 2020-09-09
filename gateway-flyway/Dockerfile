FROM openjdk:8-slim-stretch
ENV PKG_NAME flyway-1.0.0-SNAPSHOT-jar-with-dependencies.jar
ADD ${PKG_NAME} /${PKG_NAME}
CMD sh -c "exec java -jar /${PKG_NAME}"
