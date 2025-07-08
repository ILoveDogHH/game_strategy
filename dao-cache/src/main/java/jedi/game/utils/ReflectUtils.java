package jedi.game.utils;

import java.lang.reflect.Field;

public class ReflectUtils {

    /**
     * 给对象的字段赋值，自动处理基本类型、包装类型的转换
     *
     * @param obj       目标对象
     * @param field     目标字段（已设置 accessible）
     * @param valueFromDb  从数据库取出的值（可能是 Integer、Long、BigDecimal、String等）
     * @throws IllegalAccessException 赋值异常
     */
    public static void setFieldValue(Object obj, Field field, Object valueFromDb) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        if (valueFromDb == null) {
            // 基本类型不能赋null，赋默认值
            if (fieldType.isPrimitive()) {
                if (fieldType == boolean.class) {
                    field.setBoolean(obj, false);
                } else if (fieldType == byte.class) {
                    field.setByte(obj, (byte) 0);
                } else if (fieldType == short.class) {
                    field.setShort(obj, (short) 0);
                } else if (fieldType == int.class) {
                    field.setInt(obj, 0);
                } else if (fieldType == long.class) {
                    field.setLong(obj, 0L);
                } else if (fieldType == float.class) {
                    field.setFloat(obj, 0f);
                } else if (fieldType == double.class) {
                    field.setDouble(obj, 0d);
                } else if (fieldType == char.class) {
                    field.setChar(obj, '\u0000');
                }
            } else {
                // 包装类型和引用类型赋null
                field.set(obj, null);
            }
            return;
        }

        // 非空时根据目标字段类型转换
        if (fieldType == String.class) {
            field.set(obj, valueFromDb.toString());
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            if (valueFromDb instanceof Boolean) {
                field.set(obj, valueFromDb);
            } else {
                // 一些数据库字段为0/1的情况
                field.set(obj, "1".equals(valueFromDb.toString()) || "true".equalsIgnoreCase(valueFromDb.toString()));
            }
        } else if (fieldType == byte.class || fieldType == Byte.class) {
            if (valueFromDb instanceof Number) {
                field.set(obj, ((Number) valueFromDb).byteValue());
            } else {
                field.set(obj, Byte.parseByte(valueFromDb.toString()));
            }
        } else if (fieldType == short.class || fieldType == Short.class) {
            if (valueFromDb instanceof Number) {
                field.set(obj, ((Number) valueFromDb).shortValue());
            } else {
                field.set(obj, Short.parseShort(valueFromDb.toString()));
            }
        } else if (fieldType == int.class || fieldType == Integer.class) {
            if (valueFromDb instanceof Number) {
                field.set(obj, ((Number) valueFromDb).intValue());
            } else {
                field.set(obj, Integer.parseInt(valueFromDb.toString()));
            }
        } else if (fieldType == long.class || fieldType == Long.class) {
            if (valueFromDb instanceof Number) {
                field.set(obj, ((Number) valueFromDb).longValue());
            } else {
                field.set(obj, Long.parseLong(valueFromDb.toString()));
            }
        } else if (fieldType == float.class || fieldType == Float.class) {
            if (valueFromDb instanceof Number) {
                field.set(obj, ((Number) valueFromDb).floatValue());
            } else {
                field.set(obj, Float.parseFloat(valueFromDb.toString()));
            }
        } else if (fieldType == double.class || fieldType == Double.class) {
            if (valueFromDb instanceof Number) {
                field.set(obj, ((Number) valueFromDb).doubleValue());
            } else {
                field.set(obj, Double.parseDouble(valueFromDb.toString()));
            }
        } else if (fieldType == char.class || fieldType == Character.class) {
            String s = valueFromDb.toString();
            if (s.length() > 0) {
                field.set(obj, s.charAt(0));
            }
        } else {
            // 其他类型，直接赋值，可能会失败
            field.set(obj, valueFromDb);
        }
    }
}
