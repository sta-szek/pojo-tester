# Comparison

These libraries test some of the same methods. They do not do the same job. Versions below were current when this page was written in November 2026. A later release can move a cell. This page does not repeat the coverage numbers from the 2016 comparison. Those were measured with JaCoCo 0.7.7 on four generated classes and are not a result for the current line.

| | pojo-tester | EqualsVerifier | OpenPojo | MeanBean | JavaBean Tester | bean-matchers |
| --- | --- | --- | --- | --- | --- | --- |
| Coordinates | `pl.pojo:pojo-tester` | `nl.jqno.equalsverifier:equalsverifier` | `com.openpojo:openpojo` | `com.github.meanbeanlib:meanbean` | `com.github.hazendaz:javabean-tester` | `com.google.code.bean-matchers:bean-matchers` |
| Version | `0.8.0-SNAPSHOT`, last release `0.7.6` (2017) | `4.5.2` (August 2026) | `0.9.1` | `3.0.0-M9` (2020) | `2.12.2` | `0.14` |
| License | LGPL-3.0 | Apache-2.0 | Apache-2.0 | Apache-2.0 | Apache-2.0 | BSD-3-Clause |
| Java | bytecode 8 | 8, with newer lines | 8 and newer | 8 | 17 | 8 |
| `equals` | yes | yes, the contract | only through its own `BusinessIdentity` | yes | yes | yes |
| `hashCode` | yes | yes, the contract | only through `BusinessIdentity` | yes | yes | yes |
| `toString` | yes | no | only through `BusinessIdentity` | yes, in the 3.x line | yes | yes |
| getters and setters | yes | no | yes | yes | yes | yes |
| constructors | yes | no | no | no | yes | a no-args constructor |
| Production code stays untouched | yes | yes | no, for equals | yes | yes | yes |

## How they differ

**EqualsVerifier** is the library to use when the question is only whether `equals` and `hashCode` meet the contract. It is strict about symmetry, inheritance, significant fields and the usual shortcuts, and it understands records. It does not call getters or setters. pojo-tester is not a substitute for that check.

**OpenPojo** validates structure with rules (`SetterMustExistRule`, and so on) and can test getters and setters. Its `equals`, `hashCode` and `toString` support expects the class to delegate to `BusinessIdentity` and to mark fields with `@BusinessKey`. A class that already has its own implementations is not what that tester runs.

**MeanBean** tests getters, setters, `equals` and `hashCode`. The original 2.x line is the one the 2016 page compared. The 3.x line, published as milestones through `3.0.0-M9`, adds `toString`, fluent setters and a package scan. There is no stable 3.0.0.

**JavaBean Tester** covers constructors, getters, setters, `equals`, `hashCode`, `toString` and `Serializable`. It requires Java 17, so it is not an option for a Java 8 test suite.

**bean-matchers** expresses the same checks as Hamcrest matchers, including a no-args constructor. The last release is `0.14`. The project is not maintained.

pojo-tester runs the six testers against the methods the class already has. It does not ask for an annotation, and one assertion can also select fields, a constructor, a supplier and a prefix. It will not catch every contract mistake EqualsVerifier catches. Use both when a class has a hand-written `equals` and also has getters.

## Earlier libraries

The 2016 page also listed SmartUnit, testUtils and testUtil. They are not maintained and are not in the table. A project that still builds against one of them has no reason to stay there for a new test.
