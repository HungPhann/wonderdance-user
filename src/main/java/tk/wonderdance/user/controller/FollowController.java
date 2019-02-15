package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.wonderdance.user.helper.FollowUserTransaction;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.follow.follow.FollowUserFailResponse;
import tk.wonderdance.user.payload.follow.follow.FollowUserSuccessResponse;
import tk.wonderdance.user.payload.follow.unfollow.UnfollowUserSuccessResponse;
import tk.wonderdance.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class FollowController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserTransaction followUserTransaction;

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ResponseEntity<?> followUser(@RequestParam("follower_user_id") long followerUserId,
                                        @RequestParam("following_user_id") long followingUserId,
                                        @RequestParam("follow") boolean follow){

        try {

            Optional<User> followerUserQuery = userRepository.findUserById(followerUserId);
            User followerUser = followerUserQuery.get();

            try {
                Optional<User> followingUserQuery = userRepository.findUserById(followingUserId);
                User followingUser = followingUserQuery.get();

                if(follow){
                    return followUserHelper(followerUser, followingUser);
                }
                else {
                    return unfollowUserHelper(followerUser, followingUser);
                }
            }
            catch (NoSuchElementException e){
                boolean success = false;
                int error_code = 2;
                String message = "Following User ID does not exist";

                FollowUserFailResponse followUserFailResponse = new FollowUserFailResponse(success, error_code, message);
                return ResponseEntity.ok(followUserFailResponse);
            }

        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "Follower User ID does not exist";

            FollowUserFailResponse followUserFailResponse = new FollowUserFailResponse(success, error_code, message);
            return ResponseEntity.ok(followUserFailResponse);
        }
    }


    private ResponseEntity<?> followUserHelper(User followerUser, User followingUser){
        followerUser.getFollowings().add(followingUser);
        followingUser.getFollowers().add(followerUser);

        try {
            followUserTransaction.saveUsers(followerUser, followingUser);

            boolean success = true;

            FollowUserSuccessResponse followUserSuccessResponse = new FollowUserSuccessResponse(success);
            return ResponseEntity.ok(followUserSuccessResponse);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private ResponseEntity<?> unfollowUserHelper(User followerUser, User followingUser){
        followerUser.getFollowings().remove(followingUser);
        followingUser.getFollowers().remove(followerUser);

        try {
            followUserTransaction.saveUsers(followerUser, followingUser);

            boolean success = true;

            UnfollowUserSuccessResponse unfollowUserSuccessResponse = new UnfollowUserSuccessResponse(success);
            return ResponseEntity.ok(unfollowUserSuccessResponse);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
