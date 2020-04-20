package softwareGenius.model;

public class LeaderBoardRecord {
    private Integer userId;
    private String userName;
    private String userAvatar;
    private Integer charScore;

    public LeaderBoardRecord(Integer userId, String userName, String userAvatar, Integer charScore) {
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
