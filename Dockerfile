FROM amazoncorretto:17-al2-generic
    VOLUME /tmp
    COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar
    ENTRYPOINT [ "java", "-jar", "/app.jar" ]
