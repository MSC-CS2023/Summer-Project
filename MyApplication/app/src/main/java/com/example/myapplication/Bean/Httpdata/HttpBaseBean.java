package com.example.myapplication.Bean.Httpdata;

public class HttpBaseBean<T>{
        private Boolean success;
        private Integer code;
        private String message;
        private T data;

        public Boolean getSuccess(){
            return success;
        }
        public void setSuccess(Boolean input){
            this.success = input;
        }
        public int getCode(){
            return code;
        }
        public void setCode(int input){
            this.code = input;
        }
        public String getMessage(){
            return message;
        }
        public void setMessage(String input){
            this.message = input;
        }
        public T getData(){
            return data;
        }
        public void setData(T input){
            this.data = input;
        }

}
