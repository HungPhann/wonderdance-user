package tk.wonderdance.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.payload.users.get.GetUsersResponse;
import tk.wonderdance.user.repository.UserRepository;
import tk.wonderdance.user.service.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@RequestParam MultiValueMap requestParams,
                                      @RequestParam("required_data") List<String> requiredData,
                                      @RequestParam("page") int page) throws MethodArgumentTypeMismatchException {




        List<User> users = userService.findUsers(requestParams, PageRequest.of(page, 10));
        Set<Map<String, Object>> data = new HashSet<>();

        for (User user : users){
            Map<String, Object> userInfor = user.getInformation(requiredData);

            data.add(userInfor);
        }

        return ResponseEntity.ok(data);
    }
}
