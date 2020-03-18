package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import softwareGenius.model.Land;

import java.util.List;

@Mapper
@Component
public interface LandDao {
    /**
     * add a land in a world to landTable
     * @param land land object
     * @return success or not
     */

    Boolean addLand(Land land);

    /**
     * delete a land from landTable
     * @param landId the id of the land
     * @return success or not
     */
    Boolean deleteLand(Integer landId);

    /**
     * get all lands from a world
     * @param worldId the id of the world
     * @return a list of lands
     */
    List<Land> getLandByWorldId(Integer worldId);

    /**
     * get a land information by its id
     * @param landId the id of the land
     * @return land object
     */
    Land getLandByLandId(Integer landId);

    /**
     * change the owner and ownerDifficultyLevel of a land
     * @param landId the id of the land
     * @param ownerId the id of the new owner
     * @param difficultyLevel the new difficulty level
     * @return success or not
     */
    Boolean changeOwner(@Param("lid") Integer landId, @Param("nid") Integer ownerId, @Param("ndl") Integer difficultyLevel);
}
