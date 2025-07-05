package org.example.studyflowmaven.controller;

import lombok.RequiredArgsConstructor;
import org.example.studyflowmaven.entity.User;
import org.example.studyflowmaven.repository.UserRepository;
import org.example.studyflowmaven.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserInfoController {

    private final UserRepository userRepository;

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(Principal principal) {
        var username = principal.getName();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return Map.of(
                "username", user.getUsername(),
                "role", user.getRole().toString()
        );
    }
}
