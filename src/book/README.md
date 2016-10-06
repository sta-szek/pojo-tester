# `POJO-TESTER` User Guide

# Introduction
This is a documentation for writing `pojo-tests` using [pojo-tester](https://github.com/sta-szek/pojo-tester) library.

If you have any questions, we can [![Gitter](https://badges.gitter.im/pojo-tester/Lobby.svg)](https://gitter.im/pojo-tester/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Build status is provided by [![Build Status](https://travis-ci.org/sta-szek/pojo-tester.svg?branch=master)](https://travis-ci.org/sta-szek/pojo-tester)

Current coverage is [![codecov](https://codecov.io/gh/sta-szek/pojo-tester/branch/master/graph/badge.svg)](https://codecov.io/gh/sta-szek/pojo-tester)

Download latest version [![Download](https://api.bintray.com/packages/sta-szek/maven/pojo-tester/images/download.svg) ](https://bintray.com/sta-szek/maven/pojo-tester/_latestVersion)

Get automatic notifications about new `POJO-TESTER` versions
<a href='https://bintray.com/sta-szek/maven/pojo-tester?source=watch' alt='Get automatic notifications about new "pojo-tester" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>


## What is pojo-tester? {#what-is-pojo-tester}
`POJO-TESTER` is a java testing library, which makes your `pojo-tests` much easier. You can test your `pojo` against `equals`, `hashCode`, `toString`, `getters` and `setters`.


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
    testCompile 'pl.pojo:pojo-tester:0.4.0'
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
  <version>0.4.0</version>
  <type>pom</type>
</dependency>
```

## JavaDoc documentation {#javadoc}
Javadoc can be found [here](http://www.pojo.pl/javadoc/index.html).
