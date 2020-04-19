package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.LandDao;
import softwareGenius.mapper.UserDao;
import softwareGenius.mapper.WorldDao;
import softwareGenius.model.Category;
import softwareGenius.model.World;

import java.util.List;
import java.util.UUID;

@Service
public class WorldService {
    private WorldDao worldDao;
    private LandService landService;

    @Autowired
    public WorldService(WorldDao worldDao, LandService landservice) {
        this.worldDao = worldDao;
        this.landService = landservice;
    }

    public Integer initNewWorld(Integer ownerId,Integer charId, Category category){
        World world = new World(ownerId,charId,category);
        worldDao.addWorld(world);
        System.out.println(world.getWorldId());
        return world.getWorldId();
    }

    public void unlockWorld(Integer worldId) {
        worldDao.unlockWorld(worldId);
    }

    public World getWorldByWorldId(Integer worldId) {
        return worldDao.getWorldByWorldId(worldId);
    }

    public World getWorldByCharId(Integer charId) { return worldDao.getWorldByCharId(charId); }

    public List<World> getWorldByOwnerId(Integer ownerId) {
        return worldDao.getWorldByOwnerId(ownerId);
    }

    public Integer getCharIdByWorldId(Integer worldId) {
        System.out.println(worldId);
        return worldId;
    }
}
