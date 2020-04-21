package softwareGenius.model;

import java.sql.Timestamp;

public class Session {
    private Integer sessionId;
    private Integer userId;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public Session(){}

    public Session(Integer sessionId, Integer userId, Timestamp loginTime, Timestamp logoutTime) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }
}





