# Tạo một hình ảnh sử dụng OpenJDK 17
FROM adoptopenjdk:17-jdk-hotspot

# Thiết lập làm việc trong thư mục /app
WORKDIR /app

# Sao chép file dự án Spring Boot của bạn vào container
COPY . .

# Cài đặt Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Cài đặt các dependencies của Maven
RUN mvn dependency:resolve

# Xây dựng dự án và chạy Maven Spring Boot:build
RUN mvn spring-boot:build-image

# Bật port 9191 của Spring Boot trong container
EXPOSE 9191

# Chạy ứng dụng Spring Boot khi container được khởi chạy
CMD ["java", "-jar", "target/qlda-0.0.1-SNAPSHOT.jar"]
