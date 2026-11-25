# What is checked

This is the behaviour of `0.8.0-SNAPSHOT`. A failure is an `AbstractAssertionError`. The message names the class, the field and the two values that were compared. The test fails at `areWellImplemented()`. It does not return a boolean.

Nothing here generates `equals`, `hashCode`, `toString` or accessors, and nothing asks for an annotation on the class under test.

## Which fields

Included by default: every non-static field declared on the class and on its superclasses, including non-public fields. Synthetic fields are skipped.

Excluded even when named: `static` fields. A field you pass to `exclude` is not changed and must not affect `equals` or `hashCode`. If it still does, the test fails.

`include` tests only the named fields. Prefer `exclude`. A field added later and left out of `equals` is still found. An include-list hides it.

## `EQUALS`

For the instances the library builds:

- an object equals itself
- it does not equal `null`
- it does not equal an instance of another type
- two instances with the same included values are equal
- changing one included field makes them unequal
- the relation is symmetric, transitive and consistent for those instances
- each included field that is not primitive and not `final` is also set to `null` on one side

## `HASH_CODE`

- equal instances have the same hash code
- the value is stable for one instance
- unequal instances have different hash codes, unless `allowingSameHashCodeForUnequalObjects()` is set

`Object.hashCode` does not require the last rule. It stays on because a collision is usually a field left out of `hashCode`.

## `TO_STRING`

The text must change when an included field changes, and stay the same when the field does not. A `String` or `char` may be wrapped in single quotes. An `Object[]` printed with `Arrays.toString` is accepted. Primitive arrays are read as arrays, not as `Object.toString`.

## Getters and setters

A getter must return the value held in the field. A setter must write the value it is given. Every included field is set to a value different from the one in the other fields, so a setter that copies the wrong field fails.

A setter may return the class under test (`return this`). A method that returns `Object` is not a setter. A setter of a `final` field is not called.

JavaBeans names are used. `xCoordinate` matches `getxCoordinate`. `URL` matches `getURL`. A `boolean` may use `is`.

`withFieldPrefix("m")` maps `mName` to `getName` and `setName`. `withFieldPrefix("m_")` maps `m_name` the same way. A field that does not start with the prefix is matched as written.

`usingFluentAccessors()` also accepts a getter or setter whose name is the field name. That is the shape of Lombok `@Accessors(fluent = true)`. It is off by default.

An enum is not tested for setters. A setter of an enum-typed field on an ordinary class still is. A field whose type is the raw class `java.lang.Enum` is set to `RoundingMode.UP`. The `Enum` constructor is not called.

## Constructors

Each non-synthetic constructor is called, except a non-public constructor whose only parameter is a builder type nested in the same class. That is the constructor Lombok `@SuperBuilder` generates. A public constructor on the same class, including a public no-arg constructor, is still called. Arguments come from the same value changers as fields. Pass `create` when the constructor rejects those values. The constructor is chosen by the argument types.

`create(Class, Supplier)` replaces construction for that class only. The supplier must return a new instance on every call. Use it for a builder, or for a type that has no constructor the library can call. Lombok `@Data` with `@Builder` still has a constructor. `@SuperBuilder` together with a public no-arg constructor is tested through that constructor. The protected builder constructor is skipped.

This form does not apply to an abstract class. An abstract class can still be tested when a concrete subclass is what gets constructed, or when the abstract type only appears as a field.

## Several classes

`assertPojoMethodsForAll` runs the assertion on each class. When one of those classes is the type of a field on another, that field is changed by rebuilding the nested instance, not only by replacing it with another empty one.

`assertPojoMethodsFor` does the same when you pass a `ClassAndFieldPredicatePair` for the outer class and one for the nested class. A static nested class is an ordinary class. Test it on its own, or let the outer class replace the nested value as a whole.

`DefaultPackageFilter.forPackage("com.example.order")` and `forClass(Order.class)` load `*.class` files from that package and its subpackages. The result is passed to `assertPojoMethodsForAll`.

## How many instances

`thoroughly()` is the default. It combines the included fields, up to 12 on one class (`ThoroughFieldPermutator.MAX_FIELDS`). A class with more fields, including a nested one, uses the same linear strategy as `quickly()`, and a warning names the class and the field count. `quickly()` uses that linear strategy for every class. A class checked that way is covered less.

## Final fields

A `final` field keeps the value it was given at construction. `equals`, `hashCode` and `toString` are checked through the other fields.

`changingFinalFields()` also writes a new value into a `final` field. The write uses reflection and is not available for every `final` field on every JDK. Leave it off unless the class can only be distinguished by that field.

## Values

`DefaultFieldValueChanger` produces the next value of a field. It handles primitives and their wrappers, `String`, enums, `UUID`, the usual collections and maps, arrays, streams, `BigInteger`, `BigDecimal`, the `java.time` types, `java.util.Date` and `java.sql.Date`.

`using` replaces that chain. A custom changer extends `AbstractFieldValueChanger` and implements `canChange` and `increaseValue`. Attach it with `DefaultFieldValueChanger.INSTANCE.attachNext(...)` so the built-in types still change.

An empty collection, map or array is a different value from `null`. A boxed number, `char` or `boolean` that is `null` becomes `1`, `'a'` or `true`. The value already stored in a non-null field is changed as before.

## What this does not do

- It does not test a record. The methods are generated.
- It does not prove the full `equals` contract the way a library written only for that contract does. Symmetry and transitivity are checked on the instances this library builds, not on every possible subclass.
- It does not call package-private or private methods other than the accessors, `equals`, `hashCode`, `toString` and constructors it found.
- Logging goes through SLF4J. `DEBUG` on `pl.pojo.tester` prints the tester, the field and the two values.
