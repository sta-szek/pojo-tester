# `POJO-TESTER` User Guide

# Introduction
This is a documentation for writing `pojo-tests` using [pojo-tester](https://github.com/sta-szek/pojo-tester) library.

If you have any questions, we can chat on [![Rocket.Chat](https://demo.rocket.chat/images/join-chat.svg)](http://chat.pojo.pl/channel/pojo-tester)

Build status is provided by [![Build Status](http://ci.pojo.pl/buildStatus/icon?job=pojo-tester/master)](http://ci.pojo.pl/job/pojo-tester/job/master/)

Project quality [![Codacy Badge](https://api.codacy.com/project/badge/Grade/f20e4ae366964fe4864179d26ed392c4)](https://www.codacy.com/app/sta-szek/pojo-tester?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sta-szek/pojo-tester&amp;utm_campaign=Badge_Grade)

Current coverage is [![codecov](https://codecov.io/gh/sta-szek/pojo-tester/branch/master/graph/badge.svg)](https://codecov.io/gh/sta-szek/pojo-tester)

Download latest version [![Download](https://api.bintray.com/packages/sta-szek/maven/pojo-tester/images/download.svg) ](https://bintray.com/sta-szek/maven/pojo-tester/_latestVersion)

Get automatic notifications about new `POJO-TESTER` versions
<a href='https://bintray.com/sta-szek/maven/pojo-tester?source=watch' alt='Get automatic notifications about new "pojo-tester" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>


## What is pojo-tester? {#what-is-pojo-tester}
`POJO-TESTER` is a java testing library, which makes your `pojo-method` tests much easier. 
You can test your `pojo` against `equals`, `hashCode`, `toString`, `getters`, `setters` and even `constructors`.

`POJO-TESTER` automatically performs tests on basic `pojo-methods` so you don't have to copy-paste all dummy tests over and over.


## Supported Java versions {#supported-java}
`POJO-TESTER` requires Java 8. 

## Installation {#installation}
`POJO-TESTER` library can be found on `jCenter` repository.

### Gradle
```
repositories {
    jcenter()
}

dependencies {
    testCompile 'pl.pojo:pojo-tester:${latest-version}'
}
```

### Maven
```xm
<repositories>
<repository>
  <id>jcenter</id>
  <url>http://jcenter.bintray.com/</url>
</repository>
</repositories>
  
<dependency>
  <groupId>pl.pojo</groupId>
  <artifactId>pojo-tester</artifactId>
  <version>${latest-version}</version>
  <type>pom</type>
</dependency>
```

## JavaDoc documentation {#javadoc}
Javadoc can be found [here](http://www.pojo.pl/javadoc/index.html).
