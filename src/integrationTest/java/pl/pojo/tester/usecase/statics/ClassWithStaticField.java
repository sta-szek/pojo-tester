package pl.pojo.tester.usecase.statics;

import lombok.Data;

@Data
class ClassWithStaticField {

    private static final String STATIC_FINAL = "test";
    private int a;
    private int b;

}
