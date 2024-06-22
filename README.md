# CatCache 分布式缓存中间件
参考 redis server 实现。

## 基于RESP协议 
`Redis serialization protocol specification`  具体参考官网: https://redis.io/topics/protocol

**协议基础设计：**

`简单字符串（Simple Strings）`：以`+`字符开头，例如："+OK\r\n"

`错误信息（Errors）`：以`-`字符开头，例如："-Error message\r\n"

`整数（Integers）`：以`:`字符开头，例如：":1000\r\n"

`块字符串（Bulk Strings）`：以`$`字符开头，后跟字符串长度和字符串内容，例如："$6\r\nfoobar\r\n"

`数组（Arrays）`：以`*`字符开头，后跟数组的长度和数组元素，例如："*3\r\n$3\r\nfoo\r\n$3\r\nbar\r\n$5\r\nhello\r\n"

## 当前进展
* 基于 netty 完成 mock redis server 初版，可通过 redis-cli 发送指令
* 实现 redis 部分 common 和 string 类型指令处理
* 通过命令模式重构代码
* 增加 list 类型指令
* 增加 set 类型指令
