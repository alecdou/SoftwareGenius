package softwareGenius.model;

public class User {
    private final int userId;
    private String userName;
    private String password;
    private String email;
    private final String accountType; // social account type: FB, Twitter, ...
    private final Boolean isAdmin; // user type
    private float accuracy;
    private int overallExp;

    public User(int userId, String userName, String password, String email, String accountType, Boolean isAdmin, float accuracy, int overallExp) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
        this.isAdmin = isAdmin;
        this.accuracy = accuracy;
        this.overallExp = overallExp;
    }

    public int getId() {
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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public int getOverallExp() {
        return overallExp;
    }

    public void setOverallExp(int overallExp) {
        this.overallExp = overallExp;
    }


}
