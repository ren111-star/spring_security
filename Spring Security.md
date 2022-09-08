# Spring Boot and Spring Security with JWT including Access and Refresh Tokens 

## Spring Boot Backend

### dependencies

![image-20220907221335390](Spring Security.assets/image-20220907221335390.png)

### Database

#### domain

- User
  ```java
  @Entity @Data @NoArgsConstructor @AllArgsConstructor
  public class User {
      @Id @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;
      private String name;
      private String username;
      private String password;
      @ManyToMany(fetch = FetchType.EAGER)
      private Collection<Role> roles = new ArrayList<>();
  }
  ```

- Role
  ```java
  @Entity
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class Role {
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;
      private String name;
  }
  ```

#### repo

- RoleRepo
  ```java
  public interface RoleRepo extends JpaRepository<Role, Long> {
      Role findByName(String name);
  }
  ```

- UserRepo
  ```java
  public interface UserRepo extends JpaRepository<User, Long> {
      User findByUsername(String username);
  }
  ```

#### service and `Impl`

- `UserService`

  ```java
  public interface UserService {
      User saveUser(User user);
      Role saveRole(Role role);
      void addRoleToUser(String username, String roleName);
      User getUser(String username);
      List<User> getUsers();
  }
  ```

- `UserServiceImpl`

  ```java
  @Service 
  @RequiredArgsConstructor 
  @Transactional 
  @Slf4j
  public class UserServiceImpl implements UserService{
      private final UserRepo userRepo;
      private final RoleRepo roleRepo;
  
      @Override
      public User saveUser(User user) {
          return userRepo.save(user);
      }
  
      @Override
      public Role saveRole(Role role) {
          return roleRepo.save(role);
      }
  
      @Override
      public void addRoleToUser(String username, String roleName) {
          User user = userRepo.findByUsername(username);
          Role role = roleRepo.findByName(roleName);
          user.getRoles().add(role);
      }
  
      @Override
      public User getUser(String username) {
          return userRepo.findByUsername(username);
      }
  
      @Override
      public List<User> getUsers() {
          return userRepo.findAll();
      }
  }
  ```


#### `log4j`

```java
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding tole {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching users");
        return userRepo.findAll();
    }
}
```

#### create `api`

```java
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

   @PostMapping("/user/save")
   public ResponseEntity<User> saveUser(@RequestBody User user) {
       URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
       return ResponseEntity.created(uri).body(userService.saveUser(user));
   }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        // in these, 201 is better than 200, so we need create a url which from java.net
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> saveRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
```

#### properties

```properties
server.port=3000
spring.datasource.url=jdbc:mysql://localhost:3306/userservice?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=Rml901217
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true
```

#### Test

```java
@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner run (UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));


            userService.saveUser(new User(null, "John Travolta", "john", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Will Smith", "will", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));

            userService.addRoleToUser("john", "ROLE_USER");
            userService.addRoleToUser("john", "ROLE_MANAGER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_USER");
        };
    }
}
```

