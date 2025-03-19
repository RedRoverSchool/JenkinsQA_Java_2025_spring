FROM maven:3.9.9-amazoncorretto-17

ENV JAVA_HOME=/usr/lib/jvm/java-17-amazon-corretto
ENV PATH=$JAVA_HOME/bin:$PATH

RUN java -version && mvn -version

WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY ./src ./src/

CMD ["mvn", "test"]