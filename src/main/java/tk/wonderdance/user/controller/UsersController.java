package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.users.get.GetUsersResponse;
import tk.wonderdance.user.repository.UserRepository;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@RequestParam("user_ids") Set<Long> userIDs,
                                      @RequestParam("required_data") List<String> requiredData) throws MethodArgumentTypeMismatchException {



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

        return ResponseEntity.ok(data);
    }
}
