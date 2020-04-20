package softwareGenius;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softwareGenius.api.PlayerController;
import softwareGenius.model.User;
import softwareGenius.service.AccountService;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerControllerTest extends AbstractTest{

    // A connection to the database
    Connection connection;

    @Autowired
    private Environment env;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Closes the database connection.
     *
     * @return true if the closing was successful, false otherwise
     */
    public boolean disconnectDB() {
        try{
            connection.close();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Test
    public void testAddUser() throws Exception{
        String postUri = "/getUser";

        // construct a new user object
        User user = new User();
        user.setUserName("Rebecca");
        user.setOverallExp(10000000);
        user.setAccountType("fairy");

        // map the object to json in string
        String inputJson = super.mapToJson(user);

        // mock post
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(postUri)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(inputJson)).andReturn();

        // assert response status
        int status = mvcPostResult.getResponse().getStatus();
        assertEquals(201, status);

        // method 1: get the actual user by mock another get request
        String returnedUserId = mvcPostResult.getResponse().getContentAsString();

        String getUri = String.format("/getUser/{%s}", returnedUserId);
        MvcResult mvcGetResult = mvc.perform(MockMvcRequestBuilders.get(getUri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String actualUser = mvcGetResult.getResponse().getContentAsString();

        assertEquals(actualUser, inputJson);

        // method 2:
        String dbUrl = env.getProperty("spring.datasource.url");

        //
        String sql = "select userId, username, realName, userAvatar, password, email, accountType, isAdmin, overallExp " +
                        "from user " +
                        "where userId = ?";


        try (Connection conn = this.connect()){

             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, Integer.parseInt(returnedUserId));
             ResultSet result = pstmt.executeQuery();
            assertEquals(result, inputJson);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

    @Test
    public void testGetUser() throws Exception{

    }
}
