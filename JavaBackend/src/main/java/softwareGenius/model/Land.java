package softwareGenius.model;

public class Land {
    private Integer landId;
    private Integer ind;
    private Integer worldId;
    private Integer ownerId;
    private String ownerName;
    private Integer ownerDifficultyLevel;

    public Land() {}
    public Land(Integer landId, Integer worldId, Integer index, Integer ownerId, Integer ownerDifficultyLevel) {
        this.landId = landId;
        this.ind = index;
        this.worldId = worldId;
        this.ownerId = ownerId;
        this.ownerDifficultyLevel = ownerDifficultyLevel;
    }

    public Land(Integer worldId, Integer index, Integer ownerId, Integer ownerDifficultyLevel) {
        this.worldId = worldId;
        this.ind = index;
        this.ownerId = ownerId;
        this.ownerDifficultyLevel = ownerDifficultyLevel;
    }

    public Integer getLandId() { return landId; }

    public void setLandId(Integer landId) { this.landId=landId; }

    public Integer getInd() { return ind;}

    public void setInd(Integer index) { this.ind=index; }

    public Integer getWorldId() { return worldId; }

    public void setWorldId(Integer worldId) {
        this.worldId = worldId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getOwnerDifficultyLevel() {
        return ownerDifficultyLevel;
    }

    public void setOwnerDifficultyLevel(Integer ownerDifficultyLevel) {
        this.ownerDifficultyLevel = ownerDifficultyLevel;
    }

    public Integer getOwnerId() { return ownerId; }

    public void setOwnerId(Integer ownerId) { this.ownerId=ownerId; }

    public Integer getOwnerDifficulty() { return ownerDifficultyLevel; }

    public void setOwnerDifficulty(Integer ownerDifficultyLevel) { this.ownerDifficultyLevel = ownerDifficultyLevel; }
}
