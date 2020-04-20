package softwareGenius.model;

public class User {
    private Integer userId;
    private String username;
    private String realName;
    private String password;
    private String email;
    /** social account type: FB, Twitter, ... **/
    private String accountType;
    /** user type **/
    private Boolean isAdmin;
    private Integer overallExp;

    public User() {}
    public User(Integer userId, String username, String realName, String password, String email, String accountType, Boolean isAdmin,
                Integer overallExp) {
        this.userId = userId;
        this.username = username;
        this.realName = realName;
        this.password = password;
        this.email = email;
        this.accountType = accountType;
        this.isAdmin = isAdmin;
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

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
         this.isAdmin = new Boolean(isAdmin);
    }

    public Integer getOverallExp() {
        return overallExp;
    }

    public void setOverallExp(int overallExp) {
        this.overallExp = overallExp;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
