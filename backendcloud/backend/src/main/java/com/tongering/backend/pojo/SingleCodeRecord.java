package com.tongering.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleCodeRecord {
    private Integer id;
    private Integer userId;
    private Integer exerciseId;
    private String result;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date recordTime;
    private String exerciseName;
    private Integer exerciseType;
}
