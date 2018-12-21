# mybatis-generator-my-plugin

java中用boolean类型对应mysql用bit  有索引的尽量不要用null

### mybatis-gengerator 插件扩展

-  动态查询
-  Example增强(支持example链式调用)
-  Model增强（新缯内部类Column，asc(),desc()）
-  分页参数支持
-  xml Mapper文件覆盖（原来是append操作）
-  批量新增（没有检查null值）
-  批量更新（检查了null值）
-  支持形如 a like ‘123%’ or a like '234%'的语句
