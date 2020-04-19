package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.LeaderboardDao;
import softwareGenius.model.Category;
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

    public List<LeaderBoardRecord> getLeaderBoardByWorldName(Category worldCategory, int offset, int limit){
        return leaderboardDao.getLeaderBoardByWorldNameViaCache(worldCategory, offset, limit);
    }

    public List<LeaderBoardRecord> getGeneralLeaderBoard(int offset, int limit){
        return leaderboardDao.getGeneralLeaderBoardViaCache(offset, limit);
    }

    public List<List<LeaderBoardRecord>> getAllLeaderBoard(int offset, int limit){
        List<List<LeaderBoardRecord>> all= new ArrayList<List<LeaderBoardRecord>>();
        all.add(getGeneralLeaderBoard(offset, limit));
        all.add(getLeaderBoardByWorldName(Category.SE, offset, limit));
        all.add(getLeaderBoardByWorldName(Category.SA, offset, limit));
        all.add(getLeaderBoardByWorldName(Category.PM, offset, limit));
        all.add(getLeaderBoardByWorldName(Category.QA, offset, limit));
        return all;
    }

    public int getOverallRankingByUserId(int userId){
        return leaderboardDao.getGeneralLeaderBoardViaCache().indexOf(userId);
    }

    @Scheduled(cron="* */30 * * * *")
    // Update evey 30 minutes
    public void cronUpdate(){
        try{
            leaderboardDao.setTopUserForWorld1(leaderboardDao.getLeaderBoardByWorldName(Category.SE, 0, 1000));
            leaderboardDao.setTopUserForWorld2(leaderboardDao.getLeaderBoardByWorldName(Category.SA, 0, 1000));
            leaderboardDao.setTopUserForWorld3(leaderboardDao.getLeaderBoardByWorldName(Category.PM, 0, 1000));
            leaderboardDao.setTopUserForWorld4(leaderboardDao.getLeaderBoardByWorldName(Category.QA, 0, 1000));
            leaderboardDao.setTopUserForGeneral(leaderboardDao.getGeneralLeaderBoard(0, 1000));
            System.out.println("Leader Board Updated!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
