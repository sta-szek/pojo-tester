# Release Notes

Download latest version [ ![Download](https://api.bintray.com/packages/sta-szek/maven/pojo-tester/images/download.svg) ](https://bintray.com/sta-szek/maven/pojo-tester/_latestVersion)

## Release version 0.7.0 {#release-070}

First `POJO-TESTER` open source release.

### Features
* Support for inheritance fields ([#143](https://github.com/sta-szek/pojo-tester/issues/143))

### Bugfixes
* Better equality check ([#146](https://github.com/sta-szek/pojo-tester/issues/146))





## Release version 0.6.0 {#release-060}

First `POJO-TESTER` open source release.

### Features
* Tests will fail if `equals` or `hashCode` implementation depends on excluded fields ([#121](https://github.com/sta-szek/pojo-tester/issues/121))





## Release version 0.5.0 {#release-050}

### Features
* `POJO-TESTER` can test constructors ([#113](https://github.com/sta-szek/pojo-tester/issues/113))
* `POJO-TESTER` will change `String` fields by default ([#133](https://github.com/sta-szek/pojo-tester/issues/133))
* Testing classes by package name or class package ([#114](https://github.com/sta-szek/pojo-tester/issues/114))

### Bugfixes
* `POJO-TESTER` fails on synthetic constructors ([#126](https://github.com/sta-szek/pojo-tester/issues/126)) 





## Release version 0.4.0 {#release-040}

First `POJO-TESTER` open source release.

### Features
* Javadocs
* `POJO-TESTER` creates collections objects instead of mocking them ([#112](https://github.com/sta-szek/pojo-tester/issues/112))





## Release version 0.3.0 {#release-030}

### Features
* Parameters validation on API layer ([#66](https://github.com/sta-szek/pojo-tester/issues/66))
* Testing classes by name on API ([#72](https://github.com/sta-szek/pojo-tester/issues/72))
* Choose constructor and pass parameters for creating new objects ([#84](https://github.com/sta-szek/pojo-tester/issues/84))

### Bugfixes
* Wrong proxy implementation ([#88](https://github.com/sta-szek/pojo-tester/issues/88)) 





## Release version 0.2.0 {#release-020}

### Features
* `SetterGetterTester` split into `SetterTester` and `GetterTester` ([#87](https://github.com/sta-szek/pojo-tester/issues/87))
* New, not empty value when initializing `String` objects ([#86](https://github.com/sta-szek/pojo-tester/issues/86))

### Bugfixes
* Setter not found, when field is boolean type and has `is` prefix ([#89](https://github.com/sta-szek/pojo-tester/issues/89)) 
* Wrong getter is found for fields with same endingd ([#90](https://github.com/sta-szek/pojo-tester/issues/90))
* Accessing not public classes, setters and getters in those classes ([#75](https://github.com/sta-szek/pojo-tester/issues/75), [#78](https://github.com/sta-szek/pojo-tester/issues/78))
* Tests test same objects, which cause assertion exception ([#85](https://github.com/sta-szek/pojo-tester/issues/85))





## Release version 0.1.0 {#release-010}

### Features
* Testing methods: `equals`, `hashCode`, `toString`, `getters and setters`
* Testing classes by name
