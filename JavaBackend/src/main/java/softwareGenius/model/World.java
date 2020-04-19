package softwareGenius.model;

import softwareGenius.service.AccountService;

public class World {
    private Integer worldId;
    private Integer ownerId;
    private Integer charId;
    private Category category;

    public World(){}
    public World(Integer worldId, Integer ownerId, Integer charId, Category category) {
        this.worldId = worldId;
        this.ownerId = ownerId;
        this.charId = charId;
        this.category = category;
    }

    public World(Integer ownerId, Integer charId, Category category) {
        this.ownerId = ownerId;
        this.charId = charId;
        this.category = category;
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