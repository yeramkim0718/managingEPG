package com.example.demo.model.option;

import org.junit.jupiter.api.Test;

import java.util.List;

class EpgSettingIdTest {
    @Test
    public void getEpgSettingIds () {
        EpgSettingId option = new EpgSettingId();
        List<String> values = option.getValues();

        for (String value : values) {
            System.out.println(value);
        }
    }

}