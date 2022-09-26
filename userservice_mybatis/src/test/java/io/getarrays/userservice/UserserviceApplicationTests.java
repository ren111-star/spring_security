package io.getarrays.userservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.getarrays.userservice.domain.Role;
import io.getarrays.userservice.domain.User;
import io.getarrays.userservice.repo.RoleRepo;
import io.getarrays.userservice.repo.UserRepo;
import io.getarrays.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
        String jwt = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwicm9sZXMiOlsiSm9obiBUcmF2b2x0YSJdLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjY0MTE2NjYzfQ.UKUW2FsLqNcqv4AmbW16CIlLkuRA8qTRK64jC_7iJhg";
        String token = jwt.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        User user = userService.getUser(username);
        System.out.println("-----------------------------------------");
        System.out.println(user.getUsername());
        System.out.println(user.getName());
    }

    @Test
    void findUserByUsernameTest() {
        User will = userRepo.findByUsername("will");
        System.out.println(will);
    }

}
