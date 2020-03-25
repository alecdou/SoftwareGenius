package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.User;

import java.util.List;

@Mapper
@Component
public interface ReportDao {
    /**
     * Fetch the user object with matching userId
     * @param userId unique id of the user
     * @return User object
     */
    User getUserById(Integer userId);

    /**
     * Get all users
     * @return List of User objects
     */
    List<User> getAll();
}
