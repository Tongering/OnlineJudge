package com.tongering.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiComment {
    private Integer id;
    private Integer userId;
    private Integer invitationId;
    private String content;
    private Date createTime;
    private String photo;
    private String username;
}
