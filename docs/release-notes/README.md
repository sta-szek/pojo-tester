# Release Notes



## Release version 0.3.0 {#release-0.3.0}
### Features
* Parameters validation on API layer ([#66](https://github.com/sta-szek/pojo-tester/issues/66))
* Testing classes by name on API ([#72](https://github.com/sta-szek/pojo-tester/issues/72))
* Choose constructor and pass parameters for creating new objects ([#84](https://github.com/sta-szek/pojo-tester/issues/84))

### Bugfixes
* Wrong proxy implementation ([#88](https://github.com/sta-szek/pojo-tester/issues/88)) 


## Release version 0.2.0 {#release-0.2.0}
### Features
* `SetterGetterTester` split into `SetterTester` and `GetterTester` ([#87](https://github.com/sta-szek/pojo-tester/issues/87))
* New, not empty value when initializing `String` objects ([#86](https://github.com/sta-szek/pojo-tester/issues/86))

### Bugfixes
* Setter not found, when field is boolean type and has `is` prefix ([#89](https://github.com/sta-szek/pojo-tester/issues/89)) 
* Wrong getter is found for fields with same endingd ([#90](https://github.com/sta-szek/pojo-tester/issues/90))
* Accessing not public classes, setters and getters in those classes ([#75](https://github.com/sta-szek/pojo-tester/issues/75), [#78](https://github.com/sta-szek/pojo-tester/issues/78))
* Tests test same objects, which cause assertion exception ([#85](https://github.com/sta-szek/pojo-tester/issues/85))


## Release version 0.1.0 {#release-0.1.0}
### Features
* Testing methods: `equals`, `hashCode`, `toString`, `getters and setters`
* Testing classes by name
