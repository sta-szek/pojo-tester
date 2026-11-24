package pl.pojo.tester.issue240;

import java.util.List;
import java.util.Objects;

class Questionnaire {

    private String id;
    private Answer answer;

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Questionnaire that = (Questionnaire) other;
        return Objects.equals(id, that.id) && Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer);
    }

    static class Answer {

        private String type;
        private List<String> options;

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Answer answer = (Answer) other;
            return Objects.equals(type, answer.type) && Objects.equals(options, answer.options);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, options);
        }
    }
}
