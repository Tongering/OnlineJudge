package com.tongering.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSession {
    private Session session;
}
