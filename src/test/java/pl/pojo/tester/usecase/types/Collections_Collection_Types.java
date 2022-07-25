package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collections_Collection_Types {

    private ArrayList arrayList;
    private Deque deque;
    private HashSet hashSet;
    private LinkedHashSet linkedHashSet;
    private LinkedList linkedList;
    private List list;
    private Queue queue;
    private Set set;
    private SortedMap sortedMap;
    private Stack stack;
    private TreeSet treeSet;
    private Vector vector;
}
