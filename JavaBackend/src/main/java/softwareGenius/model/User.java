package softwareGenius.model;

public class User {
    private final Integer userId;
    private String userName;
    private String password;
    private String email;
    /** social account type: FB, Twitter, ... **/
    private final String accountType;
    /** user type **/
    private final Boolean isAdmin;
    private Float accuracy;
    private Integer overallExp;

    public User(Integer userId, String userName, String password, String email, String accountType, Boolean isAdmin,
                Float accuracy, Integer overallExp) {
        this.userId = userId;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


}
