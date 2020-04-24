package softwareGenius.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import softwareGenius.model.*;
import softwareGenius.model.Character;
import softwareGenius.service.*;

import java.util.ArrayList;
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
    private AccountService accountService;

    @Autowired
    public WorldController(WorldService worldService, LeaderboardService leaderboardService, LandService landService, CharacterService charService,AccountService accountService) {
        this.worldService = worldService;
        this.leaderboardService = leaderboardService;
        this.landService = landService;
        this.charService = charService;
        this.accountService = accountService;
    }

    /**
     * TO get the character object of a world
      * @param worldId id of the world
     * @return character object
     */
    @GetMapping("/getCharByWorldId/{worldId}")
    public Character getCharByWorldId(@PathVariable Integer worldId) {
        Character character = charService.getCharacterByCharId(worldService.getCharIdByWorldId(worldId));
        if (character==null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Character Not Found!"
        );
        return character;
    }

    /**
     * To get all 24 lands in a world
     * @param worldId
     * @return list of 24 land objects
     */
    @GetMapping("/getLandsByWorldId/{worldId}")
    public List<Land> getLandsByWorldId(@PathVariable Integer worldId) {
        List<Land> lands=landService.getLandByWorld(worldId);
        for (Land land:lands) {
            int id=land.getOwnerId();
            if (id==0) continue;
            User user=accountService.getUserById(id);
            land.setOwnerName(user.getUsername());
        }
        if (lands==null || lands.size()==0) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "World Not Found!"
        );
        return lands;
    }

    /**
     * To get lands by user Id and an provided category
     * @param userId id of user
     * @param category the category
     * @return list of 24 land objects
     */
    @GetMapping("/getLandsByUserIdAndCategory/{userId}/{category}")
    public List<Land> getLandsByUserIdAndCategory(@PathVariable Integer userId,@PathVariable String category) {
        User user=accountService.getUserById(userId);
        if (user==null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Not Found!"
        );
        List<World> worlds=worldService.getWorldByOwnerId(userId);
        try {
            for (World world:worlds) {
                if (world.getCategory()==Category.valueOf(category)) {
                    List<Land> lands=landService.getLandByWorld(world.getWorldId());
                    for (Land land:lands) {
                        int id=land.getOwnerId();
                        if (id==0) continue;
                        User user2=accountService.getUserById(id);
                        land.setOwnerName(user2.getUsername());
                    }
                    return lands;
                }
            }
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "World is Locked!"
            );
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Category!"
            );
        }
    }


    /**
     * To get all world id and its corresponding category of a user
     * @param userId id of the user
     * @return a map whose keys are four categories("SE","SA","PM","QA"), values are the worldId(if the world is locked, value is null)
     */
    @GetMapping("/getWorldIdsByUserId/{userId}")
    public Map<String,Integer> getWorldListByUserId(@PathVariable Integer userId) {
        User user=accountService.getUserById(userId);
        if (user==null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Not Found!"
        );
        Map<String,Integer> map=new HashMap<>();
        map.put("SE",null);
        map.put("SA",null);
        map.put("PM",null);
        map.put("QA",null);
        List<World> worlds=worldService.getWorldByOwnerId(userId);
        if (worlds==null) return map;
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

    @GetMapping("/getUsersByCategory/{category}")
    public List<User> getUsersByCategory(@PathVariable String category) {
        List<User> all=accountService.getAll();
        List<User> list=new ArrayList<>();
        try {
            for (User user : all) {
                List<World> worlds = worldService.getWorldByOwnerId(user.getUserId());
                if (worlds == null) continue;
                for (World world : worlds) {
                    if (world.getCategory() == Category.valueOf(category)) {
                        list.add(user);
                        break;
                    }
                }
            }
            return list;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Category!"
            );
        }
    }

    /**
     * get all characters of a user
     * @param userId id of the user
     * @return a map of four character objects
     */
    @GetMapping("/getCharsByUserId/{userId}")
    public Map<String,Character> getCharsByUserId(@PathVariable Integer userId) {
        User user=accountService.getUserById(userId);
        if (user==null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Not Found!"
        );
        Map<String,Character> map=new HashMap<>();
        map.put("SE",null);
        map.put("SA",null);
        map.put("PM",null);
        map.put("QA",null);
        List<Character> list=charService.getCharacterByUserId(userId);
        if (list==null) return map;
        for (Character character:list) {
            Category category=character.getCharName();
            if (category==Category.SE) map.replace("SE",character);
            if (category==Category.SA) map.replace("SA",character);
            if (category==Category.PM) map.replace("PM",character);
            if (category==Category.QA) map.replace("QA",character);
        }
        return map;
    }

    @GetMapping("/unlock/checkUnlockedLands/{userId}")
    public Map<String,Integer> getUnlockedLandsNumByUserId(@PathVariable Integer userId) {
        User user=accountService.getUserById(userId);
        if (user==null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Not Found!"
        );
        Map<String, Integer> worldList = getWorldListByUserId(userId);
        Map<String, Integer> result=new HashMap<>();
        for (String s: worldList.keySet()) {
            Integer worldId = worldList.get(s);
            if (worldId == null) {
                result.put(s, -1);
            } else {
                List<Land> landList = getLandsByUserIdAndCategory(userId, s);
                int count = 0;
                for (Land l: landList) {
                    if (l.getOwnerId() != 0) {
                        count += 1;
                    }
                }
                result.put(s, count);
            }
        }
        return result;
    }

    /**
     * To unlock a new world
     * @param userId id of the user
     * @param category the new world category in String ("SE","SA","PM","QA")
     * @return the id of the new world
     */
    @GetMapping("/unlock/{userId}/{category}")
    public Integer initNewWorld(@PathVariable Integer userId,@PathVariable String category){
        User user=accountService.getUserById(userId);
        if (user==null) throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User Not Found!"
        );
        Category c;
        try {
            c=Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Category!"
            );
        }
        List<World> worlds=worldService.getWorldByOwnerId(userId);
        if (worlds!=null) {
            for (World world:worlds) if (world.getCategory()==c) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Category Already Unlocked!"
                );
            }
        }
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
    @GetMapping("/changeOwner/{landId}/{ownerId}/{difficulty}")
    public void changeOwner(@PathVariable Integer landId,@PathVariable Integer ownerId,@PathVariable Integer difficulty) {
        if (difficulty > 3 || difficulty < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Difficulty Level!"
            );
        }
        landService.changeOwner(landId,ownerId,difficulty);
    }
}
