package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.wonderdance.user.helper.FollowUserTransaction;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.user.follow.FollowUserFailResponse;
import tk.wonderdance.user.payload.user.follow.FollowUserSuccessResponse;
import tk.wonderdance.user.payload.user.follower_list.FollowerListFailResponse;
import tk.wonderdance.user.payload.user.follower_list.FollowerListSuccessResponse;
import tk.wonderdance.user.payload.user.following_list.FollowingListFailResponse;
import tk.wonderdance.user.payload.user.following_list.FollowingListSuccessResponse;
import tk.wonderdance.user.payload.user.unfollow.UnfollowUserFailResponse;
import tk.wonderdance.user.payload.user.unfollow.UnfollowUserSuccessResponse;
import tk.wonderdance.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Controller
public class FollowController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserTransaction followUserTransaction;

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ResponseEntity<?> followUser(@RequestParam("follower_user_id") long user_id,
                                        @RequestParam("following_user_id") long followingUserId){

        try {

            Optional<User> followerUserQuery = userRepository.findUserById(user_id);
            User followerUser = followerUserQuery.get();

            try {
                Optional<User> followingUserQuery = userRepository.findUserById(followingUserId);
                User followingUser = followingUserQuery.get();

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


    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    public ResponseEntity<?> unfollowUser(@RequestParam("follower_user_id") long user_id,
                                          @RequestParam("following_user_id") long followingUserId){

        try {

            Optional<User> followerUserQuery = userRepository.findUserById(user_id);
            User followerUser = followerUserQuery.get();

            try {
                Optional<User> followingUserQuery = userRepository.findUserById(followingUserId);
                User followingUser = followingUserQuery.get();

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
            catch (NoSuchElementException e){
                boolean success = false;
                int error_code = 2;
                String message = "Following User ID does not exist";

                UnfollowUserFailResponse unfollowUserFailResponse = new UnfollowUserFailResponse(success, error_code, message);
                return ResponseEntity.ok(unfollowUserFailResponse);
            }

        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "Follower User ID does not exist";

            UnfollowUserFailResponse unfollowUserFailResponse = new UnfollowUserFailResponse(success, error_code, message);
            return ResponseEntity.ok(unfollowUserFailResponse);
        }
    }

    @RequestMapping(value = "/followers/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFollowers(@PathVariable("user_id") long userID){

        try {
            Optional<User> userQuery = userRepository.findUserById(userID);
            User user= userQuery.get();
            boolean success = true;
            Set<Long> userIDs = User.getUserIds(user.getFollowers());

            FollowerListSuccessResponse followerListSuccessResponse = new FollowerListSuccessResponse(success, userIDs);
            return ResponseEntity.ok(followerListSuccessResponse);
        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "User ID does not exist";

            FollowerListFailResponse followerListFailResponse = new FollowerListFailResponse(success, error_code, message);
            return ResponseEntity.ok(followerListFailResponse);
        }
    }


    @RequestMapping(value = "/followings/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFollowings(@PathVariable("user_id") long userID){

        try {
            Optional<User> userQuery = userRepository.findUserById(userID);
            User user= userQuery.get();
            boolean success = true;
            Set<Long> userIDs = User.getUserIds(user.getFollowings());

            FollowingListSuccessResponse followingListSuccessResponse = new FollowingListSuccessResponse(success, userIDs);
            return ResponseEntity.ok(followingListSuccessResponse);
        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "User ID does not exist";

            FollowingListFailResponse followingListFailResponse = new FollowingListFailResponse(success, error_code, message);
            return ResponseEntity.ok(followingListFailResponse);
        }
    }
}
