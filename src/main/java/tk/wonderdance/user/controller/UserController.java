package tk.wonderdance.user.controller;

import com.amazonaws.services.lambda.model.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.CustomMethodArgumentTypeMismatchException;
import tk.wonderdance.user.exception.exception.UserNotFoundException;
import tk.wonderdance.user.helper.FollowUserTransaction;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.user.create.CreateUserRequest;
import tk.wonderdance.user.payload.user.get.GetUserResponse;
import tk.wonderdance.user.payload.user.update.UpdateUserResponse;
import tk.wonderdance.user.repository.UserRepository;
import tk.wonderdance.user.payload.user.create.CreateUserResponse;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowUserTransaction followUserTransaction;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest requestBody) throws MethodArgumentTypeMismatchException, ResourceConflictException, tk.wonderdance.user.exception.exception.ResourceConflictException {

        if (userRepository.existsByIdOrEmail(requestBody.getUser_id().longValue(), requestBody.getEmail())) {
            throw new tk.wonderdance.user.exception.exception.ResourceConflictException("User existed with user_id=" + requestBody.getUser_id() + " or email=" + requestBody.getEmail());

        } else {
            User user = new User(requestBody.getUser_id(), requestBody.getEmail(), requestBody.getFirst_name(), requestBody.getLast_name());
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("user_id") long userID,
                                     @RequestParam("required_data") List<String> requiredData) throws MethodArgumentTypeMismatchException, UserNotFoundException {

        Optional<User> userQuery = userRepository.findUserById(userID);

        try {
            User user= userQuery.get();
            boolean success = true;
            Map<String, Object> data = user.getInformation(requiredData);

            GetUserResponse getUserResponse = new GetUserResponse(success, data);
            return ResponseEntity.ok(getUserResponse);
        }
        catch (NoSuchElementException e){
            throw new UserNotFoundException("Cannot find User with user_id=" + userID);
        }
    }


    @RequestMapping(value = "{user_id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("user_id") long userID,
                                        @RequestBody Map<String, Object> data) throws MethodArgumentTypeMismatchException, UserNotFoundException, CustomMethodArgumentTypeMismatchException{

        try {
            Optional<User> userQuery = userRepository.findUserById(userID);
            User user= userQuery.get();
            user.updateInformation(data);
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            throw new UserNotFoundException("Cannot find User with user_id=" + userID);
        }
        catch (Exception e) {
            throw new CustomMethodArgumentTypeMismatchException("");
        }
    }
}
