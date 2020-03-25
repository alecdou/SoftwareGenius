package softwareGenius.model;

import java.time.LocalDateTime;

public class Combat {

    private Integer worldId;

    private Integer landId;

    private Integer combatId;

    private Integer difficultyLevel;

    /** The mode the of the combat: battle or duel. */
    private String mode;

    private Integer playerId;

    private Integer npcId;

    /** The status includes pending, failed, succeeded. */
    private String status;

    /** The time when the combat begins. */
    private LocalDateTime combatTime;

    /** The total number of question showed in a combat. */
    private Integer totalNumOfQuestions;

    /** The number of correctly answered questions in a combat. */
    private Integer numOfCorrectAns;

    public Combat(Integer worldId, Integer landId, Integer combatId, Integer difficultyLevel,
                  String mode, Integer playerId, Integer npcId, String status, LocalDateTime combatTime,
                  Integer totalNumOfQuestions, Integer numOfCorrectAns) {
        this.worldId = worldId;
        this.landId = landId;
        this.combatId = combatId;
        this.difficultyLevel = difficultyLevel;
        this.mode = mode;
        this.playerId = playerId;
        this.npcId = npcId;
        this.status = status;
        this.combatTime = combatTime;
        this.totalNumOfQuestions = totalNumOfQuestions;
        this.numOfCorrectAns = numOfCorrectAns;
    }

    public Integer getWorldId() {
        return worldId;
    }

    public void setWorldId(Integer worldId) {
        this.worldId = worldId;
    }

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCombatTime() {
        return combatTime;
    }

    public void setCombatTime(LocalDateTime combatTime) {
        this.combatTime = combatTime;
    }

    public Integer getTotalNumOfQuestions() {
        return totalNumOfQuestions;
    }

    public void setTotalNumOfQuestions(Integer totalNumOfQuestions) {
        this.totalNumOfQuestions = totalNumOfQuestions;
    }

    public Integer getNumOfCorrectAns() {
        return numOfCorrectAns;
    }

    public void setNumOfCorrectAns(Integer numOfCorrectAns) {
        this.numOfCorrectAns = numOfCorrectAns;
    }
}
