package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter
@Entity
@Table(name="epg")
@IdClass(EpgId.class)
@EntityListeners(AuditingEntityListener.class)

public class Epg implements Serializable {


    @Id
    private String plfmCode;
    @Id
    private String sdkVer;
    @Id
    private String cntryCode;
    @Id
    private String epgSettingId;
    @Id
    private String publishFlag = "N";

    public Epg () {

    }

    public Epg(String epgSettingId, String plfmCode, String sdkVer, String cntryCode) {
        this.epgSettingId = epgSettingId;
        this.plfmCode = plfmCode;
        this.sdkVer = sdkVer;
        this.cntryCode = cntryCode;
    }

    private String activeYn;

    @CreatedBy
    private Long genUsrNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime crtDate;

    @LastModifiedBy
    private Long lastChgUsrNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastChgDate;

    private String useFlag;
    @Override
    public int hashCode() {

        String identifier = epgSettingId.concat(plfmCode).concat(sdkVer).concat(cntryCode).concat(publishFlag);
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epg item = (Epg) o;
        return Objects.equals(epgSettingId, item.epgSettingId) &&
                Objects.equals(plfmCode, item.plfmCode) &&
                Objects.equals(sdkVer, item.sdkVer) &&
                Objects.equals(cntryCode, item.cntryCode) &&
                Objects.equals(publishFlag, item.publishFlag);
    }

}


