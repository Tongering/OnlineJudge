package com.tongering.backend.controller.Admin;

import com.tongering.backend.pojo.BarPhoto;
import com.tongering.backend.service.barphoto.BarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListBarPhotoController {
    @Autowired
    private BarPhotoService service;
    @PostMapping("/api/admin/bar/get/")
    public List<BarPhoto> get(){
        return service.getPhoto();
    }
}
