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

    @GetMapping("/getAll")
    public List<User> getAll(){
            return accountService.getAll();
        }

    @PostMapping("/add")
    public Integer initUser(@RequestBody User user){
        Integer userId = accountService.addNewUser(user);
        for (Category category: Category.values()) {
//            worldService.initNewWorld(userId, charService.initNewCharacter(userId, category), category); //FIXME no method called initNewWorld
        }
        return userId;
    }
}





