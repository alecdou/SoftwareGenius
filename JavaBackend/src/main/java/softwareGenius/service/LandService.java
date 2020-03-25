package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.LandDao;
import softwareGenius.model.Land;

import java.util.List;

@Service
public class LandService {
    private LandDao landDao;

    @Autowired
    public LandService(LandDao landDao) {
        this.landDao = landDao;
    }

    /**
     * initialize lands for a world
     * @param worldId the id of the world
     * @return success or not
     */
    public Boolean initNewLand(Integer worldId) {
        for (int i=0;i<24;i++) {
            Land land=new Land(worldId,0,0);
            landDao.addLand(land);
        }
        return Boolean.TRUE;
    }

    /**
     * change the owner of a land
     * @param landId the id of the land
     * @param newOwnerId the new owner id
     * @param newDifficultyLevel the new difficulty level
     * @return success or not
     */
    //after receive combat result
    public Boolean changeOwner(Integer landId,Integer newOwnerId,Integer newDifficultyLevel) {
        landDao.changeOwner(landId,newOwnerId,newDifficultyLevel);
        return Boolean.TRUE;
    }

    /**
     * get all the land in a world
     * @param worldId the id of the world
     * @return a list of lands in belong to that world
     */
    public List<Land> getLandByWorld(Integer worldId) {
        return landDao.getLandByWorldId(worldId);
    }

}
