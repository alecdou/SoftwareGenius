package softwareGenius.model;

public class NPC {
    private Integer npcId;
    private Integer difficulty;
    private Integer hitPoint;

    public NPC(Integer npcId, Integer difficulty, Integer hitPoint) {
        this.npcId = npcId;
        this.difficulty = difficulty;
        this.hitPoint = hitPoint;
    }

    public Integer getNpcId() {
        return npcId;
    }

    public void setNpcId(Integer npcId) {
        this.npcId = npcId;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(Integer hitPoint) {
        this.hitPoint = hitPoint;
    }
}
