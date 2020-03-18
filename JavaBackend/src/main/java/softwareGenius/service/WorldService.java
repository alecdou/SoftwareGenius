package softwareGenius.service;

import org.springframework.stereotype.Service;
import softwareGenius.mapper.LandDao;
import softwareGenius.mapper.UserDao;
import softwareGenius.mapper.WorldDao;
import softwareGenius.model.World;

@Service
public class WorldService {
    private WorldDao worldDao;
    private UserDao userDao;
    private LandService landService;

    public WorldService(WorldDao worldDao, UserDao userDao, LandDao landDao, LandService landservice) {
        this.worldDao = worldDao;
        this.userDao = userDao;
        this.landService = landservice;
    }

    public Integer initNewWorld(Integer ownerId, String worldCategory, Integer charId) {
        World world=new World(ownerId,charId, worldCategory);
        int worldId=worldDao.addWorld(world);
        landService.initLandForWorld(worldId);
        return worldId;
    }
}
