package softwareGenius.model;

/**
 * This represents a Leader Board for top players in a specific world
 */
public class Leaderboard {
    /**
     * The id of the world that the Leader Board specifically refers to
     */
    public Integer worldId;
    /**
     * Top players with score sorted in descending order
     */
    public User[] topUserList;

    public Leaderboard(Integer worldId, User[] userList) {
        this.worldId = worldId;
        this.topUserList = userList;
    }

    public void setWorldId(Integer worldId) {
        this.worldId = worldId;
    }

    public void setUserList(User[] userList) {
        this.topUserList = userList;
    }

    public Integer getWorldId() {
        return worldId;
    }

    public User[] getUserList() {
        return topUserList;
    }
}

