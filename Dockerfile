#指定基础镜像，在其上进行定制
FROM jdk:14

#维护者信息
MAINTAINER erwinynag

#运行时自动挂载为匿名卷，写入的信息都不会记录进容器存储层
VOLUME /tmp

#将maven打包好的jar文件复制到容器里
ADD target/document-0.0.1-SNAPSHOT.jar app.jar

#指定运行方式为bash，使得我们的jar包能够访问
#RUN新建立一层，在其上执行这些命令，执行结束后， commit 这一层的修改，构成新的镜像。
RUN bash -c "touch/app.jar"

#对外暴露的端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE
8080

ENTRYPOINT ["java","-jar","app.jar"]

