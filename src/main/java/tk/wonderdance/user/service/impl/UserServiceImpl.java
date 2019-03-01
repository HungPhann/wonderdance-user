package tk.wonderdance.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tk.wonderdance.user.model.User;
import tk.wonderdance.user.repository.UserRepository;
import tk.wonderdance.user.service.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    public List<User> findUsers(Map<String, Object> filter, Pageable pageable){
        CriteriaBuilder cb =  entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        for(String key : filter.keySet()){
            switch (key){
                case "user_ids":
                    LinkedList<String> userIds = (LinkedList<String>) filter.get(key);
                    System.out.println(userIds);
                    System.out.println(userIds.getClass().getName());

                    List<Predicate> subPredicates = new ArrayList<>();
                    for (String userId : userIds){
                        subPredicates.add(cb.equal(root.get("id"), Integer.valueOf(userId)));
                    }
                    predicates.add(cb.or(subPredicates.toArray(new Predicate[0])));
                    break;

                case "name":
//                            Expression<String> exp = cb.concat(root.<String>get("firstName"), " "), root.<String>get("lastName"));

                    Expression<String> exp1 = cb.concat(root.<String>get("firstName"), " ");
                    exp1 = cb.concat(exp1, root.<String>get("lastName"));
                    Predicate predicate1 = cb.like(cb.lower(exp1), "%" + ((List<String>) filter.get(key)).get(0)+ "%") ;
                    Predicate predicate2 = cb.like(cb.lower(root.get("nickName")), "%" + ((List<String>) filter.get(key)).get(0) + "%");
                    predicates.add(cb.or(predicate1, predicate2));
                    break;

                case "city":
                    predicates.add(cb.equal(cb.lower(root.get("city")), ((List<String>) filter.get(key)).get(0)));
                    break;

                case "country":
                    predicates.add(cb.equal(root.get("country"), ((List<String>) filter.get(key)).get(0)));
                    break;

            }

        }
        query.where(cb.and(predicates.toArray(new Predicate[0]))) ;
        List<User> result = entityManager.createQuery(query).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        return result;
    }
}
