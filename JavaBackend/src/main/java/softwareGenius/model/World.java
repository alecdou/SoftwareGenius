package softwareGenius.model;

public class World {
    private final Integer ownerId;
    private final String category;

    public World(Integer ownerId, String category) {
        this.ownerId = ownerId;
        this.category = category;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getCategory() {
        return category;
    }

}
