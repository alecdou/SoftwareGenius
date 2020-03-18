package softwareGenius.mapper;

import softwareGenius.model.Combat;

import java.util.List;

public interface CombatDao {

    /**
     * Inserts combat information when a player starts a combat. The status is "pending".
     * @param combat the combat object
     * @return whether the adding is successful
     */
    Boolean addCombat(Combat combat);

    /**
     * Updates the status of a combat when the combat completes.
     * @param combatId the id of the combat
     * @param status the status of the combat
     * @return whether the update is successful
     */
    Boolean updateCombatStatus(Integer combatId, String status);

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
