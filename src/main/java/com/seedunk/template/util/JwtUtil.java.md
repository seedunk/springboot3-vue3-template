---

### [JwtUtil](#class)

[JWT 工具类，负责生成和验证 JWT 令牌](#description)

[src/main/java/newcloud/util/JwtUtil.java](#path)

#### [generateToken](#method)

[根据用户名生成 JWT 令牌](#description)

参数表：
- `String username` - 用户名

返回说明：String - JWT 令牌

#### [extractUsername](#method)

[从 JWT 令牌中提取用户名](#description)

参数表：
- `String token` - JWT 令牌

返回说明：String - 用户名

#### [validateToken](#method)

[验证 JWT 令牌是否有效](#description)

参数表：
- `String token` - JWT 令牌
- `UserDetails userDetails` - 用户详情

返回说明：boolean - 是否有效

