package softwareGenius.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import softwareGenius.model.Category;
import softwareGenius.model.User;
import softwareGenius.service.AccountService;
import softwareGenius.service.CharacterService;
import softwareGenius.service.WorldService;

import java.util.List;

@RequestMapping("api/player")
@RestController
public class PlayerController {
    private final CharacterService charService;
    private final WorldService worldService;
    private final AccountService accountService;

    @Autowired
    public PlayerController(CharacterService charService, WorldService worldService, AccountService accountService) {
        this.charService = charService;
        this.worldService = worldService;
        this.accountService = accountService;
    }

    @GetMapping("/get/{userId}")
    public User get(@PathVariable Integer userId){
        return accountService.getUserById(userId);
    }

    @GetMapping("getAll")
    public List<User> getAll(){
            return accountService.getAll();
        }

    @PostMapping("/add")
    public Integer initUser(@RequestBody User user){
        Integer userId = accountService.addNewUser(user);
        for (Category category: Category.values()) {
            charService.initNewCharacter(userId, category);
        }
        return userId;
    }

//    User initNewUser(Integer userId, User user, Character character, World world){
//        for (Category category: Category.values()) {
//
//            // need to get char id to init world
//            charService.initNewCharacter(character);
//
//            // paradox: need user id to init character but init character should be done during user initialization
//
////            if(!charService.initNewCharacter(user.getId(), category)){
////                throw new RuntimeException("Fail to initiate new character: "+ category);
////            }
//            worldService.addWorld(world);
//        }
//        return user;
//    }

    @GetMapping(path = "{msg}")
    public String demoApi(@PathVariable("msg") String msg) {
            return msg;
        }
}





