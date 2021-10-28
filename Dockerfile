FROM docker.io/zhangyule1993/java-base:v1.0.0

# RUN npm install -g cnpm --registry=https://registry.npm.taobao.org
# 设置容器编码格式
ENV LC_ALL "zh_CN.UTF-8"

WORKDIR /home/app

ENV active $ACTIVE

COPY pom.xml .

COPY pom.xml .

RUN mvn package -DskipTests


CMD ["java", "-Dfile.encoding=utf-8", "-Dserver.port=8080", "-jar", "/home/app/target/weixin-app-0.0.1-SNAPSHOT.jar"]