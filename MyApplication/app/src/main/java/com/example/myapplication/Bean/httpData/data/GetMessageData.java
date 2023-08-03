<<<<<<<< Updated upstream:MyApplication/app/src/main/java/com/example/myapplication/Bean/Httpdata/data/GetMessageData.java
package com.example.myapplication.Bean.Httpdata.data;

import com.example.myapplication.Bean.Httpdata.Session;
========
package com.example.myapplication.Bean.httpData.data;

import com.example.myapplication.Bean.httpData.Session;
>>>>>>>> Stashed changes:MyApplication/app/src/main/java/com/example/myapplication/Bean/httpData/data/GetMessageData.java

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
