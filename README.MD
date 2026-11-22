# POJO-TESTER

Java testing library for POJO methods: `equals`, `hashCode`, `toString`, getters, setters and constructors.

The project was paused in 2020. It is being brought back so the same Java 8 bytecode still runs on current JDKs.

## Status

| | |
| --- | --- |
| License | [LGPL-3.0](LICENSE.MD) (unchanged) |
| Bytecode | Java 8 |
| CI | GitHub Actions. `build` on Java 8, 11, 17, 21 and 25. Mutation tests on Java 21. |
| Coordinates | `pl.pojo:pojo-tester` |
| Maven Central | last published release is `0.7.6` (2017). Later builds went to Bintray, which shut down in 2021. Publishing is not restored yet. |
| Version in this branch | `0.8.0-SNAPSHOT` |

Documentation site: [pojo.pl](http://pojo.pl) (may be stale).

## Build

`./gradlew build` compiles with `--release 8`, runs the unit tests and the integration tests, and builds the library, sources and Javadoc jars. Gradle itself needs JDK 17 or newer. The produced bytecode still runs on Java 8.

```bash
./gradlew build
./gradlew build -PtestJavaVersion=21
./gradlew pitest -PtestJavaVersion=21
```

`-PtestJavaVersion` accepts `8`, `11`, `17`, `21` or `25`. The default is `8`. On JDK 16+ the test task opens `java.base` packages the library still reflects into (`java.lang`, `java.time`, and a few others).

| Task | What it runs |
| --- | --- |
| `build` | compile, unit tests, integration tests, jars |
| `test` | unit tests |
| `integrationTest` | integration tests |
| `pitest` | mutation tests (PIT) of `pl.pojo.tester` |

`build` depends on `check`, and `check` depends on both test tasks. Mutation tests are a separate task because a full PIT run is much slower than the test suite. CI runs `build` on each JDK in the matrix and `pitest` once on Java 21.

## Publishing

Not wired to a repository yet. When the build is green again, releases should go to Maven Central (Central Portal), not Bintray. See the open pull request notes for the account checklist. The license stays LGPL-3.0.
