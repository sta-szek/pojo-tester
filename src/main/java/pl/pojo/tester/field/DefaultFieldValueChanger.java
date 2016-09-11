package pl.pojo.tester.field;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pojo.tester.field.collections.CollectionsFieldValueChanger;
import pl.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new EnumValueChanger().attachNext(AbstractPrimitiveValueChanger.INSTANCE)
                                                                                   .attachNext(CollectionsFieldValueChanger.INSTANCE);
}
