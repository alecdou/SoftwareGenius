package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import softwareGenius.model.User;

import java.util.List;
import java.util.Map;

/**
 * This is to get leader boards from different worlds
 */
@Mapper
public interface LeaderboardDao {
    /**
     * To get a leader board for a specific category of world
     * @param worldCategory type of the world
     * @param offset user ranking with top <offset> players omitted.
     * @param limit num of user records
     * @return a Leaderboard object, which contains a list of User with top scores
     */
    List<User> getLeaderBoardByWorld(@Param("worldCategory") String worldCategory, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * To get a leader board for general score across all worlds
     * @param offset user ranking with top <offset> players omitted.
     * @param limit num of user records
     * @return a Leaderboard object, which contains a list of User with top scores
     */
    List<User> getGeneralLeaderBoard(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * To get leader boards for all 4 worlds respectively and one for general score
     * @param offset user ranking with top <offset> players omitted.
     * @param limit num of user records
     * @return a list of leaderboard
     */
    Map<String, List<User>> getAllLeaderBoard(@Param("offset") int offset, @Param("limit") int limit);
}
