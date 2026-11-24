package pl.pojo.tester.issue202;

class BadSetter {

    private int qp;
    private int db;

    public void setQp(final int qp) {
        this.qp = qp;
    }

    public void setDb(final int db) {
        this.db = qp;
    }
}
