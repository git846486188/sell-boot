package com.neuedu.sell.utill;

import java.util.Random;

public class KeyUtils {
    /*生成唯一id,利用随机数加上时间*/
    public static synchronized String generateUniqueKey(){
        Random random=new Random();
        int number=random.nextInt(900000)+100000;//保证生成六位

        return String.valueOf(System.currentTimeMillis())+number;
    }
}
