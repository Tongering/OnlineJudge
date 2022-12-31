package com.tongering.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndUserProfile {
    private Integer userId;
    private String userName;
    private String userPhoto;

}
