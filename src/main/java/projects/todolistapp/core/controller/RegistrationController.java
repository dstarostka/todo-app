package projects.todolistapp.core.controller;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projects.todolistapp.core.model.entity.User;
import projects.todolistapp.core.service.EmailService;
import projects.todolistapp.core.service.UserService;
import projects.todolistapp.core.util.Mappings;
import projects.todolistapp.core.util.ViewNames;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegistrationController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public RegistrationController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, EmailService emailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping(Mappings.REGISTER)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName(ViewNames.REGISTER);
        return modelAndView;
    }

    @PostMapping(Mappings.REGISTER)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user,
                                                BindingResult bindingResult, HttpServletRequest request) {

        User userExists = userService.findByEmail(user.getEmail());

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "User already registered under this email.");
            modelAndView.setViewName(ViewNames.REGISTER);
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(ViewNames.REGISTER);
        } else {
            user.setEnabled(false);
            user.setConfirmationToken(UUID.randomUUID().toString());
            userService.saveUser(user);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getLocalPort();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Registration Confirmation");
            registrationEmail.setText("To confirm your e-mail address visit link below:\n"
                    + appUrl + Mappings.CONFIRM + "?token=" + user.getConfirmationToken());

            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "Confirmation e-mail has been sent to "
                    + user.getEmail());
            modelAndView.setViewName(ViewNames.REGISTER);
        }
        return modelAndView;
    }

    @GetMapping(Mappings.CONFIRM)
    public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);
        if (user == null) {
            modelAndView.addObject("invalidToken", "Invalid confirmation link.");
        } else {
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }
        modelAndView.setViewName(ViewNames.CONFIRM);
        return modelAndView;
    }

    @PostMapping(Mappings.CONFIRM)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,
                                                @RequestParam Map<String, String> requestParams, RedirectAttributes redirect) {
        modelAndView.setViewName(ViewNames.CONFIRM);

        Zxcvbn passwordCheck = new Zxcvbn();
        Strength passwordStrength = passwordCheck.measure(requestParams.get("password"));

        if (passwordStrength.getScore() < 2) {
            bindingResult.reject("password");
            redirect.addFlashAttribute("errorMessage", "Your password is too weak. Choose stronger one.");
            modelAndView.setViewName("redirect:" + Mappings.CONFIRM + "?token=" + requestParams.get("token"));
            return modelAndView;
        }
        User user = userService.findByConfirmationToken(requestParams.get("token"));
        user.setPassword(requestParams.get("password"));
        user.setEnabled(true);
        userService.saveUser(user);
        modelAndView.addObject("successMessage", "Your password has been set.");

        return modelAndView;
    }
}