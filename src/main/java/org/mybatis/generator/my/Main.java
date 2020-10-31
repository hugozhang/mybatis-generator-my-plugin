package org.mybatis.generator.my;

import org.mybatis.generator.api.ShellRunner;

public class Main {

    public static void main(String[] args) {
        String config = Main.class.getClassLoader().getResource("mybatis-generator.xml").getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);
    }

}
