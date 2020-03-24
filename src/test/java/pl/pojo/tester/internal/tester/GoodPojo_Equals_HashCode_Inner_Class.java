package pl.pojo.tester.internal.tester;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodPojo_Equals_HashCode_Inner_Class {
    private InternalClass internalClass;

    @Data
    public static class InternalClass {
        String id;
    }
}