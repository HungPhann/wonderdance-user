package tk.wonderdance.user.service.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.wonderdance.user.model.User;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public List<User> findUsers(Map<String, Object> query, int page);

}
