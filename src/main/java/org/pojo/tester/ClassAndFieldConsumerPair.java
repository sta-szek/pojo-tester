package org.pojo.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
class ClassAndFieldConsumerPair {

    private final Class<?> testedClass;
    private final Consumer<Object> consumer;

}