package softwareGenius.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


// TODO: changed according character and user
public class Report {
    private Float accuracy;
    private final Integer reportId;
    private Integer overallExp;
    List<Character> characterList;
    /** The type of the report: admin or player. */
    private final String type;
    /** The id of the player who the report describes. */
    private final Integer userId;
    private final String userEmail; // why do we need email?
    List<LocalDateTime> loginRecords;
    /** The average online time of a player */
    Duration avgGameTime;

    public Report(Float accuracy, Integer reportId, Integer overallExp, List<Character> characterList, String type, Integer userId, String userEmail, List<LocalDateTime> loginRecords, Duration avgGameTime) {
        this.accuracy = accuracy;
        this.reportId = reportId;
        this.overallExp = overallExp;
        this.characterList = characterList;
        this.type = type;
        this.userId = userId;
        this.userEmail = userEmail;
        this.loginRecords = loginRecords;
        this.avgGameTime = avgGameTime;
    }

    public Float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Float accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getOverallExp() {
        return overallExp;
    }

    public void setOverallExp(Integer overallExp) {
        this.overallExp = overallExp;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    public String getType() {
        return type;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public List<LocalDateTime> getLoginRecords() {
        return loginRecords;
    }

    public void setLoginRecords(List<LocalDateTime> loginRecords) {
        this.loginRecords = loginRecords;
    }

    public Duration getAvgGameTime() {
        return avgGameTime;
    }

    public void setAvgGameTime(Duration avgGameTime) {
        this.avgGameTime = avgGameTime;
    }

    public Integer getReportId() {
        return reportId;
    }

}
