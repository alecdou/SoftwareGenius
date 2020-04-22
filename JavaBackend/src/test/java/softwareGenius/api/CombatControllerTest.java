package softwareGenius.api;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import softwareGenius.AbstractTest;
import softwareGenius.model.Combat;
import softwareGenius.model.User;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CombatControllerTest extends AbstractTest {


    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void testStartNewCombat() throws Exception{
        String postUri = "/api/combat/start";

        // construct a new combat object
        Combat combat = new Combat();

        // "worldId": 1,
        // "landId": 1,
        // "difficultyLevel": 1,
        // "mode": "battle",
        // "playerId": 1
        combat.setWorldId(1);
        combat.setLandId(1);
        combat.setDifficultyLevel(1);
        combat.setMode("battle"); // or duel
        combat.setPlayerId(1);

        // map the object to json in string
        String inputJson = super.mapToJson(combat);

        // mock post
        MvcResult mvcPostResult = mvc.perform(MockMvcRequestBuilders.post(postUri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // assert response status
        int status = mvcPostResult.getResponse().getStatus();
        assertEquals(200, status);
    }


}
