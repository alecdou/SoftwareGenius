package softwareGenius.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.*;
import softwareGenius.model.Character;
import softwareGenius.service.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/combat")
@RestController
public class CombatController {

    private CombatService combatService;
    private LandService landService;
    private QuestionService questionService;
    private WorldService worldService;
    private CharacterService characterService;
    private AccountService accountService;

    @Autowired
    public CombatController(CombatService combatService, LandService landService,
                            QuestionService questionService, WorldService worldService,
                            CharacterService characterService, AccountService accountService) {
        this.combatService = combatService;
        this.landService = landService;
        this.questionService = questionService;
        this.worldService = worldService;
        this.characterService = characterService;
        this.accountService = accountService;
    }

    /**
     * To start a new combat.
     * @return a collection of required objects, including questionList, NPC and character
     */
    @PostMapping(path = "start")
    public Map<String, Object> startNewCombat(@RequestBody Combat combat) {
        Map<String,Object> map = new HashMap<>();

        // initialize a combat
        combatService.startNewCombat(combat);
        Integer combatId = combat.getCombatId();

        // get question list (with 20 questions)
        World world;
        try {
            world = worldService.getWorldByWorldId(combat.getWorldId());
        } catch (Exception e) {
            map.put("error", "Error: World id is wrong or the world does not exist!");
            return map;
        }

        Category category = world.getCategory();
        List<Question> questions;
        try {
            questions = questionService.getQuestionsByCategory(category.toString(),
                    combat.getDifficultyLevel(), 20);
        } catch (Exception e) {
            map.put("error", "Error: Difficulty level should be 1, 2, or 3 (integer)!");
            return map;
        }

        // get character
        Character character;
        try {
            Integer characterId = worldService.getCharIdByWorldId(combat.getWorldId());
            character = characterService.getCharacterByCharId(characterId);
        } catch (Exception e) {
            map.put("error", "Error: The system is not able to get the character required!");
            return map;
        }


        //put all the values in the map
        map.put("combatId", combatId);
        map.put("questions", questions);
        map.put("character", character);

        return map;
    }

    /**
     * Updates the combat status, user info and character info.
     * @param combatId the id of the combat
     * @param json the json file
     * @return the combat result
     */
    @PostMapping(path = "{combatId}/end")
    public Map<String, Object> endBattle(@PathVariable("combatId") Integer combatId, @RequestBody Map<String, String> json) {
        Map<String, Object> map = new HashMap<>();

        // process the json data
        Integer characterId;
        try {
            characterId = Integer.parseInt(json.get("characterId"));
        } catch (Exception e) {
            map.put("error: ", "Error: something went wrong with the character id");
            return map;
        }

        String status;
        try {
            status = json.get("status");
        } catch (Exception e) {
            map.put("error: ", "Error: something went wrong with the status");
            return map;
        }

        Integer numOfQnsAnswered;
        try {
            numOfQnsAnswered = Integer.parseInt(json.get("numOfQnsAnswered"));
        } catch (Exception e) {
            map.put("error: ", "Error: something went wrong with the numOfQnsAnswered");
            return map;
        }

        Integer[] idOfAnsweredQns;
        Integer[] idOfCorrectlyAnsweredQns;
        try {
            String[] idOfAnsweredQnsStr = json.get("idOfAnsweredQns").replace("[", "").replace("]", "").split(",");
            idOfAnsweredQns = new Integer[idOfAnsweredQnsStr.length];
            System.out.println(Arrays.toString(idOfAnsweredQnsStr));
            for (int i = 0; i < idOfAnsweredQns.length; i++) {
                idOfAnsweredQns[i] = Integer.parseInt(idOfAnsweredQnsStr[i].trim());
            }
        } catch (Exception e) {
            idOfAnsweredQns = new Integer[0];
        }

        try {
            String[] idOfCorrectlyAnsweredQnsStr = json.get("idOfCorrectlyAnsweredQns").replace("[", "").replace("]", "").split(",");
            idOfCorrectlyAnsweredQns = new Integer[idOfCorrectlyAnsweredQnsStr.length];
            System.out.println(Arrays.toString(idOfCorrectlyAnsweredQnsStr));
            for (int i = 0; i < idOfCorrectlyAnsweredQns.length; i++) {
                idOfCorrectlyAnsweredQns[i] = Integer.parseInt(idOfCorrectlyAnsweredQnsStr[i].trim());
            }
        } catch (Exception e) {
            idOfCorrectlyAnsweredQns = new Integer[0];
        }

        // update combat record
        try {
            combatService.updateCombatResult(combatId, status, numOfQnsAnswered, idOfCorrectlyAnsweredQns.length);
        } catch (Exception e) {
            map.put("error: ", "Error: the combat does not exit");
            return map;
        }

        // get the combat data
        Combat combat;
        try {
            combat = combatService.getCombatById(combatId);
        } catch (Exception e) {
            map.put("error: ", "Error: the combat does not exit");
            return map;
        }

        // get the character used in the battle
        Character character;
        try {
            character = characterService.getCharacterByCharId(characterId);
        } catch (Exception e) {
            map.put("error: ", "Error: the character does not exit");
            return map;
        }

        // get the total experience point earned in the battle
        Integer addedExp;
        try {
             addedExp = questionService.calculateScore(idOfCorrectlyAnsweredQns);
        } catch (Exception e) {
            map.put("error: ", "Error: some question ids are wrong");
            return map;
        }

        Integer userId;
        try {
            userId = combat.getPlayerId();
        } catch (Exception e) {
            map.put("error: ", "Error: provided user id is wrong");
            return map;
        }

        User user;
        try {
            user = accountService.getUserById(userId);
        } catch (Exception e) {
            map.put("error: ", "Error: the user does not exist");
            return map;
        }

        // get the new level of the character
        Integer characterLevel = (int) Math.ceil((character.getExp() + addedExp) / 10.0);

        // update the character
        character.setExp(character.getExp() + addedExp);
        character.setLevel(characterLevel);
        character.setTotalQuesNo(character.getTotalQuesNo() + numOfQnsAnswered);
        character.setCorrectQuesNo(character.getCorrectQuesNo() + idOfCorrectlyAnsweredQns.length);
        characterService.updateCharacter(character);

        // update user overall exp
        user.setOverallExp(user.getOverallExp() + addedExp);

        // update question record
        questionService.addQnsAnswered(idOfAnsweredQns);
        questionService.addQnsCorrectlyAnswered(idOfCorrectlyAnsweredQns);
        // update the land if combat succeeded

        if (!status.equals("failed")) {
            landService.changeOwner(combat.getLandId(), combat.getPlayerId(), combat.getDifficultyLevel());
        }

        map.put("addedExp", addedExp);
        return map;
    }
}
