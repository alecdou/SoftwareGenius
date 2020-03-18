package softwareGenius.api;


import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping(path = "start")
    public Map<String, Object> startNewCombat(@RequestBody Combat combat) {
        Combat newCombat = combatService.startNewCombat(combat);

        Map<String,Object> map=new HashMap<>();
        //put all the values in the map
        map.put("combat", newCombat);
        return map;
    }

    @GetMapping(path = "{combatId}")
    public Combat getCombatById(@PathVariable("combatId") Integer combatId) {
        return combatService.getCombatById(combatId);
    }

    @GetMapping(path = "{playerId}")
    public List<Combat> getCombatByPlayerId(@PathVariable("playerId") Integer playerId) {
        return null;
    }

}
