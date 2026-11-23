# Contributors

## Contribution rules

pojo-tester is `pl.pojo:pojo-tester`, licensed under LGPL-3.0.

- Open a pull request. Do not push to `master`.
- Write in English: issues, commits, and discussion.
- Keep the bytecode on Java 8. Gradle itself needs JDK 17 or newer.
- Add or update a test for a behavior change. `./gradlew build` runs the unit tests and the integration tests.
- Do not commit secrets, tokens, or signing keys.
- One change per pull request. A change that breaks an existing assertion needs an explicit note. Do not weaken a check just to make a test pass.

```bash
./gradlew build
./gradlew build -PtestJavaVersion=21
./gradlew pitest -PtestJavaVersion=21
./gradlew spotlessApply -PtestJavaVersion=21
```

`-PtestJavaVersion` accepts `8`, `11`, `17`, `21`, or `25`. The default is `8`. `spotlessApply` formats the files you changed, compared with `origin/master`.

The same rules are in [CONTRIBUTING.md](https://github.com/sta-szek/pojo-tester/blob/master/CONTRIBUTING.md).

## People

{% GitHubContributors %}
{% endGitHubContributors %}
