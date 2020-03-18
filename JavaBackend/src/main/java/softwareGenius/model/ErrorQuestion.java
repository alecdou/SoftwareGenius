package softwareGenius.model;

public class ErrorQuestion extends Question{
    public String wrongAnswer;

    public ErrorQuestion(Integer id, String category, String problem, String choice1, String choice2, String choice3, String choice4, Integer answer, Integer difficultyLevel, String wrongAnswer) {
        super(id, category, problem, choice1, choice2, choice3, choice4, answer, difficultyLevel);
        this.wrongAnswer = wrongAnswer;
    }

    public String getWrongAnswer() {
        return wrongAnswer;
    }

    public void setWrongAnswer(String wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
    }
}
