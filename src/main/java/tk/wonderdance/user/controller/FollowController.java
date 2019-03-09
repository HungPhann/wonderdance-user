package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.CustomMethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.UserNotFoundException;
import tk.wonderdance.user.helper.FollowUserTransaction;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.follow.follow.FollowUserRequest;
import tk.wonderdance.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Controller
public class FollowController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserTransaction followUserTransaction;

    @RequestMapping(value = "/user/follow", method = RequestMethod.POST)
    public ResponseEntity<?> followUser(@Valid @RequestBody FollowUserRequest requestBody) throws MethodArgumentTypeMismatchException, UserNotFoundException, CustomMethodArgumentTypeMismatchException {

        if(requestBody.getFollower_user_id() == requestBody.getFollowing_user_id()){
            throw new CustomMethodArgumentTypeMismatchException("follower_user_id and following_user_id are identical");
        }

        try {
            User followerUser = userRepository.findUserById(requestBody.getFollower_user_id()).get();
            User followingUser = userRepository.findUserById(requestBody.getFollowing_user_id()).get();

            if(requestBody.getFollow()){
                return followUserHelper(followerUser, followingUser);
            }
            else {
                return unfollowUserHelper(followerUser, followingUser);
            }
        }
        catch (NoSuchElementException e){
            throw new UserNotFoundException("");
        }
    }


    private ResponseEntity<?> followUserHelper(User followerUser, User followingUser){

        try {
            followerUser.getFollowings().add(followingUser);
            followingUser.getFollowers().add(followerUser);
            followUserTransaction.saveUsers(followerUser, followingUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private ResponseEntity<?> unfollowUserHelper(User followerUser, User followingUser){

        try {
            followerUser.getFollowings().remove(followingUser);
            followingUser.getFollowers().remove(followerUser);
            followUserTransaction.saveUsers(followerUser, followingUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
