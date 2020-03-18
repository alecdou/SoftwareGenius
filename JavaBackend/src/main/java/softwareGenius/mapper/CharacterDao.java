
package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.Character;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public interface CharacterDao {
    /**
     * Insert a character object to database
     * @param character
     * @return status of the post query(ex. True if the query succeed)
     */
    Boolean addCharacter(Character character);

    /**
     * Fetch a character object by its unique character Id(charId)
     * @param charId, the unique identifier of the Character object
     * @return Character object matching the charId
     */
    Character getCharacterByUserId(Integer charId);

    /**
     * Fetch a list of character objects by the userId
     * @param userId
     * @return list of Character objects matching the userId
     */
    List<Character> getCharacterByCharId(Integer userId);

    /**
     * Get all gcharacter objects
     * @return list of Character objects
     */
    List<Character> getAll();

    /**
     * Delete the character in database with matching userId
     * @param userId
     * @return status of the query(ex. True if the query succeed)
     */
    Boolean deleteCharacter(Integer userId);

    /**
     * Update the Character object in database by the given new character
     * @param character new character
     * @return status of the query(ex. True if the query succeed)
     */
    Boolean updateCharacter(Character character);
}