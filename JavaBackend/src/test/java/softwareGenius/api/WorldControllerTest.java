package softwareGenius.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softwareGenius.AbstractTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import softwareGenius.model.Land;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class WorldControllerTest extends AbstractTest {
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
        String dbUrl = env.getProperty("spring.datasource.url");
        Connection conn = null;
        try {
            assert dbUrl != null;
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Closes the database connection.
     *
     * @return true if the closing was successful, false otherwise
     */
    private boolean disconnectDB(Connection connection) {
        try{
            connection.close();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Test
    public void getCharByWorldId() throws Exception {
    }

    @Test
    public void getLandsByWorldId() throws Exception {
        int inputWorldId = 1;
        String url = "/api/world/getLandsByWorldId/" + inputWorldId;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        Land[] landList = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Land[].class);
        assertEquals(24, landList.length);
        for (Land l: landList) {
            assertEquals(inputWorldId, l.getWorldId());
        }
    }

    @Test
    public void getLandsByUserIdAndCategory() {
    }

    @Test
    public void getWorldListByUserId() {
    }

    @Test
    public void testGetWorldListByUserId() {
    }

    @Test
    public void getCharsByUserId() {
    }

    @Test
    public void initNewWorld() {
    }

    @Test
    public void getAllLeaderBoard() {
    }

    @Test
    public void getGeneralLeaderBoard() {
    }

    @Test
    public void getLeaderBoardByWorldName() {
    }

    @Test
    public void changeOwner() {
    }
}