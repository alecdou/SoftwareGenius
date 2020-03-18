package softwareGenius.model;

public class User {
    private final Integer userId;
    private String username;
    private String userAvatar;
    private String password;
    private String email;
    /** social account type: FB, Twitter, ... **/
    private final String accountType;
    /** user type **/
    private final Boolean isAdmin;
    private Float accuracy;
    private Integer overallExp;

    public User(Integer userId, String username, String userAvatar, String password, String email, String accountType, Boolean isAdmin,
                Float accuracy, Integer overallExp) {
        this.userId = userId;
        this.username = username;
        this.userAvatar = userAvatar;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
        this.isAdmin = isAdmin;
        this.accuracy = accuracy;
        this.overallExp = overallExp;
    }

    public Integer getId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getOverallExp() {
        return overallExp;
    }

    public void setOverallExp(int overallExp) {
        this.overallExp = overallExp;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

}
