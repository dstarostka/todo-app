package projects.todolistapp.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projects.todolistapp.util.Mappings;
import java.util.Date;
import java.util.Map;

@RestController(value = Mappings.USER_LOGIN)
public class LoginController {

    @PostMapping
    public String login(@RequestBody Map<String, String> requestParams) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(requestParams.get("email"))
                .claim("roles", "USER")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 20000))
                .signWith(SignatureAlgorithm.HS512, requestParams.get("password"))
                .compact();
    }
}