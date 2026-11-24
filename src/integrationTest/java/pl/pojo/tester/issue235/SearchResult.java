package pl.pojo.tester.issue235;

import java.util.Arrays;

class SearchResult {

    private Object[] lastRecordSortValues = new Object[] {"a", "b"};
    private int pageTotal = 2;

    @Override
    public String toString() {
        return "SearchResult{"
                + "lastRecordSortValues=" + Arrays.toString(lastRecordSortValues)
                + ", pageTotal=" + pageTotal
                + '}';
    }
}
