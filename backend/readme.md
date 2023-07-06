# Backend
## 开发工具 SpringBoot
教程https://www.bilibili.com/video/BV1nV4y1s7ZN/

可在 https://mvnrepository.com/ 里搜索依赖

## 辅助工具 Apipost
模仿前端发送请求，代替前端来测试接口功能
### 教程
https://www.bilibili.com/video/BV1yv4y1d7yo/
### 官网下载
https://www.apipost.cn/

# 已完成部分

## 用户登录
通过向 /login 发送 POST 请求，在 body 中写上 id 和 password 两个参数。

通过验证会返回一个 token。

## JWT 验证
token 要放在 Header Authorization 字段中。

token 有效期 7 天。

若 token 过期，或 token 中用户不存在，或 token 非法，则无回复。（之后会用 Spring Security 带上回复）

### 以下token用于测试接口

* 时间不对的 token

eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIxMSIsImlhdCI6MTY4ODY4MTU4NywiZXhwIjoxNjg4NjgxNTg3fQ.M4sYS-t_nOxMgazlLhvdECvTgD07XwSZezjcbcn8e_M

* 用户ID不对的 token

eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiItMSIsImlhdCI6MTY4ODY4MTY4OCwiZXhwIjoxNjg5Mjg2NDg4fQ.a4DHuHx5eXwK4m3f4AfbmVQo78UapecA92X8rGUUgKk

* 可长期使用的 token，所代表用户 id 是 11

eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiIxMSIsImlhdCI6MTY4ODY4NjMyNSwiZXhwIjo0NzEyNjg2MzI1fQ.bNJz0gRaw7q_b1s0URF0aDckI1x0-jfLAjyHbHDni3s
