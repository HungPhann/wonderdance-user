package tk.wonderdance.user.service.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.wonderdance.user.model.User;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    List<User> findUsers(Map<String, Object> query, Pageable pageable);

}
