# Changelog

All notable changes to `pl.pojo:pojo-tester` are recorded here. The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/). Dates are the GitHub release dates.

## [Unreleased]

`0.8.0-SNAPSHOT`. Not a release. Published to the Central Portal snapshot repository after a green build on `master`.

### Added

- `create(Class, Supplier)` builds a class the library cannot construct, including a builder.
- `quickly()` changes one field at a time. `thoroughly()` remains the default.
- `withFieldPrefix` matches `mName` and `m_name` to `getName`.
- `usingFluentAccessors()` accepts a getter or setter named after the field.
- `allowingSameHashCodeForUnequalObjects()` turns off the strict hash-code check.
- `changingFinalFields()` is the opt-in write of a `final` field. It is off by default.
- `toString` may quote a `String` or a `char`, and may print an `Object[]` with `Arrays.toString`.
- Getters and setters are checked with a distinct value in every included field.
- `equals` is also checked with `null` in each included field that is not primitive and not `final`.
- A package scan loads `*.class` files only.
- GitHub Actions builds on Java 8, 11, 17, 21 and 25, and runs mutation tests, Spotless, SpotBugs and coverage.

### Changed

- The bytecode is still Java 8. Gradle 9 builds it and needs JDK 17 or newer.
- A setter may return the class under test. A method that returns `Object` is not a setter.
- JavaBeans names are kept when the second letter is uppercase (`getxCoordinate`, `getURL`).
- An enum is not tested for setters. A setter of an enum-typed field on an ordinary class still is.
- Publishing uses the Central Portal. Bintray and the old OSSRH host are not used. A release is signed and waits for the `release-ga` review. A snapshot waits for `release-snapshot`.
- This site is built with VitePress from `docs/` and published to `gh-pages`.
- Coverage reports use JaCoCo 0.8.16.
- Tests no longer use PowerMock. Field access in tests goes through `helpers.Fields`.

### Fixed

- Nested fields are given a new value when the nested class is included in the assertion.
- Empty collections, maps and arrays can be told apart from `null`.
- Static nested classes, field prefixes and the same hash code for unequal objects are covered by regression tests.
- `Class.newInstance()` is no longer used to construct a tester.

## [0.7.6] — 2017-11-26

### Added

- `quickly()`-style generation of fewer objects, then called quick testing (#201, #203).
- More instantiators for JDK types (#192).

### Fixed

- A one-constant enum no longer throws an out-of-range exception (#199).
- A getter that returns a defensive copy of an array is accepted (#212).

## [0.7.5] — 2017-03-19

### Added

- Field changers for the date and time types (#187).

### Changed

- A custom `FieldValueChanger` no longer has to override `areDifferentValues` unless `Objects.equals` is the wrong comparison.

## [0.7.4] — 2017-03-10

### Added

- `BigInteger` and `BigDecimal` changers (#178).
- Debug logging around a failed assertion (#179).

### Fixed

- The SLF4J dependency no longer collides with the test's binding (#184).

## [0.7.3] — 2017-01-27

### Changed

- Static fields are excluded (#170).
- Unit tests and integration tests run as separate tasks (#160).

## [0.7.2] — 2017-01-07

### Added

- An abstract class can be created without a default constructor (#157).

### Fixed

- Collection changers produce a non-empty collection (#153).

## [0.7.1] — 2016-12-14

### Fixed

- `UUID` values are changed, and object generation no longer repeats a value that cannot be told apart (#149).

## [0.7.0] — 2016-11-23

### Added

- Fields declared on a superclass are tested (#143).

### Fixed

- Equality uses the value, not the identity of a generated object (#146).

## [0.6.0] — 2016-10-30

### Changed

- `equals` and `hashCode` fail when they still depend on a field the test excluded (#121).

## [0.5.0] — 2016-10-10

### Added

- Constructor tests (#113).
- `String` fields are changed by default (#133).
- A package, or the package of a class, can be tested in one call (#114).

### Fixed

- Synthetic constructors are ignored (#126).

## [0.4.0] — 2016-10-01

First open-source release.

### Added

- Javadoc.
- Collections are instantiated instead of mocked (#112).

## [0.3.0]

### Added

- The public API checks its arguments (#66).
- A class can be tested by name (#72).
- The constructor and its arguments can be selected (#84).

### Fixed

- Proxy classes are no longer generated for a type the library can construct (#88).

## [0.2.0]

### Added

- Getter and setter tests are separate (#87).
- A new `String` is not empty (#86).

### Fixed

- A `boolean` field with an `is` getter finds its setter (#89).
- A getter is not chosen because its name merely ends with the field name (#90).
- Non-public classes and their accessors can be called (#75, #78).
- Two generated instances are no longer the same object (#85).

## [0.1.0]

### Added

- Tests for `equals`, `hashCode`, `toString`, getters and setters.
- A class can be tested by name.

[Unreleased]: https://github.com/sta-szek/pojo-tester/compare/0.7.6...master
[0.7.6]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.6
[0.7.5]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.5
[0.7.4]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.4
[0.7.3]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.3
[0.7.2]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.2
[0.7.1]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.1
[0.7.0]: https://github.com/sta-szek/pojo-tester/releases/tag/0.7.0
[0.6.0]: https://github.com/sta-szek/pojo-tester/releases/tag/0.6.0
[0.5.0]: https://github.com/sta-szek/pojo-tester/releases/tag/0.5.0
[0.4.0]: https://github.com/sta-szek/pojo-tester/releases/tag/0.4.0
