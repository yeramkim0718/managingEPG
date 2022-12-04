package com.example.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class EpgId implements Serializable {

    private String plfmCode;
    private String sdkVer;
    private String cntryCode;
    private String epgSettingId;
    private String publishFlag;

    public EpgId() {

    }

    public EpgId(String epgSettingId, String plfmCode, String sdkVer, String cntryCode, String publishFlag) {
        this.epgSettingId = epgSettingId;
        this.plfmCode = plfmCode;
        this.sdkVer = sdkVer;
        this.cntryCode = cntryCode;
        this.publishFlag = publishFlag;
    }

    @Override
    public int hashCode() {

        String identifier = epgSettingId.concat(plfmCode).concat(sdkVer).concat(cntryCode).concat(publishFlag);
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EpgId epgId = (EpgId) o;
        return Objects.equals(epgSettingId, epgId.epgSettingId) &&
                Objects.equals(plfmCode, epgId.plfmCode) &&
                Objects.equals(sdkVer, epgId.sdkVer) &&
                Objects.equals(cntryCode, epgId.cntryCode) &&
                Objects.equals(publishFlag, epgId.publishFlag);
    }


}
