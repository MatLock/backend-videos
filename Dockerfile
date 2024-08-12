FROM openjdk:17
RUN mkdir -p /var/www/app
WORKDIR /var/www/app
ADD target/pi-export-service.jar .
CMD ["java","-Dspring.profiles.active=production","-jar", "backend-videos.jar"]
EXPOSE 8080