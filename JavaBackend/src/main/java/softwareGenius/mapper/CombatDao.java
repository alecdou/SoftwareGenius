package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.Combat;

import java.util.List;

@Mapper
@Component
public interface CombatDao {

    /**
     * Inserts combat information when a player starts a combat. The status is "pending".
     * @param combat the combat object
     * @return whether the adding is successful
     */
    Integer addCombat(Combat combat);

    Combat getCombatById(Integer combatId);

    /**
     * Updates the result of a combat when the combat completes.
     * @param combatId the id of the combat
     * @param status the status of the combat
     * @param totalNumOfQuestions the total number of questions showed in a combat
     * @param numOfCorrectAns the number of correctly answered questions
     * @return whether the update is successful
     */
    Boolean updateCombatResult(Integer combatId, String status, Integer totalNumOfQuestions, Integer numOfCorrectAns);

    /**
     * Gets all combats in which a player involved in descending chronological order.
     * @param playerId the id of the player
     * @return the list of combats
     */
    List<Combat> getCombatByPlayerId(Integer playerId);

    /**
     * Gets all battles/duels combat in which a player involved in descending chronological order.
     * @param playerId the id of the player
     * @param mode the mode of the combat (battle or duel)
     * @return the list of combats
     */
    List<Combat> getCombatByPlayerIdAndMode(Integer playerId, String mode);

}
