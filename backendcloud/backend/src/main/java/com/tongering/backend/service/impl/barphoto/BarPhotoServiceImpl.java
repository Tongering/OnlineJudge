package com.tongering.backend.service.impl.barphoto;

import com.tongering.backend.mapper.BarPhotoMapper;
import com.tongering.backend.pojo.BarPhoto;
import com.tongering.backend.service.barphoto.BarPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarPhotoServiceImpl implements BarPhotoService {
    @Autowired
    public BarPhotoMapper mapper;
    @Override
    public List<BarPhoto> getPhoto() {
        return mapper.selectList(null);
    }
}
