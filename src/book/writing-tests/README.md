# Writing tests
Writing `pojo-methods` tests was never so easy. Using `POJO-TESTER` you just have to declare what class or classes you want to test and pass it to magic `pojo-assertions`. That's all!

##### Features
- Test `equals` and `hashCode` - all branches
- Test `getters` and `setters`
- Test `toString`
- Test `constructor` - including private and non-default (with arguments)
- Custom fields value changer
- Custom class creator (using your constructor and parameters)
- Test whole package at once
- Include / exclude fields from testing

##### Known limitations
- Custom class creator (using your constructor and parameters) does not work with abstract classes

## Basic pojo test {#basic-test}

### Basic tests for Pojo class
The simplest pojo test may look like this:

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsFor(classUnderTest).areWellImplemented();
}
```

It will test a `Pojo` class against `equals`, `hashCode`, `toString`, `getters` and `setters` which is a default test.

If your `pojo-methods` are well implemented the test will pass. Otherwise, exception will be thrown.

### Testing with AssertJ catchThrowable()
If you would rather have strict `given-when-then` convention, you can use [AssertJ](http://joel-costigliola.github.io/assertj/) and test may look a bit better.

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when
    final Throwable result = Assertions.catchThrowable(() -> assertPojoMethodsFor(classUnderTest).areWellImplemented());

    // then
    assertThat(result).isNull();
}
```
But remember, with this solution you will not get precise exception message, and you may not know why your `pojo-methods` are not well implemented.

### Testing by class name
If your class is not public, you cannot access it. Solution for this problem is testing classes via their names:

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests_When_Testing_By_Name() {
    // given
    final String qualifiedClassName = "org.pojo.playground.Pojo";

    // when

    // then
    assertPojoMethodsFor(qualifiedClassName).areWellImplemented();
}
```
When testing by class name you need to pass fully qualified class name.

### Testing with `ClassAndFieldPredicatePair`
You can pair classes and fields that should be tested in a given class in `ClassAndFieldPredicatePair`.

```java
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests_Using_ClassAndFieldPredicatePair() {
    // given
    final String qualifiedClassName = "org.pojo.playground.Pojo";
    final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(qualifiedClassName, FieldPredicate.include("a", "b"));

    // when

    // then
    assertPojoMethodsFor(classAndFieldPredicatePair).areWellImplemented();
}
```
The code above tests `pojo-methods` in the `org.pojo.playground.Pojo` class only for fields `a` and `b`.

### Changing nested fields
By default `Assertions::assertPojoMethodsFor` performs tests on a given object with changed field values. It uses field value changers to do that (see [fields values changer](#configure-fvc)). When it encounters a field that cannot be changed (e.g. `CustomPojo` type), it will create a new instance of that type and not perform any changes in this instance. If you want `POJO-TESTER` to recursively change values of such a field, you have to pass all classes with their field predicates.

For classes:

```java
class Pojo {
    private CustomPojo customPojo;
}

