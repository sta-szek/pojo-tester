# Contributing

Thanks for helping with pojo-tester. The library is `pl.pojo:pojo-tester`, licensed under LGPL-3.0.

## Ground rules

- Open a pull request. Do not push to `master`.
- Write in English: issues, commits, and discussion.
- Keep the bytecode on Java 8. Gradle itself needs JDK 17 or newer.
- Add or update a test for a behavior change. `./gradlew build` runs the unit tests and the integration tests.
- Do not commit secrets, tokens, or signing keys.

## Build

```bash
./gradlew build
./gradlew build -PtestJavaVersion=21
./gradlew pitest -PtestJavaVersion=21
```

`-PtestJavaVersion` accepts `8`, `11`, `17`, `21`, or `25`. The default is `8`.

Format only the files you changed, against `origin/master`:

```bash
./gradlew spotlessApply -PtestJavaVersion=21
```

## Pull requests

- One change per pull request.
- Say what behavior changed and how a test covers it.
- A change that breaks an existing assertion needs an explicit note in the pull request. Do not weaken a check just to make a test pass.
