package com.eventio.backend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenValidationController {

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}