package zone.god.blogprojectbe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zone.god.blogprojectbe.model.*;
import zone.god.blogprojectbe.model.message.request.LoginForm;
import zone.god.blogprojectbe.model.message.request.SignUpForm;
import zone.god.blogprojectbe.model.message.response.JwtResponse;
import zone.god.blogprojectbe.model.message.response.ResponseMessage;
import zone.god.blogprojectbe.security.jwt.JwtProvider;
import zone.god.blogprojectbe.service.RoleService;
import zone.god.blogprojectbe.service.UserService;
import zone.god.blogprojectbe.service.firebase.FirebaseStorageFileUploadService;

import javax.validation.Valid;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthoriseController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private FirebaseStorageFileUploadService firebaseStorageFileUploadService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        if (!userService.existsByUsername(loginRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("User with that username isn't exist"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setProvider("OmegaLUL");
        user.setAvatar(signUpRequest.getAvatar());
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userService.saveUser(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    @PostMapping("/socialLogin")
    public ResponseEntity<?> loginWithSocialAccount(@RequestBody SocialUser socialUser) {
        if (userService.existsByEmail(socialUser.email)) {
            if (getJwtResponseForSocialLogin(socialUser) == null) {
                return new ResponseEntity<>(new ResponseMessage("That email has already been registed with an username! Please login with that username instead"), HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(getJwtResponseForSocialLogin(socialUser));
    }

    @PostMapping("/socialLoginFirstTime")
    public ResponseEntity<?> loginWithSocialAccount(@RequestParam("socialUser") String socialUserInfo, @RequestParam("dob") String dob, @RequestParam("gender") String gender) throws JsonProcessingException {
        SocialUser socialUser = new ObjectMapper().readValue(socialUserInfo, SocialUser.class);
        User user = new User();
        user.setName(socialUser.getName());
        user.setUsername(socialUser.getEmail());
        user.setEmail(socialUser.getEmail());
        user.setPassword(encoder.encode(socialUser.getEmail()));
        user.setAvatar(socialUser.image);
        user.setProvider(socialUser.provider);
        user.setDob(dob);
        user.setGender(gender);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user.setRoles(roles);
        userService.saveUser(user);
        return ResponseEntity.ok(getJwtResponseForSocialLogin(socialUser));
    }

    @PostMapping("/user")
    public ResponseEntity<User> getUserbyUsername(@RequestBody String username) {
        User user = userService.findByUsername(username).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/checkUser")
    public ResponseEntity<?> checkIfUserExist(@RequestBody String username) {
        if (userService.existsByUsername(username)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("/changePass")
    public ResponseEntity<?> changePassword(@RequestParam("loginForm") String loginInfo, @RequestParam("newPassword") String newPassword) throws JsonProcessingException {
        LoginForm loginForm = new ObjectMapper().readValue(loginInfo, LoginForm.class);
        User user = userService.findByUsername(loginForm.getUsername()).get();
        if (encoder.matches(loginForm.getPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPassword));
            userService.saveUser(user);
            return new ResponseEntity<>(new ResponseMessage("Password changed succces ;)"), HttpStatus.ACCEPTED);

        }
        return new ResponseEntity<>(new ResponseMessage("Wrong current password :("), HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/changeUserInfo")
    public ResponseEntity<?> changeUserInfo(@RequestParam("name") String name, @RequestParam("avatarUrl") String avatarUrl, @RequestParam("username") String username, @RequestParam("dob") String dob, @RequestParam("gender") String gender) {
        try {
            User user = userService.findByUsername(username).get();
            user.setName(name);
            user.setAvatar(avatarUrl);
            user.setGender(gender);
            user.setDob(dob);
            userService.saveUser(user);
            return new ResponseEntity<>(new ResponseMessage("User Info change Success :)"), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("User Info change Fail :("), HttpStatus.NOT_ACCEPTABLE);
        }
    }


    private JwtResponse getJwtResponseForSocialLogin(SocialUser socialUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(socialUser.email, socialUser.email));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/avatarUpload")
    public ResponseEntity<String> uploadAvatar(@RequestParam("avatarImg") MultipartFile multipartFile) {
        String downloadUrl = firebaseStorageFileUploadService.uploadAvatarToFireBase(multipartFile);
        return new ResponseEntity<>(downloadUrl, HttpStatus.ACCEPTED);
    }
}
