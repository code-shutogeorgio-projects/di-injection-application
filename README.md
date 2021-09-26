## ♣️  Minimal DI Application
Demonstrate Dependency Injection inspired by Spring Framework

### ✨ Setup
**Java 11**
```bash
java --version                                                       
openjdk 11.0.7 2020-04-14
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.7+10)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.7+10, mixed mode)
```

**Maven**
```bash
mvn -v    
Apache Maven 3.8.2 (ea98e05a04480131370aa0c110b8c54cf726c06f)
Maven home: /usr/local/Cellar/maven/3.8.2/libexec
Java version: 11.0.7, vendor: AdoptOpenJDK, runtime: /Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
```

### ⭐️ Run Configuration
#### ⚒ Build   
```bash
mvn clean package
```

#### 🏃‍♂ Run
```bash
mvn exec:java -Dexec.mainClass=tech.uwaas.peaceofinjector.Application
```



