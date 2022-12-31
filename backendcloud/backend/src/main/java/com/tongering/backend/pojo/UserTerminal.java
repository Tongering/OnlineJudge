package com.tongering.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTerminal {
    private Integer userId;
    private String terminalIp;
    private Integer terminalPort;
    private String terminalUsername;
    private String terminalPassword;
}
