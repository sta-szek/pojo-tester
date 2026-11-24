# pojo-tester

pojo-tester checks the methods a plain Java object is expected to get right: `equals`, `hashCode`, `toString`, getters, setters and constructors. One assertion replaces the cases that only repeat the fields.

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

assertPojoMethodsFor(Order.class).areWellImplemented();
```

The library does not generate those methods and it does not require an annotation on the class under test. A failure means the implementation disagrees with the fields that were included.

This site describes `0.8.0-SNAPSHOT`, the line on `master`. The last release is `0.7.6`. What changed since then is in the [changelog](/changelog).

## Requirements

The bytecode is Java 8. That bytecode is tested on Java 8, 11, 17, 21 and 25. Gradle needs JDK 17 or newer to build the project itself. A test only needs the jar and Java 8.

## Installation

`0.8.0-SNAPSHOT` is on the Central Portal snapshot repository. `0.7.6` is on Maven Central and needs no extra repository.

### Gradle

```groovy
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

## What the default assertion runs

`areWellImplemented()` with no `testing(...)` call runs every tester:

| Tester | What it checks |
| --- | --- |
| `EQUALS` | Reflexive, `null`, a different type, and a change to each included field |
| `HASH_CODE` | Equal objects share a hash code. Unequal objects do not, unless that check is turned off |
| `TO_STRING` | The text changes when an included field changes, and stays stable when it does not |
| `GETTER` | The getter returns the value that was set on the field |
| `SETTER` | The setter writes the field. A setter may return the class under test |
| `CONSTRUCTOR` | A declared constructor can be called |

Records are not the target. A record already has these methods, and there is nothing useful to assert about a generated accessor.

The [guide](/guide) covers field selection, nested types, constructors, suppliers and the switches that change the default. The [Javadoc](/javadoc/) is generated from the same commit as this site.
