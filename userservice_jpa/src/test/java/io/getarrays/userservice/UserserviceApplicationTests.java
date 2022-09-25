package io.getarrays.userservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.getarrays.userservice.domain.User;
import io.getarrays.userservice.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@SpringBootTest
class UserserviceApplicationTests {
    @Autowired
    private UserRepo userRepo;

    @Test
    void contextLoads() {
        String jwt = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwicm9sZXMiOlsiUk9MRV9NQU5BR0VSIiwiUk9MRV9VU0VSIl0sImlzcyI6Ii9hcGkvbG9naW4iLCJleHAiOjE2NjQxMTU4MDF9.BNSD1v8MISu0UmzGggT26AF5yryjylaQ8QJm3J75lw8";
        String token = jwt.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        User user = userRepo.findByUsername(username);
        System.out.println("---------------------------------");
        System.out.println(user.getName());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }

}
