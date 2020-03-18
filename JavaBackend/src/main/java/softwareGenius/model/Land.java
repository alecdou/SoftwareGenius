package softwareGenius.model;

public class Land {
    private Integer landId;
    private Integer ownerId;
    private Integer ownerDifficulty;

    public Land(Integer landId, Integer ownerId, Integer ownerDifficulty) {
        this.landId = landId;
        this.ownerId = ownerId;
        this.ownerDifficulty = ownerDifficulty;
    }

    public Integer getLandId() {
        return landId;
    }

    public void setLandId(Integer landId) {
        this.landId = landId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOwnerDifficulty() {
        return ownerDifficulty;
    }

    public void setOwnerDifficulty(Integer ownerDifficulty) {
        this.ownerDifficulty = ownerDifficulty;
    }
}
