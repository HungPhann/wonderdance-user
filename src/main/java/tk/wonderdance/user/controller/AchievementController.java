package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.AchievementNotFoundException;
import tk.wonderdance.user.exception.exception.ForbiddenException;
import tk.wonderdance.user.exception.exception.UserNotFoundException;
import tk.wonderdance.user.model.Achievement;
import tk.wonderdance.user.model.DanceGenreName;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.achievement.create.CreateAchievementResponse;
import tk.wonderdance.user.payload.achievement.delete.DeleteAchievementResponse;
import tk.wonderdance.user.payload.achievement.update.UpdateAchievementResponse;
import tk.wonderdance.user.repository.AchievementRepository;
import tk.wonderdance.user.repository.UserRepository;

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
                                               @RequestParam("year") int year) throws UserNotFoundException, MethodArgumentTypeMismatchException {

        try {
            Optional<User> userQuery = userRepository.findUserById(userID);
            User user= userQuery.get();

            boolean success = true;
            Achievement achievement = new Achievement(title, danceGenre, competition, year, user);
            achievementRepository.save(achievement);
            user.getAchievements().add(achievement);
            userRepository.save(user);

            CreateAchievementResponse createAchievementResponse = new CreateAchievementResponse(success, achievement.getId());
            return ResponseEntity.ok(createAchievementResponse);
        }
        catch (NoSuchElementException e){
            throw new UserNotFoundException("Cannot find User with user_id=" + userID);
        }
    }


    @RequestMapping(value = "{achievement_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAchievement(@PathVariable("user_id") long userID,
                                               @PathVariable("achievement_id") long achievementID) throws MethodArgumentTypeMismatchException, AchievementNotFoundException, ForbiddenException{

        try {
            Optional<Achievement> achievementQuery = achievementRepository.findAchievementById(achievementID);
            Achievement achievement = achievementQuery.get();

            if(!(userID == achievement.getUser().getId())){
                throw new ForbiddenException("User with user_id=" + userID + "do not have permission to delete Achievement with achievement_id=" + achievementID);
            }
            else {
                achievementRepository.delete(achievement);

                boolean success = true;
                DeleteAchievementResponse deleteAchievementResponse = new DeleteAchievementResponse(success);
                return ResponseEntity.ok(deleteAchievementResponse);
            }
        }
        catch (NoSuchElementException e){
            throw new AchievementNotFoundException("Cannot find Achievement with achievement_id=" + achievementID);
        }
    }


    @RequestMapping(value = "{achievement_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAchievement(@PathVariable("user_id") long userID,
                                               @PathVariable("achievement_id") long achievementID,
                                               @RequestParam("title") String title,
                                               @RequestParam("dance_genre") DanceGenreName danceGenre,
                                               @RequestParam("competition") String competition,
                                               @RequestParam("year") int year) throws MethodArgumentTypeMismatchException, AchievementNotFoundException, ForbiddenException{
        try {

            Optional<Achievement> achievementQuery = achievementRepository.findAchievementById(achievementID);
            Achievement achievement = achievementQuery.get();

            if(!(userID == achievement.getUser().getId())){
                throw new ForbiddenException("User with user_id=" + userID + "do not have permission to update Achievement with achievement_id=" + achievementID);
            }
            else {
                achievement.setTitle(title);
                achievement.setDanceGenre(danceGenre);
                achievement.setCompetition(competition);
                achievement.setYear(year);
                achievementRepository.save(achievement);

                boolean success = true;
                UpdateAchievementResponse updateAchievementResponse = new UpdateAchievementResponse(success);
                return ResponseEntity.ok(updateAchievementResponse);
            }

        }
        catch (NoSuchElementException e){
            throw new AchievementNotFoundException("Cannot find Achievement with achievement_id=" + achievementID);
        }
    }
}
