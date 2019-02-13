package tk.wonderdance.user.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.repository.UserRepository;

import javax.transaction.Transactional;

@Component
public class FollowUserTransaction {
    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackOn = {Exception.class})
    public void saveUsers(User followerUser, User followingUser) throws Exception{
        userRepository.save(followerUser);
        userRepository.save(followingUser);
    }
}
