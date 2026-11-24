# Fork survey

Survey of the public forks of [sta-szek/pojo-tester](https://github.com/sta-szek/pojo-tester), taken on 23 November 2026. Nothing on this page was merged as a repository. It records what the forks added and what is already here.

## Verdict

Do not merge any fork back as a whole.

The only fork with a long independent history is [obsidiandynamics/pojo-tester](https://github.com/obsidiandynamics/pojo-tester). It republishes the library under different Maven coordinates, stops writing `final` fields, and describes this project as abandoned. Taking that history would drop the Java 8 through 25 line. The behaviour worth keeping from the other forks is already on `master`.

## How they were compared

The GitHub counter said 24 forks. The forks API returned 23 public repositories. Each default branch was compared with `master` (`base...fork:master`: commits reachable from the fork and not from `master`). Branches other than `master`, `gh-pages` and `issue-217` were compared the same way. `gh-pages` is the published site. `issue-217` on those forks is the 2017 supplier draft. The current form of that idea landed later, not as a fork.

`ahead` means commits on the fork that are not in `master`. A feature can still already be present if it landed later as a different commit.

## Already in this repository

| Fork work | Where it landed |
| --- | --- |
| User-defined instantiators (Galeries Lafayette and frecco75, 2020) | #243, `UserObjectInstantiator` |
| Skip setters of `final` fields | `SetterTester` filters with `FieldUtils.isFinal` |
| `toString` of primitive arrays | #231 |
| Fast equals and hashCode, later `quickly()` | `AbstractAssertion.quickly()` |
| Test every class in a package | `assertPojoMethodsForAll(PackageFilter)` |
| Static nested class | already in history |
| Bintray and Maven Central wiring | superseded by Central Portal publishing |
| Equals and hashCode of inner classes | #244 |
| LGPL-3.0 text | `LICENSE.MD` |
| `Method.getTester()` no longer calls `Class.newInstance()` | `getDeclaredConstructor().newInstance()` |

## Forks with nothing of their own

These 18 default branches are strictly behind `master`.

`sdk2035`, `djn72`, `p91paul`, `drobins29`, `thePrincipal` (default branch only), `niklasga`, `dhaase`, `rmohta`, `elvisnovoa`, `Theosakamg`, `anand1velusamy`, `jbaltus`, `gedl`, `adamsiemion`, `SemionPar`, `suteerth`, `theangrydev`, `waffle-iron`.

## Forks that diverged

### obsidiandynamics/pojo-tester

32 commits ahead, 18 behind. Last push 26 July 2022. Published as `com.obsidiandynamics.pojotester:core`. One downstream fork, [sansp00/pojo-tester](https://github.com/sansp00/pojo-tester), which adds a Maven POM for those coordinates.

The library changes are array `toString`, skipping setters of `final` fields, and no longer clearing the `final` modifier. The first two are on `master`. The third deletes an ability this repository kept as `changingFinalFields()`. The rest is packaging from that period and documentation that calls this project abandoned.

Do not merge the repository. The coordinates and the decision about `final` fields conflict with this one.

### galeries-lafayette/pojo-tester

7 commits ahead, 18 behind. January 2020. Released by them as `0.8.0` from a Maven build aimed at Java 12.

The library commits are custom instantiators, skipping setters on `final` fields, and a `FieldUtils` that stops writing `final` fields. The first two are on `master`. The third is the same deletion as the Obsidian change. This repository stays on Gradle.

### frecco75/pojo-tester

5 commits ahead, 18 behind. The same series as Galeries Lafayette, with Java 13 instead of the Maven release commits. The instantiator and inner-class branches are already in history through #243 and #244.

### thecrazzymouse/pojo-tester and thePrincipal `feature/java-13-compatible`

One commit each, November 2019. Both stop clearing the `final` modifier. Superseded by the current `FieldUtils`.

### Deleted account, `fast-test-equals-v2`

The owner is shown as `36893488147419103231` because the account was deleted. That branch added `EQUALS_FAST` and `HASH_CODE_FAST`, then replaced them with `quickly()` after review on issue #203. `quickly()` is on `master`. Merging the branch would move the permutators and restore the abandoned enum constants.

## What is left

Nothing from the forks is a missing feature.
