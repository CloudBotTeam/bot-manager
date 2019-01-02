FROM maven:3.3.3

# 移动有问题！！！
ADD CloudBot-Common /tmp/build/CloudBot-Common
# 移动 settings
ADD settings.xml /root/.m2/

RUN cd /tmp/build/CloudBot-Common && mvn clean install

# 把父项目pom.xml 移动到 /tmp/build
ADD pom.xml /tmp/build/


#RUN cd /tmp/build && mvn -q dependency:resolve

ADD src /tmp/build/src
        #构建应用

RUN cd /tmp/build && mvn -q -DskipTests=true package \
        #拷贝编译结果到指定目录
        && ls && mv target/*.jar /app.jar \
        #清理编译痕迹
        && cd / && rm -rf /tmp/build

VOLUME /tmp
EXPOSE 8101
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar", "/app.jar"]