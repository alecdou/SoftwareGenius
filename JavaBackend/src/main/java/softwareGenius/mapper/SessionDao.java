package softwareGenius.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.Session;

import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
@Component
public interface SessionDao {
    Boolean addSession(@Param("sessionId") Integer sessionId, @Param("userId") Integer userId, @Param("loginTime") LocalDateTime loginTime);
    Boolean updateSessionEndTime(@Param("sessionId") Integer sessionId, @Param("logoutTime") LocalDateTime logoutTime);
    List<Session> getSessionByUserID(@Param("sessionId") Integer sessionId);
}
