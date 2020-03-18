package softwareGenius.mapper;
import softwareGenius.model.Session
public interface SessionDao {
    Booolean addSession(@Param("sessionId") Integer sessionId, @Param("userId") Integer userId, @Param("loginTime") LocalDateTime loginTime);
    Boolean updateSessionEndTime(@Param("sessionId") Integer sessionId, @Param("logoutTime") LocalDateTime logoutTime);
    Session[*] getSessionByUserID(@Param("sessionId") Integer sessionId);
}
