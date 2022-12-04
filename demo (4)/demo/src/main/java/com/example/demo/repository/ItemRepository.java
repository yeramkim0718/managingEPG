package com.example.demo.repository;

import com.example.demo.model.Epg;
import com.example.demo.model.EpgId;
import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Epg, EpgId>  {

    List<Epg> findByPublishFlag(String publishFlag);

    @Query ("SELECT new com.example.demo.model.Item (epg.plfmCode, epg.sdkVer,epg.cntryCode) FROM Epg epg group by epg.plfmCode, epg.sdkVer, epg.cntryCode ")
    Page<Item> getGroupByItem(Pageable pageable);


    List<Epg> findByPlfmCodeAndSdkVerAndCntryCodeAndPublishFlag(String plfmCode, String sdkVer, String cntryCode, String publishFlag);

    @Query ("SELECT new com.example.demo.model.Item (epg.plfmCode, epg.sdkVer,epg.cntryCode) FROM Epg epg where epg.plfmCode like CONCAT('%',:plfmCode,'%') and epg.sdkVer like CONCAT('%',:sdkVer,'%')and epg.cntryCode like CONCAT('%',:cntryCode,'%') group by epg.plfmCode, epg.sdkVer, epg.cntryCode ")
   Page<Item> searchGroupByItem(@Param("plfmCode")String plfmCode, @Param("sdkVer") String sdkVer, @Param("cntryCode") String cntryCode, Pageable pageable);
}
