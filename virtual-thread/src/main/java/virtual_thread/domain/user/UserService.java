package virtual_thread.domain.user;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();
}
