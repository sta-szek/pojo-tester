package pl.pojo.tester.internal.field.date;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public final class DefaultDateAndTimeFieldValueChanger {
    public static final AbstractFieldValueChanger INSTANCE = new ZonedDateTimeFieldValueChanger().attachNext(new DateFieldValueChanger())
                                                                                                 .attachNext(new LocalDateFieldValueChanger())
                                                                                                 .attachNext(new LocalDateTimeFieldValueChanger())
                                                                                                 .attachNext(new LocalTimeFieldValueChanger())
                                                                                                 .attachNext(new SqlDateFieldValueChanger())
                                                                                                 .attachNext(new InstantFieldValueChanger());

    private DefaultDateAndTimeFieldValueChanger() {
    }
}
