package pl.pojo.tester.usecase.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWithAllFieldTypes {

    private UUID uuid;
    private Enum enum_;
    private Boolean boolean_;
    private Byte byte_;
    private Character character_;
    private Double double_;
    private Integer integer_;
    private Long long_;
    private Short short_;
    private String string_;
    private Float float_;
    private int[] array;
    private Stream stream;
    private ArrayList arrayList;
    private Deque deque;
    private HashSet hashSet;
    private LinkedHashSet linkedHashSet;
    private LinkedList linkedList;
    private List list;
    private Queue queue;
    private Set set;
    private SortedSet sortedSet;
    private Stack stack;
    private TreeSet treeSet;
    private Vector vector;
    private HashMap hashMap;
    private Hashtable hashtable;
    private LinkedHashMap linkedHashMap;
    private Map map;
    private SortedMap sortedMap;
    private TreeMap treeMap;
    private Iterator iterator;
    private Iterable iterable;
    private BigInteger bigInteger;
    private BigDecimal bigDecimal;
}
