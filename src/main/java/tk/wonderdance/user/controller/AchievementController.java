package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.wonderdance.user.model.Achievement;
import tk.wonderdance.user.model.DanceGenreName;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.achievement.create.CreateAchievementFailResponse;
import tk.wonderdance.user.payload.achievement.create.CreateAchievementSuccessResponse;
import tk.wonderdance.user.payload.user.get_user.GetUserFailResponse;
import tk.wonderdance.user.payload.user.get_user.GetUserSuccessResponse;
import tk.wonderdance.user.repository.AchievementRepository;
import tk.wonderdance.user.repository.UserRepository;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/user/{user_id}/achievement")
public class AchievementController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AchievementRepository achievementRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createAchievement(@PathVariable("user_id") long userID,
                                               @RequestParam("title") String title,
                                               @RequestParam("dance_genre") DanceGenreName danceGenre,
                                               @RequestParam("competition") String competition,
                                               @RequestParam("year") int year){

        try {
            Optional<User> userQuery = userRepository.findUserById(userID);
            User user= userQuery.get();

            boolean success = true;
            Achievement achievement = new Achievement(title, danceGenre, competition, year, user);
            achievementRepository.save(achievement);
            user.getAchievements().add(achievement);
            userRepository.save(user);

            CreateAchievementSuccessResponse createAchievementSuccessResponse = new CreateAchievementSuccessResponse(success, achievement.getId());
            return ResponseEntity.ok(createAchievementSuccessResponse);
        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "User ID does not exist";

            CreateAchievementFailResponse createAchievementFailResponse = new CreateAchievementFailResponse(success, error_code, message);
            return ResponseEntity.ok(createAchievementFailResponse);
        }
    }
}
