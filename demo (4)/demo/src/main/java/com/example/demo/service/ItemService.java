package com.example.demo.service;

import com.example.demo.model.Epg;
import com.example.demo.model.EpgId;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


        public List<Epg> getEpgsByItem(Item item) {
            return itemRepository.findByPlfmCodeAndSdkVerAndCntryCodeAndPublishFlag(item.getPlfmCode(), item.getSdkVer(), item.getCntryCode(), "N");
        }

        public Epg save(Epg item) {
            Epg saved = itemRepository.save(item);
            return saved;
        }

        public int delete(EpgId epgId) {
            itemRepository.deleteById(epgId);

            return epgId.hashCode();
        }

        public Epg update(EpgId originalEpgId, Epg update) {

            Epg original = itemRepository.getOne(originalEpgId);

            original.setUseFlag(update.getUseFlag());
            original.setActiveYn(update.getActiveYn());

            itemRepository.save(original);

            return original;
        }

    public Page<Item> getAllItems (Pageable pageable) {
        Page<Item> items =itemRepository.getGroupByItem(pageable);
        return items;
    }

    public Page<Item> searchItems(Pageable pageable, String plfmCode, String sdkVer , String cntryCode) {


            Page<Item> searchItems = itemRepository.searchGroupByItem(plfmCode, sdkVer, cntryCode, pageable);
            /*
        Page<Item> searchItems = itemRepository.findAll(new Specification<Epg>() {

            @Override
            public Predicate toPredicate(Root<Epg> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();

                if(!StringUtils.isEmpty(plfmCode) && Objects.nonNull(plfmCode)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("plfmCode"), plfmCode));
                }

                if(!StringUtils.isEmpty(cntryCode) && Objects.nonNull(cntryCode)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("cntryCode"), cntryCode));

                }

                if (!StringUtils.isEmpty(sdkVer) && Objects.nonNull(sdkVer)) {
                    p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("sdkVer"), sdkVer));

                }

                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("publishFlag"),"N"));


                return p;
            }
        },pageable);
*/
        return  searchItems;

    }


}
