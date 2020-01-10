# mybatis-generator-my-plugin

java中用boolean类型对应mysql用bit  有索引的尽量不要用null

## mybatis-generator 插件扩展

-  动态查询(where条件构建example不用检查有没有值)
-  Example增强(支持example链式调用)
-  Model增强（新缯内部类Column，asc(),desc()）
-  分页参数支持
-  xml Mapper文件覆盖（原来是append操作）
-  批量新增
-  批量更新
-  支持形如 a like ‘123%’ or a like '234%'的语句
-  生成Service与ServiceImpl

### 以下是默认实现的例子

    当值为null时为不会构造条件

    List<TaskAck> taskAckList = selectByExample(new TaskAckExample()
        .orderBy(TaskAck.Column.ackId.desc())
        .createCriteria()
        .andTaskIdEqualTo(submitAck.getTaskId())
        .andVendorIdEqualTo(submitAck.getVendorId())
        .andAckStatusIn(taskAckStatusList)
        .example());

### 以下是V2版本的例子(不会每个model对应一个example)

    当值为null时为不会构造条件
    
    Example example = new Example().and()
            .eq(UserTest.Column.age, null)
            .example();
    example.or().eq(UserTest.Column.age,null);
    List<UserTest> test = userTestService.selectByExample(example);
    
    
