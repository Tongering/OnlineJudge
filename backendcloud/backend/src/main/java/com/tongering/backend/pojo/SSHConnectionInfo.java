package com.tongering.backend.pojo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SSHConnectionInfo {
    private Session session;
    private JSch jSch;
    private Channel channel;
}
