# `POJO-TESTER` User Guide

# Introduction
This is a documentation for writing `pojo-tests` using [pojo-tester](https://github.com/obsidiandynamics/pojo-tester) library.

## What is pojo-tester? {#what-is-pojo-tester}
`POJO-TESTER` is a java testing library, which makes your `pojo-method` tests much easier. 
You can test your `pojo` against `equals`, `hashCode`, `toString`, `getters`, `setters` and even `constructors`.

`POJO-TESTER` automatically performs tests on basic `pojo-methods` so you don't have to copy-paste all dummy tests over and over.


## Supported Java versions {#supported-java}
`POJO-TESTER` requires Java 8. 

## Installation {#installation}
### Gradle
```
dependencies {
    testImplementation 'com.obsidiandynamics.pojotester:core:${latest-version}'
}
```

### Maven
```xml
<dependency>
      <groupId>com.obsidiandynamics.pojotester</groupId>
      <artifactId>core</artifactId>
      <version>${latest-version}</version>
      <type>pom</type>
</dependency>
```

## Documentation
