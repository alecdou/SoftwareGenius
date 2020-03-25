package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.UserDao;
import softwareGenius.model.Character;
import softwareGenius.model.User;
import softwareGenius.model.World;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    public enum Category {SE, SA, PM, QA};
    private final UserDao userDao;
    private final CharacterService charService;
    private final WorldService worldService;

    @Autowired
    public AccountService(UserDao userDao, CharacterService charService, WorldService worldService) {
        this.userDao = userDao;
        this.charService = charService;
        this.worldService = worldService;
    }

    /**
     * Add and initiate a new user to database with given user object
     * @param user user object
     * @return status of the request (ex. True if succeed)
     */
    public Boolean addNewUser(User user, Character character, World world ) {
        if (!user.getAdmin()){
            return userDao.addUser(initNewUser(user, character, world));
        }
        return userDao.addUser(user);
    }

    private User initNewUser(User user, Character character, World world){
        for (Category category: Category.values()) {

            // need to get char id to init world
            charService.initNewCharacter(character);

            // paradox: need user id to init character but init character should be done during user initialization

//            if(!charService.initNewCharacter(user.getId(), category)){
//                throw new RuntimeException("Fail to initiate new character: "+ category);
//            }
            worldService.addWorld(world);
        }
        return user;
    }

    /**
     * update non-credential user info including email, username, and more
     * @param user updated user object
     * @return status of the request (ex. True if succeed)
     */
    public Boolean updateUserInfo(User user) {
        return userDao.updateUser(user);
    }

    /**
     * update user password with validation
     * @param newPassword new password
     * @param oldPassword original password for validation purpose
     * @param userId id of the user
     * @return status of the request (ex. True if succeed)
     */
    public Boolean updateUserInfo(String newPassword, String oldPassword, Integer userId) {

        //try validate the old password before update to new one
        try{
            validatePassword(oldPassword, userId);
        } catch (Exception e){
            System.err.println("Unmatched password");
            e.printStackTrace();
            return false;
        }

        // get the original user object by userId
        User user = getUserById(userId);

        // modify the user password
        user.setPassword(newPassword);

        return userDao.updateUser(user);
    }

    /**
     * validate user password with validation
     * @param inputPassword password input by user for validation purpose
     * @param userId id of the user
     * @return result of the validation (ex. True if succeed)
     */

    // do we need token to do so??
    private Boolean validatePassword(String inputPassword, Integer userId) {
        // get the original user password by userId
        String origPassword = getUserById(userId).getPassword();

        // validate the pw
        return origPassword == inputPassword;
    }

    /**
     * Get User by the given userId
     * @param userId id of the user
     * @return the matching user object
     */
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    /**
     * Get all users
     * @return a list of all user objects
     */
    public List<User> getAll() {
        return userDao.getAll();
    }

    /**
     * Delete user with given userId
     * @param userId id of the user
     * @return status of the request (ex. True if succeed)
     */
    public Boolean deleteUser(Integer userId) {
        return userDao.deleteUser(userId);
    }

}
