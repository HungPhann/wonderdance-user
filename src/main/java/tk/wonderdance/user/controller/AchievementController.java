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
import tk.wonderdance.user.payload.achievement.delete.DeleteAchievementFailResponse;
import tk.wonderdance.user.payload.achievement.delete.DeleteAchievementSuccessResponse;
import tk.wonderdance.user.payload.achievement.update.UpdateAchievementFailResponse;
import tk.wonderdance.user.payload.achievement.update.UpdateAchievementSuccessResponse;
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


    @RequestMapping(value = "{achievement_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAchievement(@PathVariable("user_id") long userID,
                                               @PathVariable("achievement_id") long achivementID){
        try {

            Optional<Achievement> achievementQuery = achievementRepository.findAchievementById(achivementID);
            Achievement achievement = achievementQuery.get();

            if(!(userID == achievement.getUser().getId())){
                boolean success = false;
                int error_code = 2;
                String message = "Achievement ID  and User ID does not match";

                DeleteAchievementFailResponse deleteAchievementFailResponse = new DeleteAchievementFailResponse(success, error_code, message);
                return ResponseEntity.ok(deleteAchievementFailResponse);
            }
            else {
                achievementRepository.delete(achievement);

                boolean success = true;
                DeleteAchievementSuccessResponse deleteAchievementSuccessResponse = new DeleteAchievementSuccessResponse(success);
                return ResponseEntity.ok(deleteAchievementSuccessResponse);
            }

        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "Achievement ID does not exist";

            DeleteAchievementFailResponse deleteAchievementFailResponse = new DeleteAchievementFailResponse(success, error_code, message);
            return ResponseEntity.ok(deleteAchievementFailResponse);
        }
    }


    @RequestMapping(value = "{achievement_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAchievement(@PathVariable("user_id") long userID,
                                               @PathVariable("achievement_id") long achivementID,
                                               @RequestParam("title") String title,
                                               @RequestParam("dance_genre") DanceGenreName danceGenre,
                                               @RequestParam("competition") String competition,
                                               @RequestParam("year") int year){
        try {

            Optional<Achievement> achievementQuery = achievementRepository.findAchievementById(achivementID);
            Achievement achievement = achievementQuery.get();

            if(!(userID == achievement.getUser().getId())){
                boolean success = false;
                int error_code = 2;
                String message = "Achievement ID  and User ID does not match";

                UpdateAchievementFailResponse updateAchievementFailResponse = new UpdateAchievementFailResponse(success, error_code, message);
                return ResponseEntity.ok(updateAchievementFailResponse);
            }
            else {
                achievement.setTitle(title);
                achievement.setDanceGenre(danceGenre);
                achievement.setCompetition(competition);
                achievement.setYear(year);
                achievementRepository.save(achievement);

                boolean success = true;
                UpdateAchievementSuccessResponse updateAchievementSuccessResponse = new UpdateAchievementSuccessResponse(success);
                return ResponseEntity.ok(updateAchievementSuccessResponse);
            }

        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "Achievement ID does not exist";

            UpdateAchievementFailResponse updateAchievementFailResponse = new UpdateAchievementFailResponse(success, error_code, message);
            return ResponseEntity.ok(updateAchievementFailResponse);
        }
    }
}
