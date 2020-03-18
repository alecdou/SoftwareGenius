package softwareGenius.mapper;

import softwareGenius.model.Land;
import java.util.List;

public interface LandDao {
    /**
     * add a land in a world to landTable
     * @param land
     * @return
     */
    Boolean addLand(Land land);

    /**
     * delete a land from landTable
     * @param landId
     * @return
     */
    Boolean deleteLand(Integer landId);

    /**
     * get all lands from a world
     * @param worldId
     * @return a list of lands
     */
    List<Land> getLandByWorldId(Integer worldId);

    /**
     * get a land information by its id
     * @param landId
     * @return land object
     */
    Land getLandByLandId(Integer landId);
}
