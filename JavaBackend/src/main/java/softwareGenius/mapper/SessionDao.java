package softwareGenius.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.Session;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * Used to interact with session database
 */
@Mapper
@Component
public interface SessionDao {
    /**
     * Add session to the database
     * @param sessionId the id of the session to be added
     * @param userId the id of the user
     * @param loginTime the time user logged in
     */
    Boolean addSession(@Param("sessionId") Integer sessionId, @Param("userId") Integer userId, @Param("loginTime") LocalDateTime loginTime);
    /**
     * Update session to the database
     * @param sessionId the id of the session to be added
     * @param logoutTime the time user logged out
     */
    Boolean updateSessionEndTime(@Param("sessionId") Integer sessionId, @Param("logoutTime") LocalDateTime logoutTime);
    /**
     * Get the session according to Id
     * @param sessionId the id of the session to be added
     */
    List<Session> getSessionByUserID(@Param("sessionId") Integer sessionId);
}
