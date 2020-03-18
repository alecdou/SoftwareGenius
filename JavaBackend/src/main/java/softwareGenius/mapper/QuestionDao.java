package softwareGenius.mapper;
import org.apache.ibatis.annotations.Param;
import softwareGenius.model.Question;
import java.util.List;

public interface QuestionDao {
    Boolean addNewQuestion(Question question);
    Boolean updateQuestion(Question question);
    Boolean deleteQuestion(@Param("questionId") Integer questionId);
    List<Question> getQuestionsByCategory(@Param("category") String category, @Param("difficultyLevel") Integer difficultyLevel,@Param("limit") Integer limit);
}

//@Param("questionId") Integer questionId,@Param("category") String category, @Param("problem") String problem, @Param("choice1") String choice1, @Param("choice2") String choice2, @Param("choice3") String choice3, @Param("choice4") String choice4,  @Param("answer") Integer answer, @Param("difficultyLevel") Integer difficultyLevel