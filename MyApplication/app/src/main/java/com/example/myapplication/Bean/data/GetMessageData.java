package com.example.myapplication.Bean.data;

import com.example.myapplication.Bean.Session;

import java.util.List;

public class GetMessageData {
    private List<Session> sessions;
    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
    public List<Session> getSessions() {
        return sessions;
    }
}
