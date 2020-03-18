package softwareGenius.service;

import org.springframework.stereotype.Service;
import softwareGenius.mapper.LandDao;
import softwareGenius.mapper.UserDao;
import softwareGenius.mapper.WorldDao;
import softwareGenius.model.World;

@Service
public class WorldService {
    private WorldDao worldDao;
    private LandService landService;

    public WorldService(WorldDao worldDao, LandService landservice) {
        this.worldDao = worldDao;
        this.landService = landservice;
    }

    public Integer addWorld(World world) {
        int worldId=worldDao.addWorld(world);
        landService.initLandForWorld(worldId);
        return worldId;
    }

    World getWorldByWorldId(Integer worldId) {
        return worldDao.getWorldByWorldId(worldId);
    }

    Integer getCharIdByWorldId(Integer worldId) {
        return worldDao.getCharIdByWorldId(worldId);
    }
}
