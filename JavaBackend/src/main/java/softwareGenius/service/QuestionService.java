package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.QuestionDao;
import softwareGenius.model.Question;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;


    public Boolean addNewQuestion(Question question) {
        questionDao.addNewQuestion(question);
        return true;
    }

    public Boolean updateQuestion(Question question) {
        questionDao.updateQuestion(question);
        return true;
    }

    public Boolean deleteQuestion(Integer qid) {
        questionDao.deleteQuestion(qid);
        return true;
    }
    public List<Question> getQuestionsByCategory(String category, Integer difficultyLevel, Integer limit) {
        return questionDao.getQuestionsByCategory(category, difficultyLevel, limit);
    }




}
