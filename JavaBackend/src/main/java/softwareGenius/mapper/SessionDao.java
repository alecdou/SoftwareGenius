package softwareGenius.mapper;
import softwareGenius.model.Session
import java.util.List;
public interface SessionDao {
    Booolean addSession(@Param("sessionId") Integer sessionId, @Param("userId") Integer userId, @Param("loginTime") LocalDateTime loginTime);
    Boolean updateSessionEndTime(@Param("sessionId") Integer sessionId, @Param("logoutTime") LocalDateTime logoutTime);
    List<Session> getSessionByUserID(@Param("sessionId") Integer sessionId);
}
