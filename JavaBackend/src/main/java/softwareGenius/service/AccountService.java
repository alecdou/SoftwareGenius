package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.UserDao;
import softwareGenius.model.User;
import java.util.List;

@Service
public class AccountService {
    private final UserDao userDao;

    @Autowired
    public AccountService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Add and initiate a new user to database with given user object
     * @param user user object
     * @return id of the user
     */

    public Integer addNewUser(User user) {
        return userDao.addUser(user);
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
            System.err.println(e.toString());
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
    private void validatePassword(String inputPassword, Integer userId) throws Exception {
        // get the original user password by userId
        String origPassword = getUserById(userId).getPassword();

        // validate the pw
        if(!origPassword.equals(inputPassword)){
            throw new Exception("Unmatched password");
        }
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
