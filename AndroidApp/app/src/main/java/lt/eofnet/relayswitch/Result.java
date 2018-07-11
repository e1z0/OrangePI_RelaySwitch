package lt.eofnet.relayswitch;

/*
 * 2018 (c) justinas@eofnet.lt, EofNET LAB10
 */

public class Result {

    private String answer;

    public Result() {
    }

    public Result(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Result{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
