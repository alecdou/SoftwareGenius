package softwareGenius.model;

import softwareGenius.service.AccountService;

public class World {
    private Integer worldId;
    private Integer unlock;
    private final Integer ownerId;
    private final Integer charId;
    private final Category category;

    public World(Integer worldId, Integer ownerId, Integer charId, Category category) {
        this.worldId = worldId;
        this.ownerId = ownerId;
        this.charId = charId;
        this.category = category;
        this.unlock=0;
    }

    public World(Integer ownerId, Integer charId, Category category) {
        this.ownerId = ownerId;
        this.charId = charId;
        this.category = category;
        this.unlock=0;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getWorldId() {
        return worldId;
    }

    public Integer getCharId() {
        return charId;
    }
}