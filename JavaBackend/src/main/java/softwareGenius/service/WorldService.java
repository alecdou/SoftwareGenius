package softwareGenius.service;

import org.springframework.stereotype.Service;
import softwareGenius.service.LandService;
import softwareGenius.mapper.LandDao;
import softwareGenius.mapper.UserDao;
import softwareGenius.mapper.WorldDao;
import softwareGenius.model.Land;
import softwareGenius.model.World;

@Service
public class WorldService {
    private WorldDao worldDao;
    private UserDao userDao;
    private LandDao landDao;

    public WorldService(WorldDao worldDao, UserDao userDao, LandDao landDao) {
        this.worldDao = worldDao;
        this.userDao = userDao;
        this.landDao = landDao;
    }

    public Integer initNewWorld(Integer ownerId, String worldCategory, Integer charId) {
        World world=new World(ownerId,charId, worldCategory);
        int worldId=worldDao.addWorld(world);
        //initLandForWorld()
        return worldId;
    }
}