class CustomPojo {
    private int a;
}
```

you have to define test as follows:

```java
@Test
public void Should_Pass_All_Pojo_Tests_Changing_Fields_Recursively() {
    // given
    final ClassAndFieldPredicatePair baseClass = new ClassAndFieldPredicatePair(Pojo.class, "customPojo");
    final ClassAndFieldPredicatePair fieldClasses = new ClassAndFieldPredicatePair(CustomPojo.class, "a");

    // when

    // then
    assertPojoMethodsFor(baseClass, fieldClasses).areWellImplemented();
}
```

Above test means:

> Dear `POJO-TESTER`, when you create different instances of class `Pojo`, include field `customPojo`, but have in mind that this `CustomPojo` has field `a`. You should generate two instances of `CustomPojo` - each with different value of the `a` field, because `Pojo::equals` method implementations contains `customPojo`.

## Choose kind of tests {#choosing-testers}
There is no need for testing `pojo-methods` in a class that don't implemented them.

You can choose which testers you want to run via `pl.pojo.tester.api.assertion.AbstractAssetion::testing` method.

### Running testers
```java
import pl.pojo.tester.api.assertion.Method;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests_Using_All_Testers() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
                                        .testing(Method.EQUALS)
                                        .testing(Method.HASH_CODE)
                                        .testing(Method.CONSTRUCTOR)
                                        .areWellImplemented();
}
```

## Set fields for testing {#choosing-fields}

Next step is `excluding` or `including` fields which should be tested. By default, all the fields are tested.

You can include or exclude fields using `pl.pojo.tester.api.FieldPredicate` which creates Java 8 `Predicate` that accepts given field names.

### Include all fields (default behavior)

```java
import static pl.pojo.tester.api.FieldPredicate.includeAllFields;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests_Including_All_Fields() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsFor(classUnderTest, includeAllFields(classUnderTest)).areWellImplemented();
}
```

### Include specified fields

```java
import static pl.pojo.tester.api.FieldPredicate.include;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests_Including_Specified_Fields() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsFor(classUnderTest, include("field1", "field2")).areWellImplemented();
}
```

### Exclude specified fields

```java
import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests_Excluding_Specified_Fields() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsFor(classUnderTest, exclude("field1", "field2")).areWellImplemented();
}
```

**Remember**. Always **prefer excluding over including** as this will make your `pojo-tests` less prone to bugs. For example, if you add a new field, but forget to implement `equals` method, `POJO-TESTER` will catch that. But if you chose to use `including` predicate, then you probably also forgot to include that field in your tests.


## Configure field value changer {#configure-fvc}
`POJO-TESTERS` uses `fields values changers` to change field value e.g. when creating different instances.

You can change default `fields values changer` via `pl.pojo.tester.api.assertion.AbstractAssetion::using` method as shown below.
```java
@Test
public void Should_Pass_All_Pojo_Tests_Using_Custom_Fields_Values_Changer() {
    // given
    final Class<?> classUnderTest = Pojo.class;
    final CustomFieldsValuesChanger customFieldsValuesChanger = new CustomFieldsValuesChanger();

    // when

    // then
    assertPojoMethodsFor(classUnderTest).using(customFieldsValuesChanger)
                                        .areWellImplemented();
}
```

### Define custom fields values changer
To define your own `fields values changer` you have to extend `pl.pojo.tester.internal.field.AbstractFieldValueChanger` class.

`AbstractFieldValueChanger` defines two methods that you have to override:
* `boolean canChange(final Class<?> type)` - this method should perform compatibility checks; e.g., if class is equal to your changer type `T`. If you decide that value cannot be changed, no further steps are taken. Methods `areDifferentValues` and `increaseValue` are not invoked.
* `T increaseValue(T value, final Class<?> type)` - this method should change given `value` and return new one. `type` is given as little help, when your field type is e.g. interface and the value is its implementation.

Custom fields values changer may look like this:

```java
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public class CustomFieldsValuesChanger extends AbstractFieldValueChanger<String> {
    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    protected String increaseValue(final String value, final Class<?> type) {
        return value + "++increased";
    }
}
```


### Attaching custom fields values changer
Fields values changer uses `chain of responsibility` pattern which allows you to register new fields values changer to default one.

```java
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

final AbstractFieldValueChanger valueChanger = DefaultFieldValueChanger.INSTANCE.attachNext(customFieldsValuesChanger)
                                                                                .attachNext(customFieldsValuesChanger)
                                                                                .attachNext(customFieldsValuesChanger);
