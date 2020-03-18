package softwareGenius.mapper;
import softwareGenius.model.Question;
public interface QuestionDao {

    Boolean addNewQuestion(question Question);
    Boolean updateQuestion(question Question);
    Boolean deleteQuestion(@Param("questionId") Integer questionId);
    Question[*] getQuestionsByCategory(@Param("category") String category, @Param("difficultyLevel") Integer difficultyLevel,@Param("limit") Integer limit);
}

//@Param("questionId") Integer questionId,@Param("category") String category, @Param("problem") String problem, @Param("choice1") String choice1, @Param("choice2") String choice2, @Param("choice3") String choice3, @Param("choice4") String choice4,  @Param("answer") Integer answer, @Param("difficultyLevel") Integer difficultyLevel