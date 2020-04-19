package softwareGenius.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;

import softwareGenius.model.Character;
import softwareGenius.model.Session;
import softwareGenius.model.User;
import softwareGenius.service.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/player")
@RestController
public class PlayerController {
    private final CharacterService charService;
    private final WorldService worldService;
    private final AccountService accountService;
    private final LandService landService;
    private final SessionService sessionService;
    private final LeaderboardService leaderboardService;

    @Autowired
    public PlayerController(CharacterService charService, WorldService worldService, AccountService accountService, LandService landService, SessionService sessionService, LeaderboardService leaderboardService) {
        this.charService = charService;
        this.worldService = worldService;
        this.accountService = accountService;
        this.landService = landService;
        this.sessionService = sessionService;
        this.leaderboardService = leaderboardService;
    }

    /***
     * Get the user with given userId
     * @param userId id of the user
     * @return user object with the given userId
     */
    @GetMapping("/getUser/{userId}")

    public User getUserById(@PathVariable Integer userId){
        return accountService.getUserById(userId);
    }

    /***
     * Get all users
     * @return list of user obejcts
     */
    @GetMapping("/getAll")
    public List<User> getAll() {
        return accountService.getAll();
    }

    /***
     * create a new user
      * @param user user object
     * @return userId Id of the created user
     */
    @PostMapping("/addUser")
    public Integer initUser(@RequestBody User user){
        Integer userId = accountService.addNewUser(user);
        return user.getId();
    }

    /***
     * login with current session recorded
     * @param user user object
     * @return true if login successfully; false otherwise
     */
    @PostMapping("/login")
    public Boolean login(@RequestBody User user) {
        try{
            accountService.validatePassword(user.getPassword(), user.getId());
            sessionService.addSession(user.getId(), Timestamp.valueOf(LocalDateTime.now()));
        } catch (Exception e){
            System.err.println(e.toString());
            return false;
        }
        return true;
    }

    /***
     * logout with current session ending time updated
     * @param userId id of the user object
     * @return true if login successfully; false otherwise
     */
    @GetMapping("/logout/{userId}")
    public Boolean logout(@PathVariable Integer userId){
        // get the session list of the user
        List<Session> sessionList = sessionService.getSessionByUserID(userId);

        // get the latest session
        Session session = sessionList.get(sessionList.size() - 1);

        // return true if update session time successfully
       if (sessionService.updateSessionEndTime(session.getSessionId(),LocalDateTime.now())){
            return true;
        }
        return false;
    }

    @GetMapping ("/getReport/{userId}")
    public Map<String, String> getReport(@PathVariable("userId") Integer userID){
        Map<String, String> result = new HashMap<>();
        result.put("userId", userID.toString());
        result.put("email", accountService.getUserById(userID).getEmail());
        List<Character> userChar = charService.getCharacterByUserId(userID);
        int totalQ = 0;
        int totalC = 0;
        for (Character c: userChar) {
            totalQ += c.getTotalQuesNo();
            totalC += c.getCorrectQuesNo();
            result.put(c.getCharName() + "_accuracy", String.valueOf(Float.valueOf(c.getCorrectQuesNo())/c.getTotalQuesNo()));
        }
        result.put("overall_accuracy", String.valueOf(Float.valueOf(totalC)/totalQ));
        result.put("ranking", String.valueOf(leaderboardService.getOverallRankingByUserId(userID)));
        List<Session> userSessions = sessionService.getSessionByUserID(userID);
        Period totalGameDay = Period.ZERO;
        Duration totalGameTime = Duration.ZERO;
        for (Session s: userSessions) {
            totalGameDay.plus(Period.between(s.getLogoutTime().toLocalDate(), s.getLoginTime().toLocalDate()));
            totalGameTime.plus(Duration.between(s.getLogoutTime(), s.getLoginTime()));
        }
        result.put("duration", totalGameDay.toString() + ' ' + totalGameTime.toString());
        return result;
    }

}