```

### Default fields values changer
Default field value changer (`DefaultFieldValueChanger.INSTANCE`) is a composition of listed changers:

###### Collections
* `ArrayListValueChanger`
* `DequeValueChanger`
* `HashSetValueChanger`
* `LinkedHashSetValueChanger`
* `LinkedListValueChanger`
* `ListValueChanger`
* `QueueValueChanger`
* `SetValueChanger`
* `SortedSetValueChanger`
* `StackValueChanger`
* `TreeSetValueChanger`
* `VectorValueChanger`
* `IterableValueChanger`
* `IteratorValueChanger`
* `HashMapValueChanger`
* `HashtableValueChanger`
* `LinkedHashMapValueChanger`
* `MapValueChanger`
* `SortedMapValueChanger`
* `TreeMapValueChanger`
* `ArrayValueChanger`
* `StreamValueChanger`

###### Date and time
* `DateFieldValueChanger`
* `InstantFieldValueChanger`
* `LocalDateFieldValueChanger`
* `LocalDateTimeFieldValueChanger`
* `LocalTimeFieldValueChanger`
* `SqlDateFieldValueChanger`
* `ZonedDateTimeFieldValueChanger`

###### Math
* `BigDecimalValueChanger`
* `BigIntegerValueChanger`

###### Primitives and wrappers
* `BooleanValueChanger`
* `ByteValueChanger`
* `CharacterValueChanger`
* `DoubleValueChanger`
* `FloatValueChanger`
* `IntegerValueChanger`
* `LongValueChanger`
* `ShortValueChanger`

###### Others
* `EnumValueChanger`
* `StringValueChanger`
* `UUIDValueChanger`

## Create class using selected constructor {#choose-constructor}
Sometimes you want to choose which constructor is used to instantiate your class or what parameters are passed. Common example is when constructor validates parameters and throws exceptions.

To indicate what constructor to choose, `POJO-TESTER` needs to know three things:

* a class, which constructor will be chosen
* constructor's parameters types
* constructor's parameters

And again, defining this in `POJO-TESTER` is a piece of cake:

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests() {
    // given
    final String qualifiedClassName = "org.pojo.playground.Pojo";
    final Object[] constructorParameters = {1, 2.0, new Object()};
    final Class[] constructorParameterTypes = {int.class, double.class, Object.class};

    // when

    // then
    assertPojoMethodsFor(qualifiedClassName).create(qualifiedClassName, constructorParameters, constructorParameterTypes)
                                            .areWellImplemented();
}
```

Here `POJO-TESTER` provides additional class, which groups constructor's parameter types and constructor parameters:

```java
import pl.pojo.tester.api.ConstructorParameters;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
public void Should_Pass_All_Pojo_Tests() {
    // given
    final String qualifiedClassName = "org.pojo.playground.Pojo";
    final Object[] parameters = {1, 2.0, new Object()};
    final Class[] parameterTypes = {int.class, double.class, Object.class};
    final ConstructorParameters constructorParameters = new ConstructorParameters(parameters, parameterTypes);

    // when

    // then
    assertPojoMethodsFor(qualifiedClassName).create(qualifiedClassName, constructorParameters)
                                            .areWellImplemented();
}
```

## Bulk POJO testing {#bulk-testing}

Sometimes you want to test all POJOs in one test, e.g. testing `toString` method. `POJO-TESTER` has features for testing multiple classes. In order to do that, you have to use `Assertions::assertPojoMethodsForAll` instead of `Assertions::assertPojoMethodsFor` method:
```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

@Test
public void Should_Pass_All_Pojo_Tests_For_All_Classes() {
    // given
    final Class<Pojo> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsForAll(classUnderTest, classUnderTest, classUnderTest, classUnderTest).areWellImplemented();
}
```
Method `assertPojoMethodsForAll` works a bit differently to `assertPojoMethodsFor`.
This method test all classes. If it encounters field of type from given classes, it will create an instance of that class and change its value recursively.

Imagine you are testing two classes, `A` and `B`. Class `A` has a field of type `B`. When you pass those classes to the `POJO-TESTER`, it will create instance of `B` class, change its value generating different objects, and finally will set all those objects into class `A`.


