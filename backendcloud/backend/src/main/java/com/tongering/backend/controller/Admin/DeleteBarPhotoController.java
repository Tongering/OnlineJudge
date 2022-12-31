package com.tongering.backend.controller.Admin;

import com.tongering.backend.service.admin.DeleteBarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteBarPhotoController {
    @Autowired
    private DeleteBarPhotoService service;
    @PostMapping("/api/admin/bar/delete/")
    public Map<String, String> delete(@RequestParam Map<String, String> data){
        return service.delete(data);
    }
}
