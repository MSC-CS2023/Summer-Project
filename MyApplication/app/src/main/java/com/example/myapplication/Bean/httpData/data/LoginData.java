<<<<<<<< Updated upstream:MyApplication/app/src/main/java/com/example/myapplication/Bean/Httpdata/data/LoginData.java
package com.example.myapplication.Bean.Httpdata.data;

import com.example.myapplication.Bean.Httpdata.User;
========
package com.example.myapplication.Bean.httpData.data;

import com.example.myapplication.Bean.httpData.User;
>>>>>>>> Stashed changes:MyApplication/app/src/main/java/com/example/myapplication/Bean/httpData/data/LoginData.java

public class LoginData {

    /**
     * The expiration time of the token. It is a timestamp (in seconds).
     */
    private Long exp;
    /**
     * A JWT token.
     */
    private String token;
    /**
     * Personal details of the user.
     */
    private User user;

    public Long getExp() { return exp; }
    public void setExp(Long value) { this.exp = value; }

    public String getToken() { return token; }
    public void setToken(String value) { this.token = value; }

    public User getUser() { return user; }
    public void setUser(User value) { this.user = value; }

}
