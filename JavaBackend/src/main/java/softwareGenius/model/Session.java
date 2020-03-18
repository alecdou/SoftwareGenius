package softwareGenius.model;

import java.time.LocalDateTime;

public class Session {
    private Integer sessionId;
    private Integer userId;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    public Session(Integer sessionId, Integer userId, LocalDateTime loginTime, LocalDateTime logoutTime) {
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

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }
}





