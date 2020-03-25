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

        // get NPC
        NPC npc = npcService.getNPCByDifficultyLevel(combat.getDifficultyLevel());

        // get question list (with 10 questions)
        // TODO: add category
        List<Question> questions = questionService.getQuestionsByCategory("1",
                combat.getDifficultyLevel(), 10);

        // get world such that we can get characters
        Integer worldId = worldService.getCharIdByWorldId(combat.getWorldId());

        // get character
        Character character = characterService.getCharacterByCharId(worldId);

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
                          @Value("idOfAnsweredQns") Integer[] idOfAnsweredQns,
                          @Value("idOfCorrectlyAnsweredQns") Integer[] idOfCorrectlyAnsweredQns
                          )
    {
        // update combat record
        combatService.updateCombatResult(combatId, status, numOfQnsAnswered, idOfCorrectlyAnsweredQns.length);

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
        character.setDefencePt(1);
        characterService.updateCharacter(character);

        // TODO: do we need to update user's overall experience point?
        // update user overall exp

        // update question record
        questionService.addQnsAnswered(idOfAnsweredQns);
        questionService.addQnsCorrectlyAnswered(idOfCorrectlyAnsweredQns);


        // TODO: update land


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
