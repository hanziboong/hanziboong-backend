# 🔹 1단계: 빌드용 이미지 (경량 Alpine 기반 Gradle + JDK 17)
FROM gradle:8.4.0-jdk17-alpine AS builder

# 작업 디렉토리 설정
WORKDIR /app

# 변경 가능성이 적은 파일부터 복사해서 Docker Layer 캐시 활용
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# 의존성 캐싱 (속도 개선)
RUN ./gradlew build -x test --no-daemon || return 0

# 나중에 소스코드 복사 (변경 자주됨)
COPY src /app/src

# 전체 빌드 수행 (테스트 생략, CI에서 별도로 테스트하는 경우)
RUN ./gradlew clean build -x test --no-daemon

---

# 🔹 2단계: 실행용 이미지 (경량 JRE)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 실행 파일 복사 (파일명이 정해지지 않았으므로 와일드카드 사용)
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출 (Spring Boot 기본 포트)
EXPOSE 8080

# Timezone 설정 + 프로파일 설정
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "/app/app.jar", "--spring.profiles.active=prod"]