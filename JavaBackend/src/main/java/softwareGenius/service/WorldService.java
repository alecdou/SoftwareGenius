package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.LandDao;
import softwareGenius.mapper.UserDao;
import softwareGenius.mapper.WorldDao;
import softwareGenius.model.World;

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

    public Integer initialNewWorld(Integer ownerId,Integer charId, Enum<AccountService.Category> category){
        int worldId=worldDao.addWorld(new World(ownerId,charId,category));
        return worldId;
    }


    World getWorldByWorldId(Integer worldId) {
        return worldDao.getWorldByWorldId(worldId);
    }

    Integer getCharIdByWorldId(Integer worldId) { return worldDao.getCharIdByWorldId(worldId); }
}
