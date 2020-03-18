package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.CharacterDao;
import softwareGenius.mapper.UserDao;
import softwareGenius.model.Character;
import softwareGenius.model.User;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterDao characterDao;
    private final UserDao userDao;

    @Autowired
    public CharacterService(CharacterDao characterDao, UserDao userDao) {
        this.characterDao = characterDao;
        this.userDao = userDao;
    }

    /**
     * Initiate a new character with given character object
     * @param character character object
     * @return status of the request (ex. True if succeed)
     */
    public Boolean initNewCharacter(Character character) {
        return characterDao.addCharacter(character);
    }

    /**
     * update the exp, attack points, defense points, level, and other fields of the character object and
     * the overall experience points of the user object with given userId and newCharacter object
     * @param userId id of the user
     * @param newCharacter updated character object
     * @return status of the request (ex. True if succeed)
     */
    public Boolean updateCharacter(Integer userId, Character newCharacter) {
        // Get the old character data by the id of the new Character
        Character oldCharacter =  characterDao.getCharacterByCharId(newCharacter.getCharId());

        // Calculate the difference of experience points between the old and new character
        int expDiff = newCharacter.getExp() - oldCharacter.getExp();

        // Get the old User with the given userId
        User oldUser = userDao.getUserById(userId);

        // Get the previous overall experience points of the old user
        Integer prevExp = oldUser.getOverallExp();

        // Reset the overall Experience points
        oldUser.setOverallExp(prevExp + expDiff);

        // Update the user
        userDao.updateUser(oldUser);

        // Update the character
        return characterDao.updateCharacter(newCharacter);
    }

    /**
     * Get Character by the given userId
     * @param userId id of the user
     * @return list of Character objects
     */
    public List<Character> getCharacterByUserId(Integer userId) {
        return characterDao.getCharacterByUserId(userId);
    }

    /**
     * Get Character by given charId
     * @param charId id of the character
     * @return a character object with matching charId
     */
    public Character getCharacterByCharId(Integer charId) {
        return characterDao.getCharacterByCharId(charId);
    }

    /**
     * Get all characters
     * @return a list of all character objects
     */
    public List<Character> getAll() {
        return characterDao.getAll();
    }

    /**
     * Delete character with given userId
     * @param userId id of the user
     * @return status of the request (ex. True if succeed)
     */
    public Boolean deleteCharacter(Integer userId) {
        return characterDao.deleteCharacter(userId);
    }

}
