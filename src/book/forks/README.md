# Fork survey {#fork-survey}

Survey of every public fork of [sta-szek/pojo-tester](https://github.com/sta-szek/pojo-tester), taken on 23 November 2026. Nothing in this page has been merged. It is a record of what the forks added and what is already in this repository.

## Verdict

Do not merge any fork back as a whole.

The only fork with a long independent history is [obsidiandynamics/pojo-tester](https://github.com/obsidiandynamics/pojo-tester). It republishes the library under different Maven coordinates, drops writing through `final` fields, and calls this project defunct. Taking that history would throw away the Java 8–25 line of work. The behaviour worth keeping from the other forks is already on `master`, brought in through pull requests or reimplemented here.

## How this was compared

The GitHub repository counter said 24 forks. The forks API returned 23 public repositories. Each default branch was compared with `master` (`base...fork:master`: commits reachable from the fork and not from `master`). Other branches were listed. Branches other than `master`, `gh-pages` and `issue-217` were compared the same way. `gh-pages` is the old published book. `issue-217` on those forks is the 2017 supplier draft; the current form of that idea is pull request #222, not a fork.

`ahead` means commits on the fork that are not in `master`. A feature can still already be present if it landed later as a different commit. Those cases are marked below.

## Already in this repository

| Fork work | Where it landed |
| --- | --- |
| User-defined instantiators (Galeries Lafayette and frecco75, 2020) | #243, `UserObjectInstantiator` |
| Skip setters of `final` fields (same forks, and Obsidian Dynamics) | `SetterTester` already filters with `FieldUtils.isFinal` |
| `toString` of primitive arrays (Obsidian Dynamics, and rmohta) | #231 |
| Fast equals / hashCode, later `quickly()` (deleted account, issue #203) | `AbstractAssertion.quickly()` and `SublistFieldPermutator` |
| Test every class in a package (suteerth, theangrydev, issue #114) | Already in history (`ahead = 0`) |
| Static nested class (theangrydev, issue #126) | Already in history |
| Bintray / Maven Central wiring (waffle-iron, issue #70) | Superseded by Central Portal publishing |
| Equals and hashCode of inner classes (frecco75) | #244 |
| LGPL-3.0 text (`less-gpl` branch) | `LICENSE.MD` is already that text |

## Forks with nothing of their own

These 18 default branches are strictly behind `master`. No unique commit.

`sdk2035`, `djn72`, `p91paul`, `drobins29`, `thePrincipal` (default branch only), `niklasga`, `dhaase`, `rmohta` (the array fix is #231; the branch `feature/arrays-to-string` is `ahead = 0`), `elvisnovoa` (`develop` is `ahead = 0`), `Theosakamg`, `anand1velusamy`, `jbaltus`, `gedl`, `adamsiemion`, `SemionPar`, `suteerth`, `theangrydev`, `waffle-iron`.

## Forks that diverged

### obsidiandynamics/pojo-tester

32 commits ahead, 18 behind. Last push 26 July 2022. Author of that line: Emil Koutanov. 9 stars. Published as `com.obsidiandynamics.pojotester:core`. One downstream fork, [sansp00/pojo-tester](https://github.com/sansp00/pojo-tester).

What that line actually changed in library code:

- `toString` prints arrays with `java.lang.reflect.Array`, including primitive arrays. Same fix as #231.
- Setters ignore `final` fields. Already the case here.
- `Field.set` no longer clears the `final` modifier. The fork stopped writing `final` fields because of the JDK 12 change. This repository still writes them, through `VarHandle` on JDK 12+ and the old modifier hack on JDK 8. Do not take this deletion.
- `Method.getTester()` uses `getDeclaredConstructor().newInstance()` instead of `Class.newInstance()`. That four-line cleanup is the only patch from this fork that is not already here. It should be written again on `master`, not cherry-picked from the fork. `Class.newInstance()` is deprecated, and the suite still passes on Java 25, so this is cleanup rather than a broken feature.
- The rest is packaging: Gradle 7.5, GitHub Actions of that era, integration tests folded into the unit-test source set, SLF4J NOP, badges, and docs that describe this project as abandoned.

Do not merge the repository. The coordinates, the group id and the decision about `final` fields all conflict with this one.

sansp00 adds a Maven POM for the Obsidian coordinates and adjusts `DefaultFieldValueChangerTest` for the Reflections library. No library feature.

### galeries-lafayette/pojo-tester

7 commits ahead, 18 behind. Fabien Recco, January 2020. Released by them as `0.8.0` from a Maven build (`pom.xml`, wrapper, Java 12).

The library commits are custom instantiators, skipping setters on `final` fields, and a Java 12 `FieldUtils` that deletes the modifier hack. The first two are on `master`. The third is the same dead end as the Obsidian change: this repository kept the ability to set `final` fields and made it work on Java 25. The Maven build targets Java 12 and old dependency versions. This repository stays on Gradle.

### frecco75/pojo-tester

5 commits ahead, 18 behind. Same author and the same series as Galeries Lafayette, with Java 13 instead of the Maven release commits. Branches `feat/custom_instantiators` and `fix/equals_hashcode_inner_class` are `ahead = 0` because #243 and #244 merged them. `fix/pojo_with_final_field` is the setter filter already in `SetterTester`. `feat/java13-support` is the `FieldUtils` deletion described above.

### thecrazzymouse/pojo-tester and thePrincipal `feature/java-13-compatible`

One commit each, November 2019 and the Java 13 branch. Denes Nemeth on thecrazzymouse: dependency and Gradle bump plus the same `FieldUtils` change (stop clearing `final`). thePrincipal's message is "Remove deprecated reflection code for modifiers". Superseded by the current `FieldUtils`.

### Deleted account, `fast-test-equals-v2`

The owner shows up as `36893488147419103231` because the account was deleted. 4 commits on `master`, 8 on `fast-test-equals-v2`. That branch added `EQUALS_FAST` and `HASH_CODE_FAST`, then replaced them with `quickly()` after review on issue #203. `quickly()` is on `master`. Merging the branch would move the permutators back under `internal.instantiator` and restore the abandoned enum constants. The `less-gpl` branch only adds the LGPL-3.0 text, which is already `LICENSE.MD`.

## What is worth doing next

Not a merge. One small change, written here:

- In `Method.getTester()`, construct the tester with `getDeclaredConstructor().newInstance()` and stop calling `Class.newInstance()`.

Nothing else from the forks is a missing feature.
