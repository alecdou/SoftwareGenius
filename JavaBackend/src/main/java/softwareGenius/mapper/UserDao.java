package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.User;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    int addUser(User user);
    User select(Integer userId);
    List<User> selectAll();
    int delete(Integer id);
    int update(User user);
}