## If testing time grows... {#testing-time}
... then you can use switch which will generate fewer objects to test.
It may cause lower code coverage.

Usage:

```java
 assertPojoMethodsFor(classUnderTest).quickly()
                                     .areWellImplemented();
```

By default, it will perform full tests - with much more generated objects compared to using `AbstractAssertion#quickly` method.

For more details see [#201](https://github.com/sta-szek/pojo-tester/issues/201)


## Debugging {#debugging}
`pojo-tester` is not a perfect library. Sometimes you have to debug tests to see what happened.
If you think that `pojo-tester` has a bug, just switch logging level to `DEBUG` and investigate.

### Switching to debug level

E.g., `log4j.properties` file:

```properties
# Root logger option
log4j.rootLogger=DEBUG, stdout
# Direct log messages to stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n
```

Place it in the `src/test/resources` directory:

![](log4jproperties.png)

### Log analysis

List of field value changers:

```
(...) Attaching pl.pojo.tester.internal.field.primitive.ByteValueChangerto pl.pojo.tester.internal.field.primitive.BooleanValueChanger
(...) Attaching pl.pojo.tester.internal.field.primitive.CharacterValueChangerto pl.pojo.tester.internal.field.primitive.ByteValueChanger
(...) Attaching pl.pojo.tester.internal.field.primitive.DoubleValueChangerto pl.pojo.tester.internal.field.primitive.CharacterValueChanger
(...) Attaching pl.pojo.tester.internal.field.primitive.IntegerValueChangerto pl.pojo.tester.internal.field.primitive.DoubleValueChanger
(...) Attaching pl.pojo.tester.internal.field.primitive.LongValueChangerto pl.pojo.tester.internal.field.primitive.IntegerValueChanger
```

Testers that were used:

```
(...) Testers: [EqualsTester, ToStringTester, GetterTester, HashCodeTester, SetterTester, ConstructorTester]
```

Changing field value:

```
(...) Changing value of type class java.lang.String from 'www.pojo.pl' to 'www.pojo.pl++increased' (pl.pojo.tester.internal.field.StringValueChanger@21a947fe)
(...) Changing value of type int from '1' to '2' (pl.pojo.tester.internal.field.primitive.IntegerValueChanger@69a10787)
```

Generating objects:

```
(..1) Classes: [pl.pojo.tester.model.Pojo2(a,b,c,pojo,pojo3), pl.pojo.tester.model.Pojo(a,b,c), pl.pojo.tester.model.Pojo3(a,b,c,pojo)]
(..2) Start of generating different objects for base class pl.pojo.tester.model.Pojo2(a,b,c,pojo,pojo3). Base object is Pojo2(a=0, b=0, c=0, pojo=null, pojo3=null) -- others will be cloned from this one
(..3) Caching 8 different objects for class pl.pojo.tester.model.Pojo in dejaVu cache
(..4) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(..5) 	Start of generating different objects for base class pl.pojo.tester.model.Pojo3(a,b,c,pojo). Base object is Pojo3(a=0, b=0, c=0, pojo=null) -- others will be cloned from this one
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(...) 	Reusing 8 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo
(..6) 	End of generating different objects (size=72) for base class pl.pojo.tester.model.Pojo3(a,b,c,pojo)
(...) Caching 72 different objects for class pl.pojo.tester.model.Pojo3 in dejaVu cache
(...) Reusing 72 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo3
(...) Reusing 72 objects from 'dejaVu' cache for class pl.pojo.tester.model.Pojo3
(..7) End of generating different objects (size=5256) for base class pl.pojo.tester.model.Pojo2(a,b,c,pojo,pojo3
```

* `1` print classes that are being tested
* `2` start generating different objects for given class
* `3` some objects are cached in case of need for generating them later
* `4` and some of them are reused
* `5` generating nested object (see that tabulator is used - this means second level in hierarchy)
* `6` end of generating nested object
* `7` end of generating base object