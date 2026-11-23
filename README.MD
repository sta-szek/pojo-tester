# POJO-TESTER

Java testing library for POJO methods: `equals`, `hashCode`, `toString`, getters, setters and constructors.

The project was paused in 2020. It is being brought back so the same Java 8 bytecode still runs on current JDKs.

## Status

| | |
| --- | --- |
| License | [LGPL-3.0](LICENSE.MD) (unchanged) |
| Bytecode | Java 8 |
| CI | GitHub Actions. `build` on Java 8, 11, 17, 21 and 25. Mutation tests (PIT, threshold 80%), Spotless (changed files only) and SpotBugs (SARIF to code scanning) on Java 21. Coverage goes to Codecov. |
| Coordinates | `pl.pojo:pojo-tester:0.8.0-SNAPSHOT` |
| Maven Central | last published release is `0.7.6` (2017). The next version is the snapshot above. |

Documentation site: [pojo.pl](http://pojo.pl) (may be stale). The user guide in this repository has a [fork survey](src/book/forks/README.md) (23 November 2026). No fork should be merged back as a whole.

## What a test accepts

This is the behavior on `master` (`0.8.0-SNAPSHOT`). Release `0.7.6` does not include it.

- A final field is copied, not given a different value. Call `changingFinalFields()` to opt in. On Java 12 and newer that write goes through `VarHandle`, so the old `Field.modifiers` failure does not happen.
- `create(Class, Supplier)` supplies the instance when the class has no usable constructor. That covers a builder and a constructor that rejects the library's default arguments.
- A setter may return the class under test (`return this`). A method that returns `Object` is not treated as a setter.
- JavaBeans spelling is kept when the second letter is already uppercase: `xCoordinate` matches `getxCoordinate` and `setxCoordinate`, and `URL` matches `getURL`.
- An enum is not tested for setters. A setter of an enum-typed field on an ordinary class still is.
- A package scan loads only `*.class` files. A `mapper.xml` sitting next to the classes is ignored.
- `toString` may wrap a `String` or `char` in single quotes. An `Object[]` printed with `Arrays.toString` is accepted.
- Getters and setters are checked with a distinct value in every field.
- `equals` is also checked with `null` in each included field that is not primitive and not `final`.

```gradle
dependencies {
    testImplementation 'pl.pojo:pojo-tester:0.8.0-SNAPSHOT'
}
```

The snapshot repository is `https://central.sonatype.com/repository/maven-snapshots/`. Release `0.7.6` does not need it.

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
| `spotlessCheck` | formatting of files changed vs `origin/master` (`spotlessApply` fixes them) |
| `spotbugsMain` | static analysis (reports in `build/reports/spotbugs`) |
| `jacocoTestReport` | merged unit and integration coverage |

`build` depends on `check`, and `check` depends on both test tasks. Mutation tests are a separate task because a full PIT run is much slower than the test suite. CI runs `build` on each JDK in the matrix and `pitest` once on Java 21.

## Publishing

Two workflows publish to the [Central Portal](https://central.sonatype.com/). Bintray and the old OSSRH host are not used.

| Workflow | When | Environment | What is published |
| --- | --- | --- | --- |
| [Release snapshot](.github/workflows/release-snapshot.yml) | CI is green on `master`, or a manual run from `master` | `release-snapshot` (protected branches) | The `-SNAPSHOT` version from `gradle.properties` |
| [Release](.github/workflows/release-ga.yml) | Tag `X.Y.Z` | `release-ga` (tags `*.*.*` only) | Release `X.Y.Z`, a GitHub release, then an auto-merged pull request that bumps the snapshot patch |

`master` stays on a snapshot. Do not commit a release version there, and do not publish a release from a branch. The `release-ga` environment rejects every ref that is not a `*.*.*` tag. The tag has no `v` prefix:

```bash
git tag 0.8.0
git push origin 0.8.0
```

The tag does not have to change `gradle.properties`. The workflow passes the tag as `-Pversion`.

Each environment has its own secrets (Settings, Environments). A repository secret with the same name is only a fallback. Do not commit them.

| Secret | Where | Value |
| --- | --- | --- |
| `CENTRAL_TOKEN` | both environments | User token from the portal: Account, Generate User Token. Store `username:password`, or the base64 of that pair (the Copy user token button). Not the portal login password. |
| `CENTRAL_USERNAME` | both, optional | Set it only when `CENTRAL_TOKEN` is the password by itself. |
| `SIGNING_KEY` | `release-ga` | ASCII-armored private key, or that block encoded as base64. |
| `SIGNING_PASSWORD` | `release-ga` | Passphrase of that key. |

Snapshots are not signed, so `release-snapshot` does not read the signing secrets. The namespace on the portal has to be `pl.pojo`. That group already has `pl.pojo:pojo-tester:0.7.6`, so a new account must claim the existing namespace. An old OSSRH token returns 401. Before the first snapshot, open [Namespaces](https://central.sonatype.com/publishing/namespaces), open the menu on `pl.pojo`, and choose **Enable SNAPSHOTs**. Without that the snapshot repository answers 403. Publish the public GPG key, for example to [keys.openpgp.org](https://keys.openpgp.org/), before the first release.
