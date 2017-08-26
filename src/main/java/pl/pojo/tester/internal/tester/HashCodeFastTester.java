package pl.pojo.tester.internal.tester;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

/**
 * Created by me on 26/08/17.
 */
public class HashCodeFastTester extends HashCodeTester {

  public HashCodeFastTester() {
    super();
    setThoroughTesting(false);
  }

  public HashCodeFastTester(
      AbstractFieldValueChanger abstractFieldValueChanger) {
    super(abstractFieldValueChanger);
    setThoroughTesting(false);
  }
}
