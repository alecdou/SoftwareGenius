package softwareGenius.model;

public class World {
    private final int ownerId;
    private final String category;

    public World(int ownerId, String category) {
        this.ownerId = ownerId;
        this.category = category;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getCategory() {
        return category;
    }

}
