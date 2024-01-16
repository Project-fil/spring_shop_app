package com.github.ratel.utils;

import com.github.ratel.exceptions.NotValidException;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@UtilityClass
public class EntityUtil {

    public static Sort.Direction getSortDirection(String sortDir) {
        return sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    public static <T> T updateField(T fieldData, T fieldPayload) {
        if (fieldPayload != "" && fieldPayload != null) {
            fieldData = fieldPayload;
        }
        return fieldData;
    }

    public static <T> T convertToEntityType(Object dataVO, Object conData) {
        Object object = null;
        if (conData != "" && conData != null) {
            String convertObjToString = String.valueOf(conData);
            String dataType = dataVO.getClass().getSimpleName();
            switch (dataType) {
                case "String":
                    object = convertObjToString;
                    break;
                case "Integer":
                    object = Integer.parseInt(convertObjToString);
                    break;
                case "Long":
                    object = Long.parseLong(convertObjToString);
                    break;
                case "Double":
                    object = Double.parseDouble(convertObjToString);
                    break;
                case "BigDecimal":
                    object = new BigDecimal(convertObjToString);
                    break;
            }
        }
        @SuppressWarnings(value = "unchecked") final T result = (T) object;
        return result;
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
