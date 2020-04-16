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
     *
     * @param userId id of the user
     * @return user object with the given userId
     */
    @GetMapping("/get/{userId}")
    public User get(@PathVariable Integer userId){
        return accountService.getUserById(userId);
    }

    /***
     *
     * @return get all users
     */
    @GetMapping("/getAll")
    public List<User> getAll(){
            return accountService.getAll();
        }

    /***
     *
      * @param user user object
     * @return initialize a new user
     */
    @PostMapping("/add")
    public Integer initUser(@RequestBody User user){
        Integer userId = accountService.addNewUser(user);
//        for (Category category: Category.values()) {
//            landService.initNewLand(
//                    worldService.initNewWorld(userId,
//                            charService.initNewCharacter(userId, category), category));
//        }
        return userId;
    }

    /***
     *
     * @param user user object
     * @return login with current session recorded
     */
    @GetMapping("/login")
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
     *
     * @param user user object
     * @return logout with current session ending time updated
     */
    @GetMapping("/logout")
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





