package softwareGenius.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softwareGenius.AbstractTest;
import softwareGenius.model.Category;
import softwareGenius.model.Question;
import softwareGenius.model.User;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void testAddQuestion() throws Exception{
        String postUri = "/api/question/addQuestion";

        // construct a new question object
        Question ques = new Question();
        ques.setCategory("SA");
        ques.setDifficultyLevel(3);
        ques.setAnswer(1);
        ques.setChoice1("choice 1");
        ques.setChoice2("choice 2");
        ques.setChoice3("choice 3");
        ques.setChoice4("choice 4");
        ques.setProblem("problem 1");
        ques.setuserAnswered(0)
        ques.setuserCorrect(0)
        // map the object to json in string
        String inputJson = super.mapToJson(ques);

        // mock post
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // assert response status
        int status = mvcPostResult.getResponse().getStatus();
        assertEquals(200, status);

        String sql = "SELECT last_insert_rowid()";

        // connect to db and query
        try (Connection conn = this.connect()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            Integer question_id = result.next() ? result.getInt("question_id") : null;
            String getUri = String.format("/api/question/%s", question_id);
            MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(getUri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            ques.setId(question_id);
            String actualQues = mvcGetResult.getResponse().getContentAsString();
            assertEquals(super.mapToJson(ques), actualQues);
            disconnectDB(conn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGetQuestion() throws Exception{
        int inputQuesId = 1;
        String uri = "/api/question/" + inputQuesId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // check status
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testGetAllQuestions() throws Exception{
        String uri = "/api/question/AllQuestions";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // check status
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        // convert json type to class object
        Question[] actualQuesList = super.mapFromJson(content, Question[].class);
        assertTrue(actualQuesList.length > 0);
    }

    @Test
    public void testQnsScore() throws Exception{
        int inputQuesId = 1;
        String uri = "/api/question/score/" + inputQuesId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // check status
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    @Test
    public void testGetQuestionByCategory() throws Exception{
        String uri = "/api/question/SE/1/10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // check status
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        // convert json type to class object
        Question[] actualQuesList = super.mapFromJson(content, Question[].class);
        assertTrue(actualQuesList.length == 10);
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        // positive test
        String sql = "SELECT last_insert_rowid()";
        try (Connection conn = this.connect()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            Integer question_id = result.next() ? result.getInt("question_id") : null;

            String uri = "/api/question/" + inputQuesId;
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            disconnectDB(conn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
//        assertEquals(content, "Product is deleted successsfully");
    }


    @Test
    public void testQnsAnswered() throws Exception{
        String postUri = "/api/question/Answered";

        // construct new question object list
        Question ques1 = new Question();
        ques1.setCategory("QA");
        ques1.setDifficultyLevel(3);
        ques1.setAnswer(1);
        ques1.setChoice1("choice 1");
        ques1.setChoice2("choice 2");
        ques1.setChoice3("choice 3");
        ques1.setChoice4("choice 4");
        ques1.setProblem("problem 1");
        ques1.setuserAnswered(0)
        ques1.setuserCorrect(0)
        Question ques2 = new Question();
        ques2.setCategory("SE");
        ques2.setDifficultyLevel(3);
        ques2.setAnswer(1);
        ques2.setChoice1("choice 1");
        ques2.setChoice2("choice 2");
        ques2.setChoice3("choice 3");
        ques2.setChoice4("choice 4");
        ques2.setProblem("problem 2");
        ques2.setuserAnswered(0)
        ques2.setuserCorrect(0)

        Integer quesLength = 2;

        Question [] quesList = new Question[quesLength];
        quesList[0] = ques1;
        quesList[1] = ques2;

        // map the object to json in string
        String inputJson = super.mapToJson(quesList);

        // mock post
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // assert response status
        int status = mvcPostResult.getResponse().getStatus();
        assertEquals(200, status);

        String sql = "SELECT last_insert_rowid() from question";

        String getUri;

        // connect to db and query
        try (Connection conn = this.connect()){
            for (int i = 0; i < quesLength; i++) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet result = pstmt.executeQuery();
                Integer question_id = result.next() ? result.getInt("question_id") : null;
                getUri = String.format("/api/question/%s", question_id);
                MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(getUri)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

                quesList[i].setId(question_id);
                String actualQues = mvcGetResult.getResponse().getContentAsString();
                assertEquals(super.mapToJson(quesList[i]), actualQues);
            }
            disconnectDB(conn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testQnsCorrectlyAnswered() throws Exception{
        String postUri = "/api/question/correctAnswered";

        // construct new question object list
        Question ques1 = new Question();
        ques1.setCategory("QA");
        ques1.setDifficultyLevel(3);
        ques1.setAnswer(1);
        ques1.setChoice1("choice 1");
        ques1.setChoice1("choice 2");
        ques1.setChoice3("choice 3");
        ques1.setChoice4("choice 4");
        ques1.setProblem("problem 1");

        ques1.setuserAnswered(0)
        ques1.setuserCorrect(0)
        Question ques2 = new Question();
        ques2.setCategory("SE");
        ques2.setDifficultyLevel(3);
        ques2.setAnswer(1);
        ques2.setChoice1("choice 1");
        ques2.setChoice1("choice 2");
        ques2.setChoice3("choice 3");
        ques2.setChoice4("choice 4");
        ques2.setProblem("problem 2");
        ques2.setuserAnswered(0)
        ques2.setuserCorrect(0)
        Integer quesLength = 2;

        Question [] quesList = new Question[quesLength];
        quesList[0] = ques1;
        quesList[1] = ques2;

        // map the object to json in string
        String inputJson = super.mapToJson(quesList);

        // mock post
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // assert response status
        int status = mvcPostResult.getResponse().getStatus();
        assertEquals(200, status);

        String sql = "SELECT last_insert_rowid() from question";

        String getUri;

        // connect to db and query
        try (Connection conn = this.connect()){
            for (int i = 0; i < quesLength; i++) {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet result = pstmt.executeQuery();
                Integer question_id = result.next() ? result.getInt("question_id") : null;
                getUri = String.format("/api/question/%s", question_id);
                MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(getUri)
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                quesList[i].setId(question_id);
                String actualQues = mvcGetResult.getResponse().getContentAsString();
                assertEquals(super.mapToJson(quesList[i]), actualQues);
            }
            disconnectDB(conn);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
