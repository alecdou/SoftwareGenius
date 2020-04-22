package softwareGenius.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import softwareGenius.mapper.SessionDao;
import softwareGenius.model.Session;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
@Service
public class SessionService {

    private SessionDao sessionDao;

    @Autowired
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    /**
     * Initiate a new session with given attributes
     * @param userId the id of the user
     * @param loginTime time that the user logged in
     * @return boolean of whether successful
     */
    public Boolean addSession(Integer userId, Timestamp loginTime, Timestamp logoutTime){
        return sessionDao.addSession(userId, loginTime, logoutTime);
    }

    /**
     * Initiate a new session with given attributes
     * @param sessionId the id of the session
     * @param logoutTime time that the user logged out
     * @return boolean of whether successful
     */
    public Boolean updateSessionEndTime(Integer sessionId, LocalDateTime logoutTime){
        return sessionDao.updateSessionEndTime(sessionId, logoutTime);
    }

    /**
     * Initiate a new session with given attributes
     * @param userId the id of the user
     * @return list of the session for this user
     */
    public List<Session> getSessionByUserID(Integer userId){
        return sessionDao.getSessionByUserID(userId);
    }

    /**
     * Get total game time of all users
     * @return string of flattened total game time
     */
    public String getTotalGameTime(){
        List<Session> allSession = sessionDao.getAllSession();
        return getTimeInterval(allSession);
    }

    /**
     * Get total game time of a particular user
     * @param userId id of the user
     * @return string of flattened total game time
     */
    public String getGameTimeByUserId(Integer userId){
        List<Session> userSessions = sessionDao.getSessionByUserID(userId);
        return getTimeInterval(userSessions);
    }

    private String getTimeInterval(List<Session> userSessions){
        Period totalGameDay = Period.ZERO;
        Duration totalGameTime = Duration.ZERO;
        for (Session s: userSessions) {
            totalGameDay.plus(Period.between(s.getLogoutTime().toLocalDateTime().toLocalDate(), s.getLoginTime().toLocalDateTime().toLocalDate()));
            totalGameTime.plus(Duration.between(s.getLogoutTime().toInstant(), s.getLoginTime().toInstant()));
        }

        return totalGameDay.getYears() + "Y " + totalGameDay.getMonths() + "M " +
                totalGameDay.getDays() + "D " + totalGameTime.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toUpperCase();
    }
}
