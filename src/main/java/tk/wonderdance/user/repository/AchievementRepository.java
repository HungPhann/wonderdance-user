package tk.wonderdance.user.repository;

import org.springframework.data.repository.CrudRepository;
import tk.wonderdance.user.model.Achievement;

import java.util.Optional;

public interface AchievementRepository extends CrudRepository<Achievement, Long> {
    Optional<Achievement> findAchievementById(long id);
}
