<<<<<<<< Updated upstream:MyApplication/app/src/main/java/com/example/myapplication/Bean/Httpdata/Picture.java
package com.example.myapplication.Bean.Httpdata;
========
package com.example.myapplication.Bean.httpData;
>>>>>>>> Stashed changes:MyApplication/app/src/main/java/com/example/myapplication/Bean/httpData/Picture.java

public class Picture {
    /**
     * The image id.
     */
    private Long id;
    /**
     * The time when the image uploaded.
     */
    private String timestamp;

    public Long getId() { return id; }
    public void setId(Long value) { this.id = value; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String value) { this.timestamp = value; }
}
