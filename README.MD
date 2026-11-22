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
| Maven Central | last published release is `0.7.6` (2017). Snapshots publish after CI on `master`. Releases publish from a `vX.Y.Z` tag. |
| Version in this branch | `0.8.0-SNAPSHOT` |

Documentation site: [pojo.pl](http://pojo.pl) (may be stale). The user guide in this repository has a [fork survey](src/book/forks/README.md) (23 November 2026). No fork should be merged back as a whole.

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

[Release](.github/workflows/release.yml) publishes to the [Central Portal](https://central.sonatype.com/). Bintray and the old OSSRH host are not used.

| Trigger | What is published |
| --- | --- |
| Push to `master`, after CI is green | The `-SNAPSHOT` version from `gradle.properties` |
| Tag `vX.Y.Z` | Release `X.Y.Z`, then a pull request that bumps the snapshot patch |
| Manual run with a version | That release version |
| Manual run with an empty version | The current snapshot |

`master` stays on a snapshot. Do not commit a release version there. Create the release by pushing a tag:

```bash
git tag v0.8.0
git push origin v0.8.0
```

The tag does not have to change `gradle.properties`. The workflow passes the tag as `-Pversion`.

Repository secrets (Settings, Secrets and variables, Actions). Do not commit them.

| Secret | Value |
| --- | --- |
| `CENTRAL_TOKEN` | User token from the portal: Account, Generate User Token. Store `username:password`, or the base64 of that pair (the Copy user token button). Not the portal login password. |
| `CENTRAL_USERNAME` | Optional. Set it only when `CENTRAL_TOKEN` is the password by itself. |
| `SIGNING_KEY` | ASCII-armored private key, or that block encoded as base64. Required for a release, not for a snapshot. |
| `SIGNING_PASSWORD` | Passphrase of that key. Required for a release. |

The namespace on the portal has to be `pl.pojo`. That group already has `pl.pojo:pojo-tester:0.7.6`, so a new account must claim the existing namespace. An old OSSRH token returns 401. Before the first snapshot, open [Namespaces](https://central.sonatype.com/publishing/namespaces), open the menu on `pl.pojo`, and choose **Enable SNAPSHOTs**. Without that the snapshot repository answers 403. Publish the public GPG key, for example to [keys.openpgp.org](https://keys.openpgp.org/), before the first release. Snapshots are not signed.
