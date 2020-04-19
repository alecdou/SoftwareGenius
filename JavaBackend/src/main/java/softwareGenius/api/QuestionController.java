package softwareGenius.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        return questionService.getQuestion(questionId);
    }


    @GetMapping(path = "score/{qid}")
    public Float qnsScore(@PathVariable Integer qid) {
        return questionService.getAccuracy(qid);
    }


    @GetMapping(path = "{category}/{difficultyLevel}/{limit}")
    public List<Question> getQuestionByCategory(@PathVariable String category, @PathVariable Integer difficultyLevel, @PathVariable Integer limit) {
        return questionService.getQuestionsByCategory(category,difficultyLevel,limit);
    }

    @DeleteMapping(path="{questionId}")
    public void deleteQuestionById(@PathVariable Integer questionId){
        questionService.deleteQuestion(questionId);
    }

    @PostMapping(path = "Answered")
    public void qnsAnswered(@RequestBody Integer[] questionIds) {
        questionService.addQnsAnswered(questionIds);
    }

//    @PostMapping(path = "Score")
//    public Float qnsScore(@RequestBody Integer qid) {
//        return questionService.getAccuracy(qid);
//    }

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
