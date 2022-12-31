package com.tongering.backend.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowRecord {
    private Integer userId;
    private String userPhoto;
    private String userName;
    private Integer codeId;
    private String codeName;
    private String codeType;
    private Integer recordId;
    private String recordResult;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date recordTime;
}
