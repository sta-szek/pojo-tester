package pl.pojo.tester.internal.instantiator;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.Supplier;
import java.util.stream.Stream;

class CollectionInstantiator extends ObjectInstantiator {
    private static final Map<Class<?>, Object> PREPARED_OBJECTS = new LinkedHashMap<>();

    static {
        PREPARED_OBJECTS.put(Stream.class, Stream.empty());
        PREPARED_OBJECTS.put(Stack.class, new Stack<>());
        PREPARED_OBJECTS.put(Vector.class, new Vector<>());
        PREPARED_OBJECTS.put(ArrayList.class, new ArrayList<>());
        PREPARED_OBJECTS.put(LinkedList.class, new LinkedList<>());
        PREPARED_OBJECTS.put(LinkedHashSet.class, new LinkedHashSet<>());
        PREPARED_OBJECTS.put(HashSet.class, new HashSet<>());
        PREPARED_OBJECTS.put(TreeSet.class, new TreeSet<>());
        PREPARED_OBJECTS.put(Iterator.class, new ArrayList<>().iterator());
        PREPARED_OBJECTS.put(LinkedHashMap.class, new LinkedHashMap<>());
        PREPARED_OBJECTS.put(HashMap.class, new HashMap<>());
        PREPARED_OBJECTS.put(Hashtable.class, new Hashtable<>());
        PREPARED_OBJECTS.put(NavigableMap.class, new TreeMap<>());
        PREPARED_OBJECTS.put(TreeMap.class, new TreeMap<>());
        PREPARED_OBJECTS.put(SortedMap.class, new TreeMap<>());
        PREPARED_OBJECTS.put(Map.class, new HashMap<>());
        PREPARED_OBJECTS.put(NavigableSet.class, new TreeSet<>());
        PREPARED_OBJECTS.put(SortedSet.class, new TreeSet<>());
        PREPARED_OBJECTS.put(Set.class, new HashSet<>());
        PREPARED_OBJECTS.put(List.class, new ArrayList<>());
        PREPARED_OBJECTS.put(Deque.class, new LinkedList<>());
        PREPARED_OBJECTS.put(Queue.class, new LinkedList<>());
        PREPARED_OBJECTS.put(Collection.class, new ArrayList<>());
        PREPARED_OBJECTS.put(Iterable.class, new ArrayList<>());
    }

    CollectionInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        return PREPARED_OBJECTS.entrySet()
                               .stream()
                               .filter(this::clazzCanBeAssigned)
                               .map(Map.Entry::getValue)
                               .findFirst()
                               .orElseThrow(createObjectInstantiationExceptionSupplier());
    }

    private boolean clazzCanBeAssigned(final Map.Entry<Class<?>, Object> entry) {
        return entry.getKey()
                    .isAssignableFrom(clazz);
    }

    private Supplier<ObjectInstantiationException> createObjectInstantiationExceptionSupplier() {
        return () -> new ObjectInstantiationException(clazz,
                                                      "There is no declared object for that class. "
                                                      +
                                                      "Please report a bug at https://github.com/sta-szek/pojo-tester");
    }
}
