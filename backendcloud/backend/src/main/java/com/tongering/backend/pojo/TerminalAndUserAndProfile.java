package com.tongering.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerminalAndUserAndProfile {
    private Integer userId;
    private String ip;
    private Integer port;
    private String terminalName;
    private String terminalPassword;
    private String photo;
    private String name;
}
