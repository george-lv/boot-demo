FROM java:7
ADD target/boot-demo.jar boot-demo.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","boot-demo.jar"]