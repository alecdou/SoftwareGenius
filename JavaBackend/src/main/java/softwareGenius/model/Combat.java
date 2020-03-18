package softwareGenius.model;

public class Combat {

    private Integer combatId;

    private Integer difficultyLevel;

    /** The mode the of the combat: battle or duel. */
    private String mode;

    private Integer playerId;

    private Integer npcId;

    /** Representing whether the player won the combat or not. */
    private Boolean isSuccessful;

    public Combat(Integer combatId, Integer difficultyLevel, String mode, Integer playerId, Integer npcId, Boolean isSuccessful) {
        this.combatId = combatId;
        this.difficultyLevel = difficultyLevel;
        this.mode = mode;
        this.playerId = playerId;
        this.npcId = npcId;
        this.isSuccessful = isSuccessful;
    }

    public Integer getCombatId() {
        return combatId;
    }

    public void setCombatId(Integer combatId) {
        this.combatId = combatId;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getNpcId() {
        return npcId;
    }

    public void setNpcId(Integer npcId) {
        this.npcId = npcId;
    }

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }
}
