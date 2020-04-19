package softwareGenius.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import softwareGenius.mapper.SessionDao;
import softwareGenius.model.Session;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public Boolean addSession(Integer userId, Timestamp loginTime){
        return sessionDao.addSession(userId, loginTime);
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

}
