package tk.wonderdance.user.repository;

import org.springframework.data.repository.CrudRepository;
import tk.wonderdance.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserById(long id);

    Boolean existsByIdOrEmail(Long id, String email);
}
