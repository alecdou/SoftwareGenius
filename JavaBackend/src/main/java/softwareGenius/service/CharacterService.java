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

    public Boolean initNewCharacter(Character character) {
        return characterDao.addCharacter(character);
    }

    public Boolean upgradeCharacter(Integer userId, Character newCharacter) {
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

    public List<Character> getCharacterByUserId(Integer userId) {
        return characterDao.getCharacterByUserId(userId);
    }

    public Character getCharacterByCharId(Integer charId) {
        return characterDao.getCharacterByCharId(charId);
    }

    public List<Character> getAll() {
        return characterDao.getAll();
    }

    public Boolean deleteCharacter(Integer userId) {
        return characterDao.deleteCharacter(userId);
    }

}
