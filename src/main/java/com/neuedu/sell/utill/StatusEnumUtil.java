package com.neuedu.sell.utill;

import com.neuedu.sell.enums.CodeEnum;

public class StatusEnumUtil {
    /*通过code值获取枚举对象*/
    public static <T extends CodeEnum> T getEnumByCode(Integer code,Class<T> EnumClass){
        for (T each: EnumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }

        }
        return null;
    }
}
