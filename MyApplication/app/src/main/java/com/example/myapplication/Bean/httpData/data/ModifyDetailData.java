<<<<<<<< Updated upstream:MyApplication/app/src/main/java/com/example/myapplication/Bean/Httpdata/data/ModifyDetailData.java
package com.example.myapplication.Bean.Httpdata.data;
========
package com.example.myapplication.Bean.httpData.data;
>>>>>>>> Stashed changes:MyApplication/app/src/main/java/com/example/myapplication/Bean/httpData/data/ModifyDetailData.java

public class ModifyDetailData {
    /**
     * The name of the attribute just changed.
     */
    private String key;
    /**
     * The new value.
     */
    private String value;

    public String getKey() { return key; }
    public void setKey(String value) { this.key = value; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
