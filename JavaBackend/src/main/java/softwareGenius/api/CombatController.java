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
    private NPCService npcService;
    private QuestionService questionService;
    private WorldService worldService;
    private CharacterService characterService;
    private AccountService accountService;

    @Autowired
    public CombatController(CombatService combatService, LandService landService, NPCService npcService,
                            QuestionService questionService, WorldService worldService,
                            CharacterService characterService, AccountService accountService) {
        this.combatService = combatService;
        this.landService = landService;
        this.npcService = npcService;
        this.questionService = questionService;
        this.worldService = worldService;
        this.characterService = characterService;
        this.accountService = accountService;
    }

    /**
     * To start a new combat.
     * @return a collection of required objects, including questionList, NPC and character
     */
    @GetMapping(path = "start")
    public Map<String, Object> startNewCombat(@RequestBody Combat combat) {
        // initialize a combat
        combatService.startNewCombat(combat);
        Integer combatId = combat.getCombatId();

        // get NPC
        NPC npc = npcService.getNPCByDifficultyLevel(combat.getDifficultyLevel());

        // get question list (with 20 questions)
        World world = worldService.getWorldByWorldId(combat.getWorldId());
        Category category = world.getCategory();
        List<Question> questions = questionService.getQuestionsByCategory(category.toString(),
                combat.getDifficultyLevel(), 20);

        // get world such that we can get characters
        Integer characterId = worldService.getCharIdByWorldId(combat.getWorldId());

        // get character
        Character character = characterService.getCharacterByCharId(characterId);

        Map<String,Object> map = new HashMap<>();

        //put all the values in the map
        map.put("combatId", combatId);
        map.put("npc", npc);
        map.put("questions", questions);
        map.put("character", character);

        return map;
    }


    @PostMapping(path = "{combatId}/end")
    public Map<String, Object> endBattle(@PathVariable("combatId") Integer combatId, @RequestBody Map<String, String> json) {
        // process the json data
        Integer characterId = Integer.parseInt(json.get("characterId"));
        String status = json.get("status");
        Integer numOfQnsAnswered = Integer.parseInt(json.get("numOfQnsAnswered"));

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
        combatService.updateCombatResult(combatId, status, numOfQnsAnswered, idOfCorrectlyAnsweredQns.length);

        // get the combat data
        Combat combat = combatService.getCombatById(combatId);

        // get the character used in the battle
        Character character = characterService.getCharacterByCharId(characterId);

        // get the total experience point earned in the battle
        Integer addedExp = questionService.calculateScore(idOfCorrectlyAnsweredQns);

        // get the new level of the character
        Integer characterLevel = (int) Math.ceil((character.getExp() + addedExp) / 10.0);

        // update the character
        character.setExp(character.getExp() + addedExp);
        character.setLevel(characterLevel);
        character.setTotalQuesNo(character.getTotalQuesNo() + numOfQnsAnswered);
        character.setCorrectQuesNo(character.getCorrectQuesNo() + idOfCorrectlyAnsweredQns.length);

        // TODO: update other attributes such as attackPoint and hitPoint

        character.setAttackPt(1);
        characterService.updateCharacter(character);

        // update user overall exp
        Integer userId = combat.getPlayerId();
        User user = accountService.getUserById(userId);
        user.setOverallExp(user.getOverallExp() + addedExp);

        // update question record
        questionService.addQnsAnswered(idOfAnsweredQns);
        questionService.addQnsCorrectlyAnswered(idOfCorrectlyAnsweredQns);
        //
        landService.changeOwner(combat.getLandId(),combat.getPlayerId(),combat.getDifficultyLevel());

        Map<String, Object> map = new HashMap<>();
        //put all the values in the map
        map.put("addedExp", addedExp);
        return map;
    }

    @GetMapping(path = "{combatId}")
    public Combat getCombatById(@PathVariable("combatId") Integer combatId) {
        return combatService.getCombatById(combatId);
    }

    @GetMapping(path = "/player{playerId}")
    public List<Combat> getCombatByPlayerId(@PathVariable("playerId") Integer playerId) {
        return combatService.getCombatByPlayerId(playerId);
    }

    @GetMapping(path = "player{playerId}/{mode}")
    public List<Combat> getCombatByPlayerIdAndMode(@PathVariable("playerId") Integer playerId,
                                                   @PathVariable("mode") String mode) {
        return combatService.getCombatByPlayerIdAndMode(playerId, mode);
    }

}
