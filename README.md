# connector-source-demo
connector数据源(source)实现demo

## connector插件开发
### 方式一（有connector主工程）
查看pom.xml可得知，此demo需要以module的形式，放到connector主工程当中。


1.在connector中创建一个module，并按规范进行命名。命名规范查看开发文档

2.在主pom中添加<module>sync-plugin-demo</module>

3.在sync-application模块的pom中，添加引用
```
<dependency>
    <groupId>com.idsmanager.sync</groupId>
    <artifactId>sync-plugin-demo</artifactId>
    <version>${plugin.version}</version>
</dependency>
```

### 方式二（无connector主工程）
当没有connector主工程的情况下，开发步骤如下：
1. 下载当前的工程
2. 在pom.xml引入开发所需要的sync-engine模块jar包和sync-api模块jar包
```
     <dependencies>
        <dependency>
            <groupId>com.idsmanager.micro</groupId>
            <artifactId>sync-engine</artifactId>
            <version>{version}</version>
        </dependency>
        <dependency>
            <groupId>com.idsmanager.micro</groupId>
            <artifactId>sync-api</artifactId>
            <version>{version}</version>
        </dependency>
    </dependencies>
```
3. 把工程打包成jar包，上传到connector项目的lib目录下，重启connector，然后进行调试。
4. 按照开发文档开发插件，完成后，把插件打包成jar包，上传到connector项目的lib目录下，然后重启connector

## 后续的如何进行开发，查看相关文档


开发文档：https://idp4.idsmanager.com/docs/%E5%B8%B8%E7%94%A8%E9%9B%86%E6%88%90%E6%96%B9%E6%A1%88/Connector%E8%87%AA%E5%AE%9A%E4%B9%89%E6%BA%90%E6%88%96%E7%9B%AE%E6%A0%87.html#id2
