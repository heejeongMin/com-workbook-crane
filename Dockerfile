FROM openjdk:11
RUN mkdir /opt/app && apt-get update && apt-get -y install netcat && apt-get clean
COPY build/libs/com-workbook-crane-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/com-workbook-crane-0.0.1-SNAPSHOT.jar"]

# 참고자료
# https://hub.docker.com/r/adoptopenjdk/openjdk11/
# https://steady-hello.tistory.com/110

# 명령어
# 프로젝트 루트에서
  # ./gradlew build
# Dockerfile 이 있는 위치에서 (crane 은 태킹임)
  # docker build -t crane .
# docker-compose 파일 없이 띄우기
  # docker run -p 8080:8080 -t crane

