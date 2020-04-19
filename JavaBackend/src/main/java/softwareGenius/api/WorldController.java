package softwareGenius.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.*;
import softwareGenius.model.Character;
import softwareGenius.service.CharacterService;
import softwareGenius.service.LandService;
import softwareGenius.service.LeaderboardService;
import softwareGenius.service.WorldService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/world")
@RestController
public class WorldController {
    private WorldService worldService;
    private LeaderboardService leaderboardService;
    private LandService landService;
    private CharacterService charService;

    /**
     * TO get the character object of a world
      * @param worldId id of the world
     * @return character object
     */
    @GetMapping("/getCharByWorldId/{worldId}")
    public Character getCharByWorldId(@PathVariable Integer worldId) {
        return charService.getCharacterByCharId(worldService.getCharIdByWorldId(worldId));
    }

    /**
     * To get all 24 lands in a world
     * @param worldId
     * @return list of 24 land objects
     */
    @GetMapping("/getLandByWorldId/{worldId}")
    public List<Land> getLandByWorldId(@PathVariable Integer worldId) {
        return landService.getLandByWorld(worldId);
    }

    /*
    @GetMapping("/getWorldByWorldId/{worldId}")
    public World getWorldByWorldId(@PathVariable Integer worldId) {
        return worldService.getWorldByWorldId(worldId);
    }
     */

    /**
     * To get all world id and its corresponding category of a user
     * @param userId id of the user
     * @return a map whose keys are four categories("SE","SA","PM","QA"), values are the worldId(if the world is locked, value is null)
     */
    @GetMapping("/getWorldsByUser/{userId}")
    public Map<String,Integer> getWorldListByUserId(@PathVariable Integer userId) {
        List<World> worlds=worldService.getWorldByOwnerId(userId);
        Map<String,Integer> map=new HashMap<>();
        map.put("SE",null);
        map.put("SA",null);
        map.put("PM",null);
        map.put("QA",null);
        for (World world:worlds) {
            Category category=world.getCategory();
            Integer worldId=world.getWorldId();
            if (category==Category.SE) map.replace("SE",worldId);
            if (category==Category.SA) map.replace("SA",worldId);
            if (category==Category.PM) map.replace("PM",worldId);
            if (category==Category.QA) map.replace("QA",worldId);
        }
        return map;
    }

    /**
     * To unlock a new world
     * @param userId id of the user
     * @param category the new world category in String ("SE","SA","PM","QA")
     * @return the id of the new world
     */
    @PostMapping("/unlock/{userId}/{category}")
    public Integer initNewWorld(@PathVariable Integer userId,@PathVariable String category){
        int charId=charService.initNewCharacter(userId,Category.valueOf(category));
        int worldId=worldService.initNewWorld(userId,charId,Category.valueOf(category));
        landService.initNewLand(worldId);
        return worldId;
    }



    @GetMapping("/getLeaderBoard/all/{offset}/{limit}")
    public List<List<LeaderBoardRecord>> getAllLeaderBoard(@PathVariable Integer offset,@PathVariable Integer limit) {
        return leaderboardService.getAllLeaderBoard(offset,limit);
    }

    @GetMapping("/getLeaderBoard/general/{offset}/{limit}")
    public List<LeaderBoardRecord> getGeneralLeaderBoard(@PathVariable Integer offset,@PathVariable Integer limit) {
        return leaderboardService.getGeneralLeaderBoard(offset,limit);
    }

    @GetMapping("/getLeaderBoard/category/{category}/{offset}/{limit}")
    public List<LeaderBoardRecord> getLeaderBoardByWorldName(@PathVariable Category category,@PathVariable Integer offset,@PathVariable Integer limit) {
        return leaderboardService.getLeaderBoardByWorldName(category,offset,limit);
    }


    //after win a combat
    @PostMapping("/changeOwner/{landId}/{ownerId}/{difficulty}")
    public void changeOwner(@PathVariable Integer landId,@PathVariable Integer ownerId,@PathVariable Integer difficulty) {
        landService.changeOwner(landId,ownerId,difficulty);
    }
    /*
    @PostMapping("/unlock/{worldId}")
    public void unlockWorld(@PathVariable Integer worldId) {
        worldService.unlockWorld(worldId);
    }
     */

}
