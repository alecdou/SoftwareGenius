package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.QuestionDao;
import softwareGenius.model.Question;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    private QuestionDao questionDao;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

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
    public Question getQuestion(Integer qid) {
        return questionDao.getQuestion(qid);
    }

    public List<Question> getAllQuestion(){return questionDao.getAllQuestion();}

    public List<Question> getQuestionsByCategory(String category, Integer difficultyLevel, Integer limit) {
        return questionDao.getQuestionsByCategory(category, difficultyLevel, limit);
    }

    public Boolean addQnsAnswered(Integer[] questionIds) {
        for (Integer qid : questionIds) {
            questionDao.answerQuestion(qid);
        }
        return true;
    }

    public Boolean addQnsCorrectlyAnswered(Integer[] questionIds) {
        for (Integer qid : questionIds) {
            questionDao.correctQuestion(qid);
        }
        return true;
    }

    public Float getAccuracy(Integer qid){
        Question q=questionDao.getQuestion(qid);
        Integer answered=q.getUserAnswered();
        if (answered==0){
            return (float) 0;
        }
        Integer correct=q.getUserCorrect();
        return (float) correct/answered;
    }

    public Integer calculateScore(Integer[] questionIds){
        int score=0;
        for (int i = 0; i < questionIds.length; i++){
            Question questionI = questionDao.getQuestion(questionIds[i]);
            score += questionI.getDifficultyLevel();
        }
        return score;
    }





}
