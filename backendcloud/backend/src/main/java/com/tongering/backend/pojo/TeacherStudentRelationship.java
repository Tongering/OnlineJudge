package com.tongering.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherStudentRelationship {
    private Integer teacherId;
    private Integer studentId;
}
