package com.tongering.coderunningsystem.controller;

import com.tongering.coderunningsystem.service.CodeRunningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class CodeRunningController {
    @Autowired
    private CodeRunningService service;

    @PostMapping("/api/code/add/")
    public String addCode(@RequestParam MultiValueMap<String, String> data){
        System.out.println(data);
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String code = data.getFirst("code");
        Integer exerciseId = Integer.parseInt(Objects.requireNonNull(data.getFirst("typeId")));
        return service.addCode(userId, code, exerciseId);
    }
}
