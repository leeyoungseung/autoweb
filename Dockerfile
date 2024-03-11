# 기반이 될 이미지 설정
FROM selenium/standalone-edge:latest

# 작업 디렉토리 설정
WORKDIR /app

# 호스트의 Gradle 프로젝트 복사
COPY . .

# Java환경변수
ENV JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
ENV PATH="${JAVA_HOME}:${PATH}"

# Gradle Wrapper에 실행 권한 부여
RUN sudo chmod +x gradlew

# Gradle 테스트 실행
#CMD ["./gradlew", "test", "--stacktrace"]