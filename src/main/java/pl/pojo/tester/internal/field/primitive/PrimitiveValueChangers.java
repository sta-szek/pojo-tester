package pl.pojo.tester.internal.field.primitive;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public final class PrimitiveValueChangers {

    public static final AbstractFieldValueChanger INSTANCE =
            new BooleanValueChanger().attachNext(new ByteValueChanger())
            .attachNext(new CharacterValueChanger())
            .attachNext(new DoubleValueChanger())
            .attachNext(new IntegerValueChanger())
            .attachNext(new LongValueChanger())
            .attachNext(new ShortValueChanger())
            .attachNext(new FloatValueChanger());

    private PrimitiveValueChangers() {
    }
}
