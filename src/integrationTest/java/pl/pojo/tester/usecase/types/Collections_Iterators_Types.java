package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collections_Iterators_Types {

    private Iterator iterator;
    private Iterable iterable;
}
