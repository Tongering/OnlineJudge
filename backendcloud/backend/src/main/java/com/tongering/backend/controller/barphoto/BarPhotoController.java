package com.tongering.backend.controller.barphoto;

import com.tongering.backend.pojo.BarPhoto;
import com.tongering.backend.service.barphoto.BarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BarPhotoController {
    @Autowired
    private BarPhotoService service;
    @GetMapping("/api/bar/photo/")
    public List<BarPhoto> getPhoto(){
        return service.getPhoto();
    }
}
