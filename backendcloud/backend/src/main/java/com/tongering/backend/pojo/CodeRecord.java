package com.tongering.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer exerciseId;
    private String userCode;
    private String result;
    private Integer errorTestId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date recordTime;
    public boolean equals(Object obj) {
        CodeRecord u = (CodeRecord) obj;
        return exerciseId.equals(u.exerciseId);
    }
    public int hashCode() {
        Integer in = exerciseId;
        return in.hashCode();
    }
}
