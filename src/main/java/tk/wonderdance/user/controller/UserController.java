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
import tk.wonderdance.user.payload.user.get_user.GetUserFailResponse;
import tk.wonderdance.user.payload.user.get_user.GetUserSuccessResponse;
import tk.wonderdance.user.payload.user.get_users.GetUsersSuccessResponse;
import tk.wonderdance.user.payload.user.unfollow.UnfollowUserFailResponse;
import tk.wonderdance.user.payload.user.unfollow.UnfollowUserSuccessResponse;
import tk.wonderdance.user.repository.UserRepository;
import tk.wonderdance.user.payload.user.create.CreateUserFailResponse;
import tk.wonderdance.user.payload.user.create.CreateUserSuccessResponse;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserTransaction followUserTransaction;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestParam("user_id") Long userID,
                                           @RequestParam("email") String email,
                                           @RequestParam("first_name") String firstName,
                                           @RequestParam("last_name") String lastName) {

        if (userRepository.existsByIdOrEmail(userID, email)) {
            boolean success = false;
            int error_code = 1;
            String message = "User ID or Email already existed";

            CreateUserFailResponse createUserFailResponse = new CreateUserFailResponse(success, error_code, message);
            return ResponseEntity.ok(createUserFailResponse);
        } else {
            User user = new User(userID, email, firstName, lastName);
            userRepository.save(user);

            boolean success = true;
            CreateUserSuccessResponse createUserSuccessResponse = new CreateUserSuccessResponse(success);
            return ResponseEntity.ok(createUserSuccessResponse);
        }
    }

    @RequestMapping(value = "{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("user_id") long user_id,
                                     @RequestParam("required_data") List<String> requiredData){

        Optional<User> userQuery = userRepository.findUserById(user_id);

        try {
            User user= userQuery.get();
            boolean success = true;
            Map<String, Object> data = user.getInformation(requiredData);

            GetUserSuccessResponse getUserSuccessResponse = new GetUserSuccessResponse(success, data);
            return ResponseEntity.ok(getUserSuccessResponse);
        }
        catch (NoSuchElementException e){
            boolean success = false;
            int error_code = 1;
            String message = "User ID does not exist";

            GetUserFailResponse getUserFailResponse = new GetUserFailResponse(success, error_code, message);
            return ResponseEntity.ok(getUserFailResponse);
        }
    }

    @RequestMapping(value = "information", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@RequestParam("user_ids") Set<Long> userIDs,
                                     @RequestParam("required_data") List<String> requiredData){



        List<User> users = new LinkedList<User>();
        for (Long userID : userIDs){
            try {
                User user = userRepository.findUserById(userID).get();
                ((LinkedList<User>) users).addFirst(user);
            }
            catch (NoSuchElementException e){
                //do nothing
            }
        }

        Set<Map<String, Object>> data = new HashSet<>();

        for (User user : users){
            Map<String, Object> userInfor = user.getInformation(requiredData);

            data.add(userInfor);
        }

        GetUsersSuccessResponse getUsersSuccessResponse = new GetUsersSuccessResponse(true, data);
        return ResponseEntity.ok(getUsersSuccessResponse);
    }

    @RequestMapping(value = "{user_id}/follow", method = RequestMethod.POST)
    public ResponseEntity<?> followUser(@PathVariable("user_id") long user_id,
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


    @RequestMapping(value = "{user_id}/unfollow", method = RequestMethod.POST)
    public ResponseEntity<?> unfollowUser(@PathVariable("user_id") long user_id,
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

    @RequestMapping(value = "{user_id}/followers", method = RequestMethod.GET)
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


    @RequestMapping(value = "{user_id}/followings", method = RequestMethod.GET)
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
