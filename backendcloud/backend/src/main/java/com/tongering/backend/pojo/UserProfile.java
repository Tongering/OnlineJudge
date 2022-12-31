package com.tongering.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String photo;
    private String email;
    private String declaration;
    private Date joinTime;
    private String background;
}
