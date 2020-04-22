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

import softwareGenius.model.Category;
import softwareGenius.model.Land;
import softwareGenius.model.LeaderBoardRecord;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class WorldControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
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
    public void getGeneralLeaderBoard() throws Exception{
        int offset = 0;
        int limit = 10;
        String url = "/api/world/getLeaderBoard/general/" + offset + "/" + limit;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        LeaderBoardRecord[] leaderBoard= super.mapFromJson(mvcResult.getResponse().getContentAsString(), LeaderBoardRecord[].class);

        for (int i = 1; i < leaderBoard.length; i++){
            assertTrue(leaderBoard[i-1].getCharScore() >= leaderBoard[i].getCharScore());
        }
    }

    @Test
    public void getLeaderBoardByWorldName() throws Exception{
        int offset = 0;
        int limit = 10;
        Category[] categories = {Category.PM, Category.SE,Category.SA,Category.QA};
        for (Category c: categories) {
            String url = "/api/world/getLeaderBoard/category/"+ c.toString() + '/' + offset + "/" + limit;
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            assertEquals(200, mvcResult.getResponse().getStatus());

            LeaderBoardRecord[] leaderBoard= super.mapFromJson(mvcResult.getResponse().getContentAsString(), LeaderBoardRecord[].class);

            for (int i = 1; i < leaderBoard.length; i++){
                assertTrue(leaderBoard[i-1].getCharScore() >= leaderBoard[i].getCharScore());
            }
        }
    }

    @Test
    public void getAllLeaderBoard() throws Exception{
        int offset = 0;
        int limit = 10;
        String url = "/api/world/getLeaderBoard/all/" + offset + "/" + limit;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        LeaderBoardRecord[][] leaderBoard= super.mapFromJson(mvcResult.getResponse().getContentAsString(), LeaderBoardRecord[][].class);

        assertEquals(5, leaderBoard.length);
        // the rest should alr be tested.
    }
}