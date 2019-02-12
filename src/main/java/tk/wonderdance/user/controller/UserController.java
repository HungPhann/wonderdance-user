package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.repository.UserRepository;
import tk.wonderdance.user.user.create.CreateUserFailResponse;
import tk.wonderdance.user.user.create.CreateUserSuccessResponse;

import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

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
}
