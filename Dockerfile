FROM navikt/java:11

COPY pdl-fullmakt-app/target/app-exec.jar /app/app.jar

ENV JAVA_OPTS="-Xmx1024m \
               -Djava.security.egd=file:/dev/./urandom"