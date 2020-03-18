package softwareGenius.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.Combat;
import softwareGenius.service.CombatService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/combat")
@RestController
public class CombatController {

    @Autowired
    private CombatService combatService;



    /**
     * To start a new combat.
     * @return a collection of required objects, including questionList, NPC and character
     */
    @GetMapping(path = "start")
    public Map<String, Object> startNewCombat(@RequestBody Combat combat) {
        combatService.startNewCombat(combat);
        Map<String,Object> map=new HashMap<>();
        //put all the values in the map
        map.put("combat", newCombat);
        return map;
    }

    @PostMapping(path = "combatId={combatId}/end")
    public endBattle(@PathVariable("combatId") Integer combatId,
                     @Value("status") String status)

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
