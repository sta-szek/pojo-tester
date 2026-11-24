# pojo-tester

pojo-tester checks the methods a plain Java object is expected to get right: `equals`, `hashCode`, `toString`, getters, setters and constructors. One assertion replaces the hand-written cases that only repeat the fields.

The project stopped in 2020, at release `0.7.6`. Work resumed in November 2026 on the same coordinates and the same license. The bytecode is still Java 8, and that bytecode is tested on Java 8, 11, 17, 21 and 25.

## Use it

The development line is `0.8.0-SNAPSHOT`. It is published to the Central Portal snapshot repository. `0.7.6` is the last release and comes from Maven Central with no extra repository.

### Gradle

```gradle
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

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

assertPojoMethodsFor(Order.class).areWellImplemented();
```

The user guide is [docs/](docs/index.md). The [changelog](CHANGELOG.md) is the same text the site publishes. [pojo.pl](https://www.pojo.pl/) is rebuilt from `master` by the Docs workflow, and again after a release.

## What `0.8.0-SNAPSHOT` accepts

Release `0.7.6` does not include this.

- A final field is copied, not given a different value. Call `changingFinalFields()` to opt in. On Java 12 and newer that write goes through `VarHandle`.
- `create(Class, Supplier)` supplies the instance when the class has no usable constructor.
- A setter may return the class under test (`return this`). A method that returns `Object` is not a setter.
- JavaBeans spelling is kept when the second letter is already uppercase: `xCoordinate` matches `getxCoordinate`, and `URL` matches `getURL`.
- An enum is not tested for setters. A setter of an enum-typed field on an ordinary class still is.
- A package scan loads only `*.class` files.
- `toString` may wrap a `String` or `char` in single quotes. An `Object[]` printed with `Arrays.toString` is accepted.
- Getters and setters are checked with a distinct value in every field.
- `equals` is also checked with `null` in each included field that is not primitive and not `final`.

## Build

`./gradlew build` compiles with `--release 8`, runs the unit tests and the integration tests, and builds the library, sources and Javadoc jars. Gradle needs JDK 17 or newer. The produced bytecode still runs on Java 8.

```bash
./gradlew build
./gradlew build -PtestJavaVersion=21
./gradlew pitest -PtestJavaVersion=21
```

`-PtestJavaVersion` accepts `8`, `11`, `17`, `21` or `25`. The default is `8`. On JDK 16 and newer the test task opens the `java.base` packages the library still reflects into.

| Task | What it runs |
| --- | --- |
| `build` | compile, unit tests, integration tests, jars |
| `test` | unit tests |
| `integrationTest` | integration tests |
| `pitest` | mutation tests of `pl.pojo.tester`, threshold 80% |
| `spotlessCheck` | formatting of files changed against `origin/master` |
| `spotbugsMain` | static analysis, report in `build/reports/spotbugs` |
| `jacocoTestReport` | merged unit and integration coverage |

CI runs `build` on Java 8, 11, 17, 21 and 25. Spotless, SpotBugs and PIT run on Java 21. Every job uses `ubuntu-latest`, and Gradle restores its dependency and build caches.

## Publishing

[Release](.github/workflows/release.yml) publishes to the [Central Portal](https://central.sonatype.com/) after CI is green on `master`. A tag does not publish anything. Bintray and the old OSSRH host are not used.

| Step | Gate | Result |
| --- | --- | --- |
| Snapshot | review on `release-snapshot` | `./gradlew build`, then the `-SNAPSHOT` from `gradle.properties` |
| Release | review on `release-ga` | `./gradlew build`, then signed `X.Y.Z`, a GitHub release, and a pull request that sets `X.Y.(Z+1)-SNAPSHOT` |

`master` stays on a snapshot. The release version is passed as `-Pversion` and is not committed. Rejecting the second review leaves the snapshot published and does not release.

Both environments allow protected branches and require a reviewer. A manual run has to start from `master`. Secrets live on the environments. A repository secret of the same name is only a fallback.

| Secret | Environment | Value |
| --- | --- | --- |
| `CENTRAL_TOKEN` | both | Portal user token, `username:password` or the base64 of that pair. Not the portal password. |
| `CENTRAL_USERNAME` | both, optional | Only when `CENTRAL_TOKEN` is the password alone. |
| `SIGNING_KEY` | `release-ga` | Armored private key, or that block in base64. |
| `SIGNING_PASSWORD` | `release-ga` | Passphrase of that key. |

Snapshots are not signed. The namespace is `pl.pojo`. Publish the public key before the first release, for example to [keys.openpgp.org](https://keys.openpgp.org/).

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md). Changes go through a pull request. The license is [LGPL-3.0](LICENSE.MD).
