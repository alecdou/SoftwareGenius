package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import softwareGenius.model.ErrorQuestion;

/**
 * Used to record and get questions that were wrongly answered by the user
 */
@Mapper
public interface ErrorQuestionDao {
    /**
     * Record questions that were wrongly answered by a student
     * @param userId id of the student
     * @param questionId id of the question
     * @param wrongAnswer wrong answer by the student
     */
    void addNewErrorQuestion(@Param("userId") Integer userId, @Param("questionId") Integer questionId, @Param("wrongAnswer") String wrongAnswer);

    /**
     * Get questions that were wrongly answered by a student
     * @param userId id of the student
     * @return a list of ErrorQuestion objects
     */
    ErrorQuestion[] getErrorQuestionByUserId(@Param("userId") Integer userId);
}
