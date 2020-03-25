package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.LeaderboardDao;
import softwareGenius.model.LeaderBoardRecord;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
@Lazy(value = false)
@Component
public class LeaderboardService {
    private LeaderboardDao leaderboardDao;

    @Autowired
    public LeaderboardService(LeaderboardDao leaderboardDao) {
        this.leaderboardDao = leaderboardDao;
    }

    public List<LeaderBoardRecord> getLeaderBoardByWorldName(String worldCategory, int offset, int limit){
        return leaderboardDao.getLeaderBoardByWorldNameViaCache(worldCategory, offset, limit);
    }

    public List<LeaderBoardRecord> getGeneralLeaderBoard(int offset, int limit){
        return leaderboardDao.getGeneralLeaderBoardViaCache(offset, limit);
    }

    public List<List<LeaderBoardRecord>> getAllLeaderBoard(int offset, int limit){
        List<List<LeaderBoardRecord>> all= new ArrayList<List<LeaderBoardRecord>>();
        all.add(getGeneralLeaderBoard(offset, limit));
        all.add(getLeaderBoardByWorldName("1", offset, limit));
        all.add(getLeaderBoardByWorldName("2", offset, limit));
        all.add(getLeaderBoardByWorldName("3", offset, limit));
        all.add(getLeaderBoardByWorldName("4", offset, limit));
        return all;
    }

    @Scheduled(cron="* */30 * * * *")
    // Update evey 30 minutes
    public void cronUpdate(){
        try{
            leaderboardDao.setTopUserForWorld1(leaderboardDao.getLeaderBoardByWorldName("1", 0, 1000));
            leaderboardDao.setTopUserForWorld2(leaderboardDao.getLeaderBoardByWorldName("2", 0, 1000));
            leaderboardDao.setTopUserForWorld3(leaderboardDao.getLeaderBoardByWorldName("3", 0, 1000));
            leaderboardDao.setTopUserForWorld4(leaderboardDao.getLeaderBoardByWorldName("4", 0, 1000));
            leaderboardDao.setTopUserForGeneral(leaderboardDao.getGeneralLeaderBoard(0, 1000));
            System.out.println("Leader Board Updated!");
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
