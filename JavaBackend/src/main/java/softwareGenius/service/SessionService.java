package softwareGenius.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import softwareGenius.mapper.SessionDao;
import softwareGenius.model.Session;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SessionService {

    private SessionDao sessionDao;

    @Autowired
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public Boolean addSession(Integer sessionId, Integer userId, LocalDateTime loginTime){
        return sessionDao.addSession(sessionId, userId, loginTime);
    }
    public Boolean updateSessionEndTime(Integer sessionId, LocalDateTime logoutTime){
        return sessionDao.updateSessionEndTime(sessionId, logoutTime);
    }
    public List<Session> getSessionByUserID(Integer sessionId){
        return sessionDao.getSessionByUserID(sessionId);
    }
}
