package com.example.demo.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter @Setter
public class Item {
    private String plfmCode;
    private String sdkVer;
    private String cntryCode;

    public Item () {

    }

    public Item(String plfmCode, String sdkVer, String cntryCode) {
        this.plfmCode = plfmCode;
        this.sdkVer = sdkVer;
        this.cntryCode = cntryCode;
    }
}
