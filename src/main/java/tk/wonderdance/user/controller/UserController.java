package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.wonderdance.user.helper.FollowUserTransaction;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.user.get_user.GetUserFailResponse;
import tk.wonderdance.user.payload.user.get_user.GetUserSuccessResponse;
import tk.wonderdance.user.payload.user.get_users.GetUsersSuccessResponse;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
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

}
