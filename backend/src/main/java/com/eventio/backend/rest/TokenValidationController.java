package com.eventio.backend.rest;

import com.eventio.backend.domain.Korisnik;
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

    @GetMapping("/validate")
    public ResponseEntity<String> validate(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Korisnik korisnik) {
                long userId = korisnik.getId();
                String username = korisnik.getUsername();

                String responseBody = "User ID: " + userId + ", Username: " + username;

                return ResponseEntity.ok(responseBody);
            } else {
              return ResponseEntity.ok("OK");
            }
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
}