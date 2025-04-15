FROM eclipse-temurin:21-jdk-alpine

# Cài đặt các gói cần thiết và cấu hình múi giờ
RUN apk --no-cache add fontconfig msttcorefonts-installer curl tzdata && \
    cp /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime && \
    echo "Asia/Ho_Chi_Minh" > /etc/timezone && \
    update-ms-fonts && \
    fc-cache -f -v

# Tạo thư mục chính cho ứng dụng
RUN mkdir -p /app && chmod -R 755 /app

VOLUME /app

# Sao chép file JAR vào container
COPY target/*.jar app.jar

# Mở cổng 8448
EXPOSE 1061

# Khởi chạy ứng dụng với profile 'prod'
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
