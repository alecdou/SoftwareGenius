package softwareGenius.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.Category;
import softwareGenius.model.LeaderBoardRecord;
import softwareGenius.model.World;
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


    @GetMapping("/getWorld/{userId}")
    public List<World> getAllWorldByUser(@PathVariable Integer userId) {
        return worldService.getWorldByOwnerId(userId);
    }

    @GetMapping("/getAll/{offset}/{limit}")
    public List<List<LeaderBoardRecord>> getAllLeaderboard(@PathVariable Integer offset,@PathVariable Integer limit) {
        return leaderboardService.getAllLeaderBoard(offset,limit);
    }

    @PostMapping("/changeOwner/{landId}/{ownerId}/{difficulty}")
    public void changeOwner(@PathVariable Integer landId,@PathVariable Integer ownerId,@PathVariable Integer difficulty) {
        landService.changeOwner(landId,ownerId,difficulty);
    }

    @PostMapping("/unlock/{worldId}")
    public void unlockWorld(@PathVariable Integer worldId) {
        worldService.unlockWorld(worldId);
    }

}
