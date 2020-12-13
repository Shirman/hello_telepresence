## kubernetes本地开发和远程调试
    本项目展示了在kubernetes环境下如何进行本地调试和热部署。以下是完成本示例所需技术：

|技术|备注|
|---|---|
|Docker Desktop|提供docker，kubernetes服务|
|Minikube|快速创建一个单节点的kubernetes集群|
|Tegepresence|提供将本地服务代理到kubernetes服务|
|IDEA|Java开发工具|
|Java Jdk8|Java开发环境|
|Springboot|一个Java快速开发框架|

### 准备环境
##### Docker Desktop安装
   * 请访问[官网下载客户端](https://www.docker.com/products/docker-desktop)

    打开客户端启动docker。
##### Minikube安装
   * 建议安装[阿里云版本](https://github.com/AliyunContainerService/minikube/wiki)
```shell script
# 启动minikube,Docker驱动
minikube start --driver=docker 
```
##### Tegepresence安装
   * 请见[官网安装教程](https://www.telepresence.io/reference/install)
##### IDEA安装
   * 请访问[官网下载客户端](https://www.jetbrains.com/idea/)
   
##### 拉取Springboot示例代码
```shell script
git clone https://github.com/Shirman/hello_telepresence.git
```

### 开始体验本地调试
#### 解决minikube不能引用本地镜像问题
```shell script
# 执行以下命令，将minikube的docker主机重用于当前的bash会话
eval $(minikube docker-env)
```
#### 制作docker镜像
```shell script
#在本项目的根目录制作docker镜像
docker build -t 18868866621/hello-world .
```
#### 运行一个Kubernetes Deployment
```shell script
#部署Deployment资源
kubectl apply -f ./docs/hello_world.yaml
#查询pod状态
kubectl get pods|grep hello-world
```
#### 使用tegepresence代理pod
至此，我们已经模拟了一个springboot项目运行在kubernetes节点的环境
```shell script
docker pull maven:3.3-jdk-8
#使用telepresence，用本地服务代理kubernetes的指定deployment
telepresence --swap-deployment hello-world --docker-run --rm -v$(pwd):/build -v $HOME/.m2/repository:/m2 -p 8080:8080 -p 5005:5005 maven:3.3-jdk-8 mvn -Dmaven.repo.local=/m2 -f /build spring-boot:run -Drun.jvmArguments=--agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
```
#### 开启远程调试

#### 修改代码测试热部署
