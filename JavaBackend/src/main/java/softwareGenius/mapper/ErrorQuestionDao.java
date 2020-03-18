package softwareGenius.mapper;

import org.apache.ibatis.annotations.Param;
import softwareGenius.model.ErrorQuestion;

public interface ErrorQuestionDao {
    void addNewErrorQuestion(@Param("userId") Integer userId, @Param("questionId") Integer questionId, @Param("wrongAnswer") String wrongAnswer);
    ErrorQuestion[] getErrorQuestionByUserId(@Param("userId") Integer userId);
}
