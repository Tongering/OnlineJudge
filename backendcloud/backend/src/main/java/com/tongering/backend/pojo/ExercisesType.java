package com.tongering.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExercisesType {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer createId;
    private String typename;
    private String photo;
}
