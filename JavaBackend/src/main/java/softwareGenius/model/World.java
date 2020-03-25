package softwareGenius.model;

import softwareGenius.service.AccountService;

public class World {
    private Integer worldId;
    private final Integer ownerId;
    private final Integer charId;
    private final Enum<AccountService.Category> category;


    public World(Integer worldId, Integer ownerId, Integer charId, Enum<AccountService.Category> category) {
        this.worldId = worldId;
        this.ownerId = ownerId;
        this.charId = charId;
        this.category = category;
    }

    public World(Integer ownerId, Integer charId, Enum<AccountService.Category> category) {
        this.ownerId = ownerId;
        this.charId = charId;
        this.category = category;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Enum<AccountService.Category> getCategory() {
        return category;
    }

    public Integer getWorldId() {
        return worldId;
    }

    public Integer getCharId() {
        return charId;
    }
}