<<<<<<<< Updated upstream:MyApplication/app/src/main/java/com/example/myapplication/Bean/Httpdata/User.java
package com.example.myapplication.Bean.Httpdata;
========
package com.example.myapplication.Bean.httpData;
>>>>>>>> Stashed changes:MyApplication/app/src/main/java/com/example/myapplication/Bean/httpData/User.java

import java.io.Serializable;

public class User implements Serializable {

    /**
     * The address of the user.
     */
    private String address;
    /**
     * The email of the user.
     */
    private String email;
    /**
     * A unique ID for the user.
     */
    private Long id;
    /**
     * The telephone number of the user.
     */
    private String tel;
    /**
     * The username of the user.
     */
    private String username;

    public String getAddress() { return address; }
    public void setAddress(String value) { this.address = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getTel() { return tel; }
    public void setTel(String value) { this.tel = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }

}
