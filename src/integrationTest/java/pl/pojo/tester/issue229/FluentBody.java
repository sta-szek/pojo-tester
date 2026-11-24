package pl.pojo.tester.issue229;

class FluentBody {

    private String message;

    public String getMessage() {
        return message;
    }

    public FluentBody setMessage(final String message) {
        this.message = message;
        return this;
    }
}
