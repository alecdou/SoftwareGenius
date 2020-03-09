package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.DemoModel;

import java.util.List;

@Mapper
@Component
public interface DemoModelDao {
    int insert(DemoModel user);
    DemoModel select(Integer id);
    List<DemoModel> selectAll();
    int delete(Integer id);
    int update(DemoModel user);
}
