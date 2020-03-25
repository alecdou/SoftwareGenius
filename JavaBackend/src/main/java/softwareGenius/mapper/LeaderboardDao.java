package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import softwareGenius.model.LeaderBoardRecord;
import softwareGenius.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is to get leader boards from different worlds
 */
@Mapper
@Component
public interface LeaderboardDao {
    ArrayList<LeaderBoardRecord> topUserForWorld1 = new ArrayList<LeaderBoardRecord>();
    ArrayList<LeaderBoardRecord> topUserForWorld2 = new ArrayList<LeaderBoardRecord>();
    ArrayList<LeaderBoardRecord> topUserForWorld3 = new ArrayList<LeaderBoardRecord>();
    ArrayList<LeaderBoardRecord> topUserForWorld4 = new ArrayList<LeaderBoardRecord>();
    ArrayList<LeaderBoardRecord> topUserForGeneral = new ArrayList<LeaderBoardRecord>();

    /**
     * To get a leader board for a specific category of world
     * @param worldCategory type of the world
     * @param offset user ranking with top <offset> players omitted.
     * @param limit num of user records
     * @return a list of user info and character experience
     */
    List<LeaderBoardRecord> getLeaderBoardByWorldName(@Param("worldCategory") String worldCategory, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * To get a leader board for general score across all worlds
     * @param offset user ranking with top <offset> players omitted.
     * @param limit num of user records
     * @return a list of user info and overall experience
     */
    List<LeaderBoardRecord>  getGeneralLeaderBoard(@Param("offset") int offset, @Param("limit") int limit);

    default List<LeaderBoardRecord> getLeaderBoardByWorldNameViaCache(@Param("worldCategory") String worldCategory, @Param("offset") int offset, @Param("limit") int limit){
        switch (worldCategory) {
            case "1":
                return topUserForWorld1.subList(offset, offset+limit);
            case "2":
                return topUserForWorld2.subList(offset, offset+limit);
            case "3":
                return topUserForWorld3.subList(offset, offset+limit);
            case "4":
                return topUserForWorld4.subList(offset, offset+limit);
            default:
                return getGeneralLeaderBoardViaCache(offset, limit);
        }
    };

    default List<LeaderBoardRecord>  getGeneralLeaderBoardViaCache(@Param("offset") int offset, @Param("limit") int limit){
        return topUserForGeneral.subList(offset, offset+limit);
    };

    default void setTopUserForWorld1(List<LeaderBoardRecord> topUsers){
        topUserForWorld1.clear();
        topUserForWorld1.addAll(topUsers);
    }

    default void setTopUserForWorld2(List<LeaderBoardRecord> topUsers){
        topUserForWorld2.clear();
        topUserForWorld2.addAll(topUsers);
    }

    default void setTopUserForWorld3(List<LeaderBoardRecord> topUsers){
        topUserForWorld3.clear();
        topUserForWorld3.addAll(topUsers);
    }

    default void setTopUserForWorld4(List<LeaderBoardRecord> topUsers){
        topUserForWorld4.clear();
        topUserForWorld4.addAll(topUsers);
    }

    default void setTopUserForGeneral(List<LeaderBoardRecord> topUsers){
        topUserForGeneral.clear();
        topUserForGeneral.addAll(topUsers);
    }
//    /**
//     * To get leader boards for all 4 worlds respectively and one for general score
//     * @param offset user ranking with top <offset> players omitted.
//     * @param limit num of user records
//     * @return a list of leaderboard
//     */
//    List<List<LeaderBoardRecord>> getAllLeaderBoard(@Param("offset") int offset, @Param("limit") int limit);
}
