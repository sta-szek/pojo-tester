# Writing tests

## One class

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@Test
void orderIsAWellFormedPojo() {
    assertPojoMethodsFor(Order.class).areWellImplemented();
}
```

A class that is not visible to the test can be named by its binary name. The name has to be qualified.

```java
assertPojoMethodsFor("com.example.internal.Order").areWellImplemented();
```

`testing` limits the run. Without it, every tester runs.

```java
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

assertPojoMethodsFor(Order.class)
        .testing(EQUALS, HASH_CODE, TO_STRING)
        .areWellImplemented();
```

The constants are `EQUALS`, `HASH_CODE`, `TO_STRING`, `GETTER`, `SETTER` and `CONSTRUCTOR`.

## Fields

Every non-static field is included. Prefer `exclude` to `include`. A field added later and forgotten in `equals` is then still tested. An include-list hides that field.

```java
import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.FieldPredicate.include;

assertPojoMethodsFor(Order.class, exclude("cached")).areWellImplemented();
assertPojoMethodsFor(Order.class, include("id", "lines")).areWellImplemented();
```

## Nested types

A field whose type the library cannot change is replaced as a whole. To change a field inside that object, pass the nested class as well.

```java
import pl.pojo.tester.api.ClassAndFieldPredicatePair;

assertPojoMethodsFor(
        new ClassAndFieldPredicatePair(Order.class, "customer"),
        new ClassAndFieldPredicatePair(Customer.class, "name")
).areWellImplemented();
```

`equals` then sees two `Customer` instances that differ in `name`. A static nested class is an ordinary class. Test it on its own, or let the outer class replace the nested value as a whole.

## Several classes, or a package

`assertPojoMethodsForAll` tests each class and, when one of them appears as a field of another, changes that field recursively.

```java
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;
import pl.pojo.tester.api.DefaultPackageFilter;

assertPojoMethodsForAll(Order.class, Customer.class).areWellImplemented();

assertPojoMethodsForAll(DefaultPackageFilter.forClass(Order.class))
        .areWellImplemented();
```

`forPackage("com.example.order")` loads the classes in that package and below. The scan reads `*.class` only.

## Constructors and suppliers

Pass the constructor arguments when the class rejects the values the library would generate. The constructor is chosen by the argument types. This does not apply to an abstract class.

```java
assertPojoMethodsFor(Order.class)
        .create(Order.class, new Object[] { 1, "n-1" }, new Class<?>[] { int.class, String.class })
        .areWellImplemented();
```

`ConstructorParameters` holds the same pair. A supplier is used when the class has no constructor the library can call, or when only a builder can create it. The supplier must return a new instance on every call.

```java
assertPojoMethodsFor(Person.class)
        .create(Person.class, () -> Person.builder().name("Ada").build())
        .testing(EQUALS, HASH_CODE)
        .areWellImplemented();
```

The supplier replaces construction of that class only, in that assertion. Lombok `@Data` with `@Builder` still has a constructor, so no supplier is required. `@SuperBuilder` does.

## Final fields

A `final` field keeps the value it was constructed with. `equals`, `hashCode` and `toString` are checked through the other fields.

```java
assertPojoMethodsFor(Key.class)
        .changingFinalFields()
        .areWellImplemented();
```

Turn that on only when the class has to be distinguished by a final field. The write uses reflection and is not available for every final field on every JDK. Setters of `final` fields are not called.

## Names that are not JavaBeans

`withFieldPrefix` strips a prefix before the getter and setter are looked up. `mName` matches `getName`. `m_name` matches `getName` when the prefix is `m_`. A field that does not start with the prefix is matched as written.

```java
assertPojoMethodsFor(Prefixed.class)
        .withFieldPrefix("m")
        .areWellImplemented();
```

`usingFluentAccessors()` also accepts a getter or setter whose name is the field name, which is what Lombok `@Accessors(fluent = true)` generates. It is off by default, so a method that happens to share a field's name is not treated as a getter.

JavaBeans spelling is already followed when the second letter is uppercase: `xCoordinate` matches `getxCoordinate`, and `URL` matches `getURL`.

## Hash codes that collide on purpose

Unequal objects must have different hash codes. `Object.hashCode` does not require that. A constant hash code is legal, and it is also what a forgotten field looks like. Allow the collision only when it is intentional.

```java
assertPojoMethodsFor(ConstantHash.class)
        .allowingSameHashCodeForUnequalObjects()
        .areWellImplemented();
```

## How many objects

The default, `thoroughly()`, builds a combination of the included fields. `quickly()` changes one field at a time. Use it when the combination is too large. Coverage of the class under test will be lower.

```java
assertPojoMethodsFor(Wide.class).quickly().areWellImplemented();
```

## Field values

`using` replaces the changer that produces the next value of a field. Extend `AbstractFieldValueChanger` and implement `canChange` and `increaseValue`. Attach the result to the default chain so the built-in types still change.

```java
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

final AbstractFieldValueChanger changers = DefaultFieldValueChanger.INSTANCE
        .attachNext(new SkuValueChanger());

assertPojoMethodsFor(Order.class).using(changers).areWellImplemented();
```

`DefaultFieldValueChanger` already handles primitives and their wrappers, `String`, enums, `UUID`, the usual collections and maps, arrays, streams, `BigInteger`, `BigDecimal`, and the `java.time` types plus `java.util.Date` and `java.sql.Date`.

## Logging

The library logs through SLF4J. Set `pl.pojo.tester` to `DEBUG` to see which tester ran, which value replaced which field, and how the instances were built. The binding is the one the test already uses.
