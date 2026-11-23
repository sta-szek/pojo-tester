# `POJO-TESTER` User Guide

# Introduction
This is a documentation for writing `pojo-tests` using [pojo-tester](https://github.com/sta-szek/pojo-tester) library.

The next version is `0.8.0-SNAPSHOT`. The last release on Maven Central is `0.7.6`.

## What is pojo-tester? {#what-is-pojo-tester}
`POJO-TESTER` is a java testing library, which makes your `pojo-method` tests much easier. 
You can test your `pojo` against `equals`, `hashCode`, `toString`, `getters`, `setters` and even `constructors`.

`POJO-TESTER` automatically performs tests on basic `pojo-methods` so you don't have to copy-paste all dummy tests over and over.


## Supported Java versions {#supported-java}
`POJO-TESTER` requires Java 8. 

## Installation {#installation}
`POJO-TESTER` is `pl.pojo:pojo-tester`. Use `0.8.0-SNAPSHOT` for the version currently on `master`. Use `0.7.6` for the last release.

### Gradle
```
repositories {
    mavenCentral()
    maven {
        url = 'https://central.sonatype.com/repository/maven-snapshots/'
        content { includeModule('pl.pojo', 'pojo-tester') }
    }
}

dependencies {
    testImplementation 'pl.pojo:pojo-tester:0.8.0-SNAPSHOT'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>central-snapshots</id>
        <url>https://central.sonatype.com/repository/maven-snapshots/</url>
        <releases><enabled>false</enabled></releases>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>pl.pojo</groupId>
    <artifactId>pojo-tester</artifactId>
    <version>0.8.0-SNAPSHOT</version>
    <scope>test</scope>
</dependency>
```

## JavaDoc documentation {#javadoc}
Javadoc can be found [here](http://www.pojo.pl/javadoc/index.html).
