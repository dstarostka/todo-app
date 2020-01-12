package projects.todolistapp.controller;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import projects.todolistapp.exceptions.UserAlreadyRegisteredException;
import projects.todolistapp.model.DTO.UserDTO;
import projects.todolistapp.model.entity.User;
import projects.todolistapp.service.EmailService;
import projects.todolistapp.service.UserService;
import projects.todolistapp.util.Mappings;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
public class  RegistrationController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService,
                                                                            EmailService emailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(Mappings.USER_REGISTRATION)
    public UserDTO registrationPage(UserDTO userDTO) {
        return userDTO;
    }

    @PostMapping(Mappings.USER_REGISTRATION)
    public ResponseEntity registerUserAccount(@RequestBody @Valid UserDTO userDTO,
                                    BindingResult bindingResult, HttpServletRequest request) {

        if (userService.findByEmail(userDTO.getEmail()) != null) {
            throw new UserAlreadyRegisteredException("User already registered under provided email address");
        }

        if (userService.findByUsername(userDTO.getUsername()) != null) {
            throw new UserAlreadyRegisteredException("User already registered under provided username");
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .enabled(false)
                .roles("ROLE_USER")
                .confirmationToken(UUID.randomUUID().toString())
                .build();

        userService.saveUser(user);

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();

        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(userDTO.getEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address visit the link below:\n"
                + appUrl + Mappings.USER_CONFIRM + "?token=" + user.getConfirmationToken());

        emailService.sendEmail(registrationEmail);

        return new ResponseEntity<>("Confirmation message has been sent to email address " + userDTO.getEmail(),
                HttpStatus.CREATED);
    }

    @GetMapping(Mappings.USER_CONFIRM)
    public void confirmationPage() {
    }

    @PostMapping(Mappings.USER_CONFIRM)
    public ResponseEntity processConfirmationForm(@RequestParam("token") String token,
                                          @RequestBody Map<String, String> requestParams, BindingResult bindingResult) {

        Zxcvbn passwordCheck = new Zxcvbn();
        Strength passwordStrength = passwordCheck.measure(requestParams.get("password"));

        if (passwordStrength.getScore() < 2) {
            return new ResponseEntity<>("Password too weak", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findByConfirmationToken(token);
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
        user.setEnabled(true);
        userService.saveUser(user);

        return new ResponseEntity<>("Password saved", HttpStatus.ACCEPTED);
    }
}