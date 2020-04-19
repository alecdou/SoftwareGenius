package softwareGenius.model;

public class Land {
    private Integer landId;
    private Integer index;
    private Integer worldId;
    private Integer ownerId;
    private Integer ownerDifficultyLevel;

    public Land(Integer landId, Integer worldId, Integer index, Integer ownerId, Integer ownerDifficultyLevel) {
        this.landId = landId;
        this.index = index;
        this.worldId = worldId;
        this.ownerId = ownerId;
        this.ownerDifficultyLevel = ownerDifficultyLevel;
    }

    public Land(Integer worldId, Integer index, Integer ownerId, Integer ownerDifficultyLevel) {
        this.worldId = worldId;
        this.index = index;
        this.ownerId = ownerId;
        this.ownerDifficultyLevel = ownerDifficultyLevel;
    }

    public Integer getLandId() { return landId; }

    public void setLandId(Integer landId) { this.landId=landId; }

    public Integer getIndex() { return index;}

    public Integer getWorldId() { return worldId; }

    public Integer getOwnerId() { return ownerId; }

    public void setOwnerId(Integer ownerId) { this.ownerId=ownerId; }

    public Integer getOwnerDifficulty() { return ownerDifficultyLevel; }

    public void setOwnerDifficulty(Integer ownerDifficultyLevel) { this.ownerDifficultyLevel = ownerDifficultyLevel; }
}
