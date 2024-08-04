package com.springboot.retrydemo.controller;

import com.springboot.retrydemo.model.PersonDetailDTO;
import com.springboot.retrydemo.service.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RetryController {

    @Autowired
    RetryService retryService;

        @PostMapping("/api/data")
        public ResponseEntity<List<PersonDetailDTO>> processData(@RequestBody Map<String, String> input)
                throws Exception {

            return ResponseEntity.ok(retryService.fetchPersonDetail(input));
        }
    }

