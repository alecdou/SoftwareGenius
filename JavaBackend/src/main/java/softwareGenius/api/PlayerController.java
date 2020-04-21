package softwareGenius.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;

import org.springframework.web.server.ResponseStatusException;
import softwareGenius.model.Category;
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
    //private final WorldService worldService;
    private final AccountService accountService;
    //private final LandService landService;
    private final SessionService sessionService;
    private final LeaderboardService leaderboardService;

    @Autowired
    public PlayerController(CharacterService charService, WorldService worldService, AccountService accountService, LandService landService, SessionService sessionService, LeaderboardService leaderboardService) {
        this.charService = charService;
        //this.worldService = worldService;
        this.accountService = accountService;
        //this.landService = landService;
        this.sessionService = sessionService;
        this.leaderboardService = leaderboardService;
    }

    /***
     * Get the user with given userId
     * @param userId id of the user
     * @return user object with the given userId
     */
    @GetMapping("/getUser/{userId}")

    public User getUserById(@PathVariable Integer userId) {
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
    public Integer initUser(@RequestBody User user) {
        Integer userId = accountService.addNewUser(user);
        return user.getUserId();
    }

    /***
     * login with current session recorded
     * @param email user's unique email
     * @param password user's password
     * @return the user id of the given email owner
     */
    @GetMapping("/login/{email}/{password}")
    public Integer login(@PathVariable String email, @PathVariable String password) {
        User user = accountService.getUserByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        try {
            accountService.validatePassword(password, user.getUserId());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "validation failed"
            );
        }
        sessionService.addSession(user.getUserId(), Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        return user.getUserId();

    }

    /***
     * logout with current session ending time updated
     * @param userId id of the user object
     * @return true if login successfully; false otherwise
     */
    @GetMapping("/logout/{userId}")
    public Boolean logout(@PathVariable Integer userId) {
        // get the session list of the user
        List<Session> sessionList = sessionService.getSessionByUserID(userId);

        // get the latest session
        Session session = sessionList.get(sessionList.size() - 1);

        // return true if update session time successfully
        if (sessionService.updateSessionEndTime(session.getSessionId(), LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    @GetMapping("/getReport/{userId}")
    public Map<String, String> getReport(@PathVariable("userId") Integer userID) {
        Map<String, String> result = new HashMap<>();
        result.put("userId", userID.toString());
        User u = accountService.getUserById(userID);
        if (u == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid User ID"
            );
        }
        result.put("email", u.getEmail());
        result.put("username", u.getUsername());
        result.put("real_name", u.getRealName());
        List<Character> userChar = charService.getCharacterByUserId(userID);
        int totalQ = 0;
        int totalC = 0;
        for (Character c : userChar) {
            totalQ += c.getTotalQuesNo();
            totalC += c.getCorrectQuesNo();
            result.put(c.getCharName() + "_accuracy", String.valueOf(Float.valueOf(c.getCorrectQuesNo()) / c.getTotalQuesNo()));
        }
        result.put("overall_accuracy", String.valueOf((float) totalC / totalQ));
        result.put("ranking", String.valueOf(leaderboardService.getOverallRankingByUserId(userID) == 0 ? leaderboardService.getOverallRankingByUserId(userID) : "Not Listed"));
        result.put("duration", sessionService.getGameTimeByUserId(userID));
        return result;
    }

    @GetMapping("/getOverallReport")
    public Map<String, String> getOverallReport() {
        Map<String, String> result = new HashMap<>();
        Map<Category, Integer> totalQ = new HashMap<>();
        Map<Category, Integer> totalC = new HashMap<>();
        List<Character> allChar = charService.getAll();
        for (Character c : allChar) {
            Category ca = c.getCharName();
            totalQ.put(ca, totalQ.getOrDefault(ca, 0) + c.getTotalQuesNo());
            totalC.put(ca, totalC.getOrDefault(ca, 0) + c.getCorrectQuesNo());
        }
        int overallQ = 0;
        for (int value : totalQ.values()) {
            overallQ += value;
        }
        int overallC = 0;
        for (int value : totalC.values()) {
            overallC += value;
        }
        int totalStudent = 0;
        for (User u : accountService.getAll()) {
            if (!u.getIsAdmin()) {
                totalStudent += 1;
            }
        }
        result.put("student_num", String.valueOf(totalStudent));
        result.put("overall_accuracy", String.valueOf((float) overallC / overallQ));
        result.put("overall_SE_accuracy", String.valueOf((float) totalC.get(Category.SE) / totalQ.get(Category.SE)));
        result.put("overall_SA_accuracy", String.valueOf((float) totalC.get(Category.SA) / totalQ.get(Category.SA)));
        result.put("overall_PM_accuracy", String.valueOf((float) totalC.get(Category.PM) / totalQ.get(Category.PM)));
        result.put("overall_QA_accuracy", String.valueOf((float) totalC.get(Category.QA) / totalQ.get(Category.QA)));
        result.put("total_game_time", sessionService.getTotalGameTime());
        return result;
    }
}





