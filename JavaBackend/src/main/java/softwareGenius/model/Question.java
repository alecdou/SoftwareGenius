package softwareGenius.model;

public class Question {
    private Integer questionId;
    /** the category that the question belong to */
    private String category;
    private String problem;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private Integer answer;
    private Integer difficultyLevel;

    public Question(Integer id, String category, String problem, String choice1, String choice2, String choice3, String choice4, Integer answer, Integer difficultyLevel) {
        this.questionId = id;
        this.category = category;
        this.problem = problem;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.difficultyLevel = difficultyLevel;
    }

    public void setId(Integer id) {
        this.questionId = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getId() {
        return questionId;
    }

    public String getCategory() {
        return category;
    }

    public String getProblem() {
        return problem;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public Integer getAnswer() {
        return answer;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

}
