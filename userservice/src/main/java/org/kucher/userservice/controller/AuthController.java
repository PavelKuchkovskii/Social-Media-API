package org.kucher.userservice.controller;

import org.kucher.userservice.dto.message.Message;
import org.kucher.userservice.security.jwt.JwtTokenUtil;
import org.kucher.userservice.service.UserService;
import org.kucher.userservice.dto.UserCreateDTO;
import org.kucher.userservice.dto.UserLoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService service;

    public AuthController(AuthenticationManager authenticationManager, UserService service) {
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @PostMapping("/registration")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Message> doPost(@Valid @RequestBody UserCreateDTO dto) {

        service.create(dto);

        Message message = new Message("info", "To complete registration, please follow the link sent to your email");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Message> doPost(@Valid @RequestBody UserLoginDTO dto) {

        // Создание аутентификационного объекта
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword());

        // Аутентификация
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Установка аутентификации в контексте безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //В этом месте нужно сгенерировать токен для пользователя
        String token = JwtTokenUtil.generateAccessToken(authentication);

        Message message = new Message("info", token);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}