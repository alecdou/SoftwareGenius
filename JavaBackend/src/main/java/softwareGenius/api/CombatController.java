package softwareGenius.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.*;
import softwareGenius.model.Character;
import softwareGenius.service.*;

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

    @Autowired
    public CombatController(CombatService combatService, LandService landService, NPCService npcService,
                            QuestionService questionService, WorldService worldService,
                            CharacterService characterService) {
        this.combatService = combatService;
        this.landService = landService;
        this.npcService = npcService;
        this.questionService = questionService;
        this.worldService = worldService;
        this.characterService = characterService;
    }

    /**
     * To start a new combat.
     * @return a collection of required objects, including questionList, NPC and character
     */
    @GetMapping(path = "start")
    public Map<String, Object> startNewCombat(@RequestBody Combat combat) {
        // initialize a combat: worldId, landId, difficultyLevel, mode, playerId, status
        Integer combatId = combatService.startNewCombat(combat);
        System.out.println(combat.getCombatId());

        // get world


        // get NPC
        NPC npc = npcService.getNPCByDifficultyLevel(combat.getDifficultyLevel());

        // get question list (with 10 questions)
        List<Question> questions = questionService.getQuestionsByCategory("1",
                combat.getDifficultyLevel(), 10);

        // get character

        Character character = new Character(1, Category.SE, 1, 10, 1, 1, true, 0, 0);

        Map<String,Object> map = new HashMap<>();
        //put all the values in the map
        map.put("npc", npc);
        map.put("questions", questions);
        map.put("character", character);

        return map;
    }


    @PostMapping(path = "{combatId}/end")
    public void endBattle(@PathVariable("combatId") Integer combatId,
                          @Value("characterId") Integer characterId,
                          @Value("status") String status,
                          @Value("numOfQnsAnswered") Integer numOfQnsAnswered,
                          @Value("idOfAnsweredQns") List<Integer> idOfAnsweredQns,
                          @Value("idOfCorrectlyAnsweredQns") List<Integer> idOfCorrectlyAnsweredQns,
                          )
    {
        combatService.updateCombatResult(combatId, status, numOfQnsAnswered, idOfCorrectlyAnsweredQns.size());
        Character character = characterService.getCharacterByCharId(characterId);

        character.setExp(character.getExp() + addedExp);
        // TODO: update other attributes such as attackPoint and hitPoint
        characterService.updateCharacter(character);
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
