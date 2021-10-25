package com.sso.login.pojo;

import java.util.HashSet;
import java.util.Set;

public class DB {
    public static Set<User> dbusers=new HashSet<>();
    static {
        dbusers.add(new User(1,"keqing","123456"));
        dbusers.add(new User(2,"xinhai","123456"));
        dbusers.add(new User(3,"diluke","123456"));
        dbusers.add(new User(4,"leidianjiangjun","123456"));
        dbusers.add(new User(5,"zhongli","123456"));
    }
}
