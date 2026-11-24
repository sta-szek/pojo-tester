package pl.pojo.tester.issue202;

class BadGetter {

    private int qp;
    private int db;

    public int getQp() {
        return db;
    }

    public int getDb() {
        return qp;
    }
}
