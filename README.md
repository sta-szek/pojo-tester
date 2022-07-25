POJO Tester
===
A Java POJO testing library. You can test objects against `equals`, `hashCode`, `toString`, getters, setters and constructors.

[![Maven release](https://img.shields.io/maven-metadata/v.svg?color=blue&label=maven-central&metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fcom%2Fobsidiandynamics%2Fpojotester%2Fcore%2Fmaven-metadata.xml)](https://mvnrepository.com/artifact/com.obsidiandynamics.pojotester)
[![Gradle build](https://github.com/obsidiandynamics/pojo-tester/actions/workflows/master.yml/badge.svg)](https://github.com/obsidiandynamics/pojo-tester/actions/workflows/master.yml)
[![codecov](https://codecov.io/gh/obsidiandynamics/pojo-tester/branch/master/graph/badge.svg)](https://codecov.io/gh/obsidiandynamics/pojo-tester)

This is a fork of the now defunct [pojo-tester](https://github.com/sta-szek/pojo-tester) project, by Piotr Joński.

# Installation
## Gradle
```
dependencies {
    testImplementation 'com.obsidiandynamics.pojotester:core:${latest-version}'
}
```

## Maven
```xml
<dependency>
      <groupId>com.obsidiandynamics.pojotester</groupId>
      <artifactId>core</artifactId>
      <version>${latest-version}</version>
      <type>pom</type>
</dependency>
```

# Documentation
* [Why Should I Use POJO-TESTER?](src/book/why-use/README.md)
    * [Be more productive](src/book/why-use/README.md#productivity)
    * [Improve your coverage](src/book/why-use/README.md#improve-coverage)
    * [Be resistant to bugs](src/book/why-use/README.md#resistant-bugs)
    * [Be resistant to changes](src/book/why-use/README.md#resistant-changes)
    * [Avoid boiler-plate tests](src/book/why-use/README.md#avoid-boiler-plate-code)
* [Writing Tests](src/book/writing-tests/README.md)
    * [Basic pojo test](src/book/writing-tests/README.md#basic-test)
    * [Choose kind of tests](src/book/writing-tests/README.md#choosing-testers)
    * [Set fields for testing](src/book/writing-tests/README.md#choosing-fields)
    * [Configure field value changer](src/book/writing-tests/README.md#configure-fvc)
    * [Create class using selected constructor](src/book/writing-tests/README.md#choose-constructor)
    * [Bulk pojos testing](src/book/writing-tests/README.md#bulk-testing)
    * [If testing time grows...](src/book/writing-tests/README.md#testing-time)
    * [Debugging](src/book/writing-tests/README.md#debugging)
* [Comparison](src/book/comparison/README.md)
    * [Other libraries](src/book/comparison/README.md#other-libs)
    * [Tests preconditions](src/book/comparison/README.md#precondtioions)
    * [Kind of tests](src/book/comparison/README.md#kind-of-tests)
    * [Coverage src/book/comparison](src/book/comparison/README.md#coverage-src/book/comparison)
    * [Conclusions](src/book/comparison/README.md#conclusions)
* [Release Notes](src/book/release-notes/README.md)
    * [Release 0.7.6](src/book/release-notes/README.md#release-076)
    * [Release 0.7.5](src/book/release-notes/README.md#release-075)
    * [Release 0.7.4](src/book/release-notes/README.md#release-074)
    * [Release 0.7.3](src/book/release-notes/README.md#release-073)
    * [Release 0.7.2](src/book/release-notes/README.md#release-072)
    * [Release 0.7.1](src/book/release-notes/README.md#release-071)
    * [Release 0.7.0](src/book/release-notes/README.md#release-070)
    * [Release 0.6.0](src/book/release-notes/README.md#release-060)
    * [Release 0.5.0](src/book/release-notes/README.md#release-050)
    * [Release 0.4.0](src/book/release-notes/README.md#release-040)
    * [Release 0.3.0](src/book/release-notes/README.md#release-030)
    * [Release 0.2.0](src/book/release-notes/README.md#release-020)
    * [Release 0.1.0](src/book/release-notes/README.md#release-010)
