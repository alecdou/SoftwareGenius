package softwareGenius.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.Category;
import java.time.LocalDateTime;
import softwareGenius.model.Combat;
import softwareGenius.model.Session;
import softwareGenius.model.User;
import softwareGenius.service.*;

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

    @Autowired
    public PlayerController(CharacterService charService, WorldService worldService, AccountService accountService, LandService landService, SessionService sessionService) {
        this.charService = charService;
        this.worldService = worldService;
        this.accountService = accountService;
        this.landService = landService;
        this.sessionService = sessionService;
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
    public List<User> getAll(){
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
            sessionService.addSession(user.getId(), LocalDateTime.now());
        } catch (Exception e){
            System.err.println(e.toString());
            return false;
        }
        return true;
    }

    /***
     * logout with current session ending time updated
     * @param user user object
     * @return true if login successfully; false otherwise
     */
    @PostMapping("/logout")
    public Boolean logout(@RequestBody User user) {
        // get the session list of the user
        List<Session> sessionList = sessionService.getSessionByUserID(user.getId());

        // get the latest session
        Session session = sessionList.get(sessionList.size() - 1);

        // return true if update session time successfully
       if (sessionService.updateSessionEndTime(session.getSessionId(),LocalDateTime.now())){
            return true;
        }
        return false;
    }

}





