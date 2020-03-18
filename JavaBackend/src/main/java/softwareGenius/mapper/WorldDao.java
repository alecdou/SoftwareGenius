package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.Character;
import softwareGenius.model.World;

import java.util.List;

@Mapper
@Component
public interface WorldDao {
    int addWorld(World world);
    World getWorldById(Integer userId);
    World getWorldByCategory(String category);
    World getWorldBy(String category);
    List<World> getAll();
    int deleteWorld(Integer userId);
}