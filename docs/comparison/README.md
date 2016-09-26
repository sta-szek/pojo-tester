# Comparison

Here you can compare pojo-tester to existing java libraries that test `pojo-methods`.

## Other libraries {#other-libs}
Here is list of libraries that were found on the Internet. If you find another one, just make a comparison and send PR.

* pojo-tester 0.4.0
* [openpojo](http://openpojo.com) 0.8.4
* [SmartUnit](https://github.com/rlogiacco/SmartUnit) 0.10.2
* [getter and setter tester](https://sourceforge.net/projects/getterandsetter) 0.11
* [testUtils](http://outsidemybox.github.io/testUtils/index.html) 0.1.3
* [testUtil](http://gtcgroup.com/testutil.html) 0.1.3
* [meanBean](http://meanbean.sourceforge.net/) 0.1.3

## Tests Preconditions {#precondtioions}
Our tests will be performed on `pojo-classes`. 
Each library has it's own `pojo-class` in a package name like library name.
There are few of them, beacuse we want to try generate `pojo-methos` using 
[Lombok](https://projectlombok.org/), 
[Apache's commons lang 3](https://commons.apache.org/proper/commons-lang/) and
[Google's guava](https://github.com/google/guava).
We also measure code coverage using [JaCoCo 0.7.7.201606060606](http://www.eclemma.org/jacoco/).

Classes contains fiels as shown below:

```java
public class Pojo_Standard_Generated_Methods {
    private int a;
    private float b;
    private String c;
    private TestEnum d;
    
    enum TestEnum {
        ONE, TWO
    }
    
    // standard generated methods
}
```

## Kind of tests
Each library provides different testing features. Here is comparison.

##### Features comparision

| Kind of tests      | pojo-tester    | openpojo |
|---                 |:---:           |:---:     |
| getters            | v              |          |
| setters            | v              |          |
| equals             | v              |          |
| hashCode           | v              |          |
| toString           | v              |          |
|                    |                |          |

##### Tests

##### `POJO-TESTER`
`POJO-TESTER` tests looks like this:
```java
@Test
public void Should_Test_Pojo() {
    // given
    final Class[] classesUnderTest = {Pojo_Guava_Generated_Methods.class,
                                      Pojo_Apache_Generated_Methods.class,
                                      Pojo_Lombok_Generated_Methods.class,
                                      Pojo_Standard_Generated_Methods.class};
    // when

    // then
    assertPojoMethodsForAll(classesUnderTest).areWellImplemented();
}
```



## Coverage
Jaki coverage można osiągnąć przy użyciu każdej z bibliotek.
Najelpiej mierzyc IntelliJ oraz JaCoCo.
Do generowania testów używać apache-lang3, Objects z javy, Objects z guavy i Lomboka

## Conclusions
