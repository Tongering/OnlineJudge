package com.tongering.coderunningsystem.service.impl.util;

import com.tongering.coderunningsystem.pojo.CodeRecord;
import com.tongering.coderunningsystem.pojo.ExerciseTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    private Integer userId;
    private String code;
    private Integer exerciseId;
    private List<ExerciseTest> list;
    private Double time;
}
