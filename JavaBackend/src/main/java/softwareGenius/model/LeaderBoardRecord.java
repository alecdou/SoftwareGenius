package softwareGenius.model;

public class LeaderBoardRecord {
    private String userName;
    private String userAvatar;
    private Integer charScore;

    public LeaderBoardRecord(String userName, String userAvatar, Integer charScore) {
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.charScore = charScore;
    }

    public LeaderBoardRecord() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getCharScore() {
        return charScore;
    }

    public void setCharScore(Integer charScore) {
        this.charScore = charScore;
    }
}
