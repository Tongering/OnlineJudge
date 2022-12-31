package com.tongering.coderunningsystem.service.impl;

import com.tongering.coderunningsystem.mapper.CodeRecordMapper;
import com.tongering.coderunningsystem.pojo.CodeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CodeRecordServiceImpl {
    @Autowired
    private  CodeRecordMapper mapper;

    public  String setRecord (Integer userId, Integer exerciseId, String userCode, String result, Integer errorTestId){
        Date create_time = new Date();
        CodeRecord record = new CodeRecord(null, userId, exerciseId, userCode, result, errorTestId, create_time);
        System.out.println(record);
        try {
            mapper.insert(record);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
