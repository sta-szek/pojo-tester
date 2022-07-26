# Why Should I Use `POJO-TESTER`? {#why-use-it}
There are several reasons you should use it.


## `POJO-TESTER` makes you more productive {#productivity}
Before `POJO-TESTER`, you had to write number of tests to check that you implemented your `pojo-methods` well. Let's see. 

For simple `Pojo` class:
```java
public class Pojo {
    private int a;
    private int b;
    // getters and setters
    // equals and hashCode
    // toString
}
```

You have to write several (8 in a good test case) tests:
```java
@Test
public void Should_Equal_Itself() {
    // given
    final Pojo pojo = new Pojo();

    // when
    final boolean result = pojo.equals(pojo);

    // then
    assertTrue(result);
}

@Test
public void Should_Equal_Other_Object_With_Same_Values() {
    // given
    final Pojo pojo1 = new Pojo();
    final Pojo pojo2 = new Pojo();

    // when
    final boolean result = pojo1.equals(pojo2);

    // then
    assertTrue(result);
}

@Test
public void Should_Not_Equal_Null() {
    // given
    final Pojo pojo = new Pojo();

    // when
    final boolean result = pojo.equals(null);

    // then
    assertFalse(result);
}

@Test
public void Should_Not_Equal_Other_Object_With_Different_Values() {
    // given
    final Pojo pojo1 = new Pojo();
    final Pojo pojo2 = new Pojo();
    pojo2.setA(1);

    // when
    final boolean result = pojo1.equals(pojo2);

    // then
    assertFalse(result);
}

@Test
public void Should_Not_Equal_Object_Of_Different_Type() {
    // given
    final Pojo pojo = new Pojo();

    // when
    final boolean result = pojo.equals(new String());

    // then
    assertFalse(result);
}

@Test
public void Should_Generate_Same_Hash_Code_Every_Time() {
    // given
    final Pojo pojo = new Pojo();

    // when
    final int result1 = pojo.hashCode();
    final int result2 = pojo.hashCode();

    // then
    assertEquals(result1, result2);
}

@Test
public void Should_Generate_Same_Hash_Code_For_Equal_Objects() {
    // given
    final Pojo pojo1 = new Pojo();
    final Pojo pojo2 = new Pojo();

    // when
    final int result1 = pojo1.hashCode();
    final int result2 = pojo2.hashCode();

    // then
    assertEquals(result1, result2);
}

@Test
public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
    // given
    final Pojo pojo1 = new Pojo();
    final Pojo pojo2 = new Pojo();
    pojo2.setA(1);

    // when
    final int result1 = pojo1.hashCode();
    final int result2 = pojo2.hashCode();

    // then
    assertNotEquals(result1, result2);
}
```

Do you really want to write all those tests each time you create a new `pojo` class?

Not really, just use `POJO-TESTER`

## Improve your coverage {#improve-coverage}

In example above, you made it! You wrote 8 tedious tests! Or just copied them from another test class and changed the class name. You changed it, didn't you?

But still, this gives you coverage at low level.

![](coverage-without-pojo.png)

`POJO-TESTER` gives you **100% coverage**!

![](coverage-with-pojo-tester.png)

Will you improve your coverage with hand-written tests?

Not really, just use `POJO-TESTER`

## Be resistant to bugs {#resistant-bugs}
Yeah, be resistant to bugs in your `pojo-methods`! 

Let's say you not a big fan of coverage. You don't even want to write tests :)

Imagine a situation: you added a field in your `pojo` class:
```java
public class Pojo {
    private int a;
    private int b;
    private int c;
    // getters and setters
    // equals and hashCode
    // toString
}
```
But you just forgot to include that field in your `equals` and `hashCode` methods.

What will happen?

All tests will pass, because you didn't write your tests for all the fields (of course, it would take one additional test per each field). You are pretty sure that you code is perfect! Yet you have 90% coverage. And who could guess that production code fails because of a `hashCode` method? 

Do you want to be responsible for it?

Not really, just use `POJO-TESTER`.

## Be resistant to changes {#resistant-changes}
Yeah, we don't forget to write an additional test for each extra field. But what happens if you have to remove fields, getters or setters?

Would you maintain all the broken tests?

Not really, just use `POJO-TESTER`

## <a id="avoid-boiler-plate-code"/> Don't write boiler plate tests
Ok, lets assume you write `pojo-methods` tests by yourself. You even maintain your implementation-depending tests (really wrong tests).

How about `getters`, `setters` and `toString` tests? Will you write them all again? Really?

Do you still want to be copy-pasting all `pojo-methods` tests?

No matter how many fields you have, no matter if you write `pojo-methods` for your own or generate them. With `POJO-TESTER` you will have **100% coverage**!

Just use `POJO-TESTER`:

```java
@Test
public void Should_Pass_All_Pojo_Tests() {
    // given
    final Class<?> classUnderTest = Pojo.class;

    // when

    // then
    assertPojoMethodsFor(classUnderTest).areWellImplemented();
}
```
