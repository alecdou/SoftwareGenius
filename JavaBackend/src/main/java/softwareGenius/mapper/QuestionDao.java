package softwareGenius.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import softwareGenius.model.Question;
import java.util.List;
/**
 * Used to interact with question database
 */
@Mapper
@Component
public interface QuestionDao {
    /**
     * Add question to the database
     * @param question question to be added
     */
    Boolean addNewQuestion(Question question);
    /**
     * Update the question
     * @param question question to be updated
     */
    Boolean updateQuestion(Question question);
    /**
     * Delete the question
     * @param questionId the id of the question to be deleted
     */
    Boolean deleteQuestion(@Param("questionId") Integer questionId);
    /**
     * Get the question by Id
     * @param questionId the id of the question to be deleted
     */
    Question getQuestion(@Param("questionId") Integer questionId);
    /**
    /**
     * Get a number of qustions according to category and difficulty
     * @param category the categor that the question belongs to
     * @param difficultyLevel the difficulty level of the question
     * @param limit number of questions needed
     */
    List<Question> getQuestionsByCategory(@Param("category") String category, @Param("difficultyLevel") Integer difficultyLevel,@Param("limit") Integer limit);
    /**
     * update that one more question is answered by one user
     * @param questionId the id of the question
     */
    Boolean answerQuestion(@Param("questionId") Integer questionId);
    /**
     * update that one more question is answered by one user correctly
     * @param questionId the id of the question
     */
    Boolean correctQuestion(@Param("questionId") Integer questionId);
}

//@Param("questionId") Integer questionId,@Param("category") String category, @Param("problem") String problem, @Param("choice1") String choice1, @Param("choice2") String choice2, @Param("choice3") String choice3, @Param("choice4") String choice4,  @Param("answer") Integer answer, @Param("difficultyLevel") Integer difficultyLevel