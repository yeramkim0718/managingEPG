package com.example.demo.model.option;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Option {
    public List<String> getValues() {

        List<String> values = new ArrayList<String>();

        Field[] declaredFields = this.getClass().getDeclaredFields();

        for (Field field : declaredFields) {
            Object value = null;

            try {
                value = field.get(this);
                values.add((String)value);
            } catch (IllegalAccessException e) {

            }
        }

        return values;
    }
}
