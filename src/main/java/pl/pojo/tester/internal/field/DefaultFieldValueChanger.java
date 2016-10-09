package pl.pojo.tester.internal.field;


import pl.pojo.tester.internal.field.collections.CollectionsFieldValueChanger;
import pl.pojo.tester.internal.field.primitive.AbstractPrimitiveValueChanger;


public class DefaultFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new EnumValueChanger()
            .attachNext(AbstractPrimitiveValueChanger.INSTANCE)
            .attachNext(CollectionsFieldValueChanger.INSTANCE)
            .attachNext(new StringValueChanger());

    private DefaultFieldValueChanger() {
    }
}
