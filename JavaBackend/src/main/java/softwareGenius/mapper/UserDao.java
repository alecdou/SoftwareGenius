package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.User;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    Boolean addUser(User user);
    User getUserById(Integer userId);
    List<User> getAll();
    Boolean deleteUser(Integer userId);
    Boolean updateUser(User user);
}