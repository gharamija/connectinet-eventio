package com.eventio.backend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transakcija")
public class TransakcijeControler {

    @PostMapping("/paypal")
    public ResponseEntity<String> PlacanjePayPal(){
        return null;
    }
    @PostMapping("/banka")
    public ResponseEntity<String> PlacanjeBanka(){
        return null;
    }
}
