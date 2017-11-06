package com.mvc.core.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by 我啊 on 2017-11-05 20:33
 */
public class TypeConverter {

    public static Object converter(Object f, Object t) {

        String typeName = (String) t;

        if (typeName.equals("java.lang.Boolean") || typeName.equals("boolean")) {
            return Boolean.parseBoolean((String) f);
        }

        if (typeName.equals("java.lang.Byte") || typeName.equals("byte")) {
            return Byte.parseByte((String) f);
        }

        if (typeName.equals("java.lang.Character") || typeName.equals("char")) {
            throw new IllegalArgumentException("String无法转化为Char");
        }


        if (typeName.equals("java.lang.Short") || typeName.equals("short")) {
            return Short.parseShort((String) f);
        }

        if (typeName.equals("java.lang.Integer") || typeName.equals("int")) {
            return Integer.parseInt((String) f);
        }

        if (typeName.equals("java.lang.Float") || typeName.equals("float")) {
            return Float.parseFloat((String) f);
        }

        if (typeName.equals("java.lang.Double") || typeName.equals("int")) {
            return Double.parseDouble((String) f);
        }
        if (typeName.equals("java.lang.Long") || typeName.equals("long")) {
            return Long.parseLong((String) f);
        }
        if (typeName.equals("java.util.Date")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            try {
                return sdf.parse((String) f);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
}
