package io.getarrays.userservice;

import io.getarrays.userservice.domain.Role;
import io.getarrays.userservice.domain.User;
import io.getarrays.userservice.repo.RoleRepo;
import io.getarrays.userservice.repo.UserRepo;
import io.getarrays.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserserviceApplicationTests {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        System.out.println("----------------------");
        userService.addRoleToUser("john", "ROLE_USER");
        System.out.println("-----------------------------");
    }

    @Test
    void SelectUserByUsernameTest() {
        System.out.println("----------------------");
        User john = userRepo.findByUsername("john");
        System.out.println(john);
        System.out.println("-----------------------------");
    }

}
