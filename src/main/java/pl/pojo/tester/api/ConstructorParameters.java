package pl.pojo.tester.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConstructorParameters {

    private Object[] constructorParameters;
    private Class<?>[] constructorParametersTypes;
}
