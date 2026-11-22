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
| Maven Central | last published release is `0.7.6` (2017). The Publish workflow uploads to the Central Portal. |
| Version in this branch | `0.8.0-SNAPSHOT` |

Documentation site: [pojo.pl](http://pojo.pl) (may be stale).

## Build

Gradle itself needs JDK 17 or newer. Sources are compiled with `--release 8`, so the jar still runs on Java 8. CI runs the test suite on Temurin 8, 11, 17, 21 and 25.

```bash
./gradlew build
./gradlew build -PtestJavaVersion=21
```

`-PtestJavaVersion` accepts `8`, `11`, `17`, `21` or `25`. On JDK 16+ the test task opens `java.base` packages the library still reflects into (`java.lang`, `java.time`, and a few others).

## Publishing

Releases go to Maven Central through the [Central Portal](https://central.sonatype.com/), not Bintray and not the old OSSRH host. GitHub Actions workflow [Publish](.github/workflows/publish.yml) runs on a published GitHub Release and on manual dispatch.

The workflow reads `version` from `gradle.properties`. A version ending in `-SNAPSHOT` is uploaded to the Central snapshot repository (`publishToMavenCentral`). Any other version is signed, uploaded, and released (`publishAndReleaseToMavenCentral`). A GitHub Release is refused while the version is still a snapshot. Set a release version (for example `0.8.0`), merge that change, publish the GitHub Release, then move `version` back to the next snapshot.

Before the first publish:

1. Sign in at [central.sonatype.com](https://central.sonatype.com/). This is a different account from the old Jira/OSSRH login. An old OSSRH token returns 401.
2. Register the namespace `pl.pojo`. It is already used by `pl.pojo:pojo-tester:0.7.6`, so a new account has to claim that existing namespace. Follow the verification Central shows (DNS or support). Do not invent a new group id.
3. Generate a user token ([Account, Generate User Token](https://central.sonatype.org/publish/generate-portal-token/)). The token has its own username and password. Those are not the portal password.
4. Create a GPG key and publish the public key (for example to [keys.openpgp.org](https://keys.openpgp.org/)). Maven Central rejects unsigned releases.

Add these repository secrets (Settings, Secrets and variables, Actions). Do not commit them.

| Secret | Value |
| --- | --- |
| `CENTRAL_USERNAME` | user-token username |
| `CENTRAL_PASSWORD` | user-token password |
| `SIGNING_KEY` | ASCII-armored private key, or that block encoded as base64 |
| `SIGNING_PASSWORD` | passphrase of that key |

Local equivalent, if you need it:

```bash
./gradlew publishAndReleaseToMavenCentral -PtestJavaVersion=21 \
  -PmavenCentralUsername=... \
  -PmavenCentralPassword=... \
  -PsigningInMemoryKey="$(cat seckey.asc)" \
  -PsigningInMemoryKeyPassword=...
```

Use `publishToMavenCentral` instead when `version` ends with `-SNAPSHOT`.
