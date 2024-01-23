package edu.miu.demoinclass.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("This is admin home");
    }
}