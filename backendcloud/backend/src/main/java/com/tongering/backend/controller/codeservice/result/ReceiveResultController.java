package com.tongering.backend.controller.codeservice.result;

import com.tongering.backend.mapper.CodeRecordMapper;
import com.tongering.backend.pojo.CodeRecord;
import com.tongering.backend.service.ReceiveResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

@RestController
public class ReceiveResultController {
    @Autowired
    private ReceiveResultService service;

    @Autowired
    private CodeRecordMapper mapper;
    @PostMapping("/api/receive/code/result/")
    public String runningresult(@RequestParam MultiValueMap<String, String>data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        String result = data.getFirst("result");
        Integer error_id = Integer.parseInt(Objects.requireNonNull(data.getFirst("error_id")));
        CodeRecord record = new CodeRecord(
                null, userId,
                Integer.parseInt(Objects.requireNonNull(data.getFirst("ExerciseId"))),
                Objects.requireNonNull(data.getFirst("code")),
                Objects.requireNonNull(data.getFirst("result")),
                Integer.parseInt(Objects.requireNonNull(data.getFirst("error_id"))),
                new Date()
                );
        mapper.insert(record);
        return service.receiveresult(userId,result,error_id);
    }
}
