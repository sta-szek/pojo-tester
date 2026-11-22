# POJO-TESTER

Java testing library for POJO methods: `equals`, `hashCode`, `toString`, getters, setters and constructors.

The project was paused in 2020. It is being brought back so the same Java 8 bytecode still runs on current JDKs.

## Status

| | |
| --- | --- |
| License | [LGPL-3.0](LICENSE.MD) (unchanged) |
| Bytecode | Java 8 |
| CI | GitHub Actions on Java 8, 11, 17, 21 and 25 |
| Coordinates | `pl.pojo:pojo-tester` |
| Maven Central | last published release is `0.7.6` (2017). Later builds went to Bintray, which shut down in 2021. Publishing is not restored yet. |
| Version in this branch | `0.8.0-SNAPSHOT` |

Documentation site: [pojo.pl](http://pojo.pl) (may be stale).

## Build

Gradle itself needs JDK 17 or newer. Sources are compiled with `--release 8`, so the jar still runs on Java 8. CI runs the test suite on Temurin 8, 11, 17, 21 and 25.

```bash
./gradlew test integrationTest
./gradlew test integrationTest -PtestJavaVersion=21
```

`-PtestJavaVersion` accepts `8`, `11`, `17`, `21` or `25`. On JDK 16+ the test task opens `java.base` packages the library still reflects into (`java.lang`, `java.time`, and a few others).

## Publishing

Not wired to a repository yet. When the build is green again, releases should go to Maven Central (Central Portal), not Bintray. See the open pull request notes for the account checklist. The license stays LGPL-3.0.
