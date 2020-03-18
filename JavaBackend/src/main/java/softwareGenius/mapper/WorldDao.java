package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.World;

import java.util.List;

@Mapper
@Component
public interface WorldDao {
    /**
     * Insert new World object to the database
     * @param world world object
     * @return status of the query (ex. True if query succeed)
     */
    Boolean addWorld(World world);

    /**
     * Fetch the world object with matching userId
     * @param userId id of the user
     * @return matching World object
     */
    World getWorldById(Integer userId);

    /**
     * Fetch the world object with matching category
     * @param category category of the world (ex. "SoftwareEngineering")
     * @return matching World object
     */
    World getWorldByCategory(String category);

    /**
     * Fetch the world object with matching ownerId
     * @param ownerId Id of the world owner
     * @return matching World object
     */
    World getWorldByOwner(Integer ownerId);

    /**
     * Get all the World objects
     * @return list of World objects
     */
    List<World> getAll();

    /**
     * Delete the world with matching userId
     * @param userId id of the user
     * @return status of the query (ex. True if query succeed)
     */
    Boolean deleteWorld(Integer userId);
}