package com.github.ratel.utils;

import com.github.ratel.exceptions.NotValidException;
import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@UtilityClass
public class UserUtil {

    public static <T> T updateField(T fieldData, T fieldPayload) {
        if (fieldPayload != "" && fieldPayload != null) {
            fieldData = fieldPayload;
        }
        return fieldData;
    }

    public <T> T updateFields(Object obj, @Valid Map<String, Object> data) {
        if (data.containsKey("id") || data.containsKey("_id"))
            throw new NotValidException("Access denied");
        for (String fieldName : data.keySet()) {
            try {
                Assert.notNull(data.get(fieldName), "data can't be null");
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, data.get(fieldName));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new NotValidException("Field '" + fieldName + "' not found");
            } catch (IllegalArgumentException e) {
                throw new NotValidException("Invalid data format for field '" + fieldName + "'");
            }
        }
        @SuppressWarnings(value = "unchecked") final T result = (T) obj;
        return result;
    }

    public Map<String, Object> createFieldsMap(Object obj) {
        Map<String, Object> data = new HashMap<>();
        if (Objects.nonNull(obj)) {
            try {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    String name = field.getName();
                    if (name.equals("id") || name.endsWith("id")) continue;
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    data.put(name, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (data.size() == 0) throw new NoSuchElementException("Objects in createFieldsMap() is null");
        return data;
    }

}
