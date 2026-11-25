package pl.pojo.tester.issue329;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkillChange {

    private String name;
    private Enum validityType;
}
