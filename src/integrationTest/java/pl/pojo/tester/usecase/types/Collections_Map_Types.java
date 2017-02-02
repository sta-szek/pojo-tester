package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collections_Map_Types {

    private HashMap hashMap;
    private Hashtable hashtable;
    private LinkedHashMap linkedHashMap;
    private Map map;
    private SortedSet sortedSet;
    private TreeMap treeMap;

}
