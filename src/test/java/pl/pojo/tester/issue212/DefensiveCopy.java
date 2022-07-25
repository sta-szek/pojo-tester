package pl.pojo.tester.issue212;


import lombok.Data;

import java.util.Arrays;

@Data
public class DefensiveCopy {
    private BBB[] bbb = new BBB[]{new BBB(), new BBB()};

    public BBB[] getBbb() {
        return bbb == null ? null : Arrays.copyOf(bbb, bbb.length);
    }

    public void setBbb(final BBB[] bbb) {
        this.bbb = (bbb == null) ? null : Arrays.copyOf(bbb, bbb.length);
    }
}

