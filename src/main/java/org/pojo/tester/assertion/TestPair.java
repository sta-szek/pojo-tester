package org.pojo.tester.assertion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TestPair {

    private final String testName;
    private final Class<?> testClass;

}
