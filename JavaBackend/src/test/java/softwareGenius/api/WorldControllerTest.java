package softwareGenius.api;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import softwareGenius.AbstractTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WorldControllerTest extends AbstractTest {
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
    public boolean disconnectDB(Connection connection) {
        try{
            connection.close();
        }
        catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Test
    void getCharByWorldId() {
    }

    @Test
    void getLandsByWorldId() {
    }

    @Test
    void getLandsByUserIdAndCategory() {
    }

    @Test
    void getWorldListByUserId() {
    }

    @Test
    void testGetWorldListByUserId() {
    }

    @Test
    void getCharsByUserId() {
    }

    @Test
    void initNewWorld() {
    }

    @Test
    void getAllLeaderBoard() {
    }

    @Test
    void getGeneralLeaderBoard() {
    }

    @Test
    void getLeaderBoardByWorldName() {
    }

    @Test
    void changeOwner() {
    }
}