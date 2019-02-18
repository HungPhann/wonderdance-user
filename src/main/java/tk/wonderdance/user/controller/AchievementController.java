package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.AchievementNotFoundException;
import tk.wonderdance.user.exception.exception.ForbiddenException;
import tk.wonderdance.user.exception.exception.UserNotFoundException;
import tk.wonderdance.user.model.Achievement;
import tk.wonderdance.user.model.DanceGenreName;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.achievement.create.CreateAchievementRequest;
import tk.wonderdance.user.payload.achievement.create.CreateAchievementResponse;
import tk.wonderdance.user.payload.achievement.delete.DeleteAchievementResponse;
import tk.wonderdance.user.payload.achievement.update.UpdateAchievementRequest;
import tk.wonderdance.user.payload.achievement.update.UpdateAchievementResponse;
import tk.wonderdance.user.repository.AchievementRepository;
import tk.wonderdance.user.repository.UserRepository;

import javax.validation.Valid;
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
                                               @Valid @RequestBody CreateAchievementRequest requestBody) throws UserNotFoundException, MethodArgumentTypeMismatchException {

        try {
            Optional<User> userQuery = userRepository.findUserById(userID);
            User user= userQuery.get();

            Achievement achievement = new Achievement(requestBody.getTitle(), requestBody.getDance_genre(), requestBody.getCompetition(), requestBody.getYear(), user);
            achievementRepository.save(achievement);
            user.getAchievements().add(achievement);
            userRepository.save(user);

            CreateAchievementResponse createAchievementResponse = new CreateAchievementResponse(achievement.getId());
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

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        catch (NoSuchElementException e){
            throw new AchievementNotFoundException("Cannot find Achievement with achievement_id=" + achievementID);
        }
    }


    @RequestMapping(value = "{achievement_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAchievement(@PathVariable("user_id") long userID,
                                               @PathVariable("achievement_id") long achievementID,
                                               @Valid @RequestBody UpdateAchievementRequest requestBody) throws MethodArgumentTypeMismatchException, AchievementNotFoundException, ForbiddenException{
        try {

            Optional<Achievement> achievementQuery = achievementRepository.findAchievementById(achievementID);
            Achievement achievement = achievementQuery.get();

            if(!(userID == achievement.getUser().getId())){
                throw new ForbiddenException("User with user_id=" + userID + "do not have permission to update Achievement with achievement_id=" + achievementID);
            }
            else {
                achievement.setTitle(requestBody.getTitle());
                achievement.setDanceGenre(requestBody.getDance_genre());
                achievement.setCompetition(requestBody.getCompetition());
                achievement.setYear(requestBody.getYear());
                achievementRepository.save(achievement);

                return new ResponseEntity<>(HttpStatus.OK);
            }

        }
        catch (NoSuchElementException e){
            throw new AchievementNotFoundException("Cannot find Achievement with achievement_id=" + achievementID);
        }
    }
}
