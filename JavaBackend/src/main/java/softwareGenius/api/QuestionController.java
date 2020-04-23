package softwareGenius.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import softwareGenius.model.*;
import softwareGenius.service.*;

import java.util.List;

@RequestMapping("api/question")
@RestController
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping(path = "{questionId}")
    public Question getQuestionById(@PathVariable Integer questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "question not found"
            );
        }
        return question;
    }

    @GetMapping(path = "AllQuestions")
    public List<Question> getAllQuestions() {
        List<Question> questions=questionService.getAllQuestion();
        if (questions == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "question not found"
            );
        }
        return questions;
    }
    
    @GetMapping(path = "score/{qid}")
    public Float qnsScore(@PathVariable Integer qid) {
        Float accuracy = questionService.getAccuracy(qid);
        if (accuracy == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "question not found"
            );
        }
        return accuracy;
    }


    @GetMapping(path = "{category}/{difficultyLevel}/{limit}")
    public List<Question> getQuestionByCategory(@PathVariable String category, @PathVariable Integer difficultyLevel, @PathVariable Integer limit) {
        return questionService.getQuestionsByCategory(category,difficultyLevel,limit);
    }

    @DeleteMapping(path="{questionId}")
    public void deleteQuestionById(@PathVariable Integer questionId){
        try{
            questionService.deleteQuestion(questionId);
        } catch(Exception e){
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "No such question"
            );
        }
    }




    @PostMapping(path = "Answered")
    public void qnsAnswered(@RequestBody Integer[] questionIds) {
        questionService.addQnsAnswered(questionIds);
    }


    @PostMapping(path = "correctAnswered")
    public void qnsCorrectlyAnswered(@RequestBody Integer[] questionIds) {
        questionService.addQnsCorrectlyAnswered(questionIds);
    }

    @PostMapping(path="addQuestion")
    public Boolean addQuestion(@RequestBody Question question){
        Boolean result= questionService.addNewQuestion(question);
        return result;
    }

}
