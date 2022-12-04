package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.model.option.CntryCode;
import com.example.demo.model.option.EpgSettingId;
import com.example.demo.model.option.PlfmCode;
import com.example.demo.model.option.SdkVer;
import com.example.demo.service.ItemService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    private List<String> oEpgSettingIds = (new EpgSettingId()).getValues();
    private List<String> oPlfmCodes = (new PlfmCode()).getValues();
    private List<String> oSdkVers = (new SdkVer()).getValues();
    private List<String> oCntryCodes = (new CntryCode()).getValues();


    @GetMapping("/login")
    public String loginForm(Model model) {
        //model.addAttribute("login_message", "로그인이 필요합니다.");
        return "login";
    }


    @PostMapping("/login")
    public String login() {

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/insert")
    public String insert(Model model) {
        model.addAttribute("oEpgSettingIds",oEpgSettingIds);
        model.addAttribute("oPlfmCodes", oPlfmCodes);
        model.addAttribute("oSdkVers",oSdkVers);
        model.addAttribute("oCntryCodes",oCntryCodes);
        return "addForm";
    }

    @PostMapping("/insert")
    public String insert(String epgSettingId, String plfmCode, String sdkVer, String cntryCode, String useFlag, String activeYn) {
        Epg epg = new Epg(epgSettingId, plfmCode, sdkVer, cntryCode);
        epg.setUseFlag(useFlag);
        epg.setActiveYn(activeYn);
        Epg saved = itemService.save(epg);
        return "redirect:/insert";
    }

    @PostMapping ("/detail")
    public String detail( Item item, Model model) throws IOException {
        List<Epg> epgs = itemService.getEpgsByItem(item);
        model.addAttribute("item",item);
        model.addAttribute("epgs",epgs);
        return "item";
    }

    /*
    @GetMapping("/edit")
    public String editForm(@PathVariable String epgSettingId,@PathVariable String plfmCode, @PathVariable String sdkVer, @PathVariable String cntryCode, Model model) {
        EpgId epgId = new EpgId(epgSettingId, plfmCode, sdkVer, cntryCode, "N");
        //Epg item = itemService.getItem(epgId);
        //model.addAttribute("item",item);

        model.addAttribute("oEpgSettingIds",oEpgSettingIds);
        model.addAttribute("oPlfmCodes", oPlfmCodes);
        model.addAttribute("oSdkVers",oSdkVers);
        model.addAttribute("oCntryCodes",oCntryCodes);

        return "editForm";
    }*/

    @PostMapping("/edit")
    public String edit(Model model, String epgSettingId, String plfmCode, String sdkVer, String cntryCode, String useFlag, String activeYn) {
        System.out.println("HomeController.edit");

        EpgId originalEpgId = new EpgId(epgSettingId, plfmCode, sdkVer, cntryCode, "N" );
        Epg update = new Epg();
        update.setEpgSettingId(epgSettingId);
        update.setPlfmCode(plfmCode);
        update.setSdkVer(sdkVer);
        update.setCntryCode(cntryCode);
        update.setUseFlag(useFlag);
        update.setActiveYn(activeYn);
        itemService.update(originalEpgId, update);
        model.addAttribute("item", new Item(plfmCode, sdkVer, cntryCode));

        return "redirect:/detail";
    }

    @PostMapping("/search")
    public String search(Model model, @PageableDefault(size=30)
                             @SortDefault.SortDefaults({@SortDefault(sort = "plfmCode"),
                                     @SortDefault(sort = "sdkVer"),
                                     @SortDefault (sort = "cntryCode")})  Pageable pageable,
                         String plfmCode, String sdkVer, String cntryCode) {
        Page<Item> searchItems = itemService.searchItems(pageable, plfmCode, sdkVer, cntryCode);

        int startPage = Math.max(1,searchItems.getPageable().getPageNumber()-5);
        int endPage = Math.max(Math.min(searchItems.getTotalPages(), searchItems.getPageable().getPageNumber()+5),1);

        model.addAttribute("username",SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("items",searchItems);

        model.addAttribute("oPlfmCodes", oPlfmCodes);
        model.addAttribute("oSdkVers",oSdkVers);
        model.addAttribute("oCntryCodes",oCntryCodes);

        model.addAttribute("plfmCode",plfmCode);
        model.addAttribute("sdkVer",sdkVer);
        model.addAttribute("cntryCode",cntryCode);

        return "items";

    }

    @PostMapping("/delete")
    public String delete(@RequestParam Map<String , String> attributes) {
        EpgId originalEpgId = new EpgId(attributes.get("epgSettingId"), attributes.get("plfmCode"), attributes.get("sdkVer"), attributes.get("cntryCode"), "N");
        itemService.delete(originalEpgId);
        return "redirect:/";
    }

    @GetMapping
    public String home(Model model ,  @PageableDefault(size=30)
            @SortDefault.SortDefaults({@SortDefault(sort = "plfmCode"),
                    @SortDefault(sort = "sdkVer"),
                    @SortDefault (sort = "cntryCode")})  Pageable pageable) {

        Page<Item> items = itemService.getAllItems(pageable);
        int startPage = Math.max(1,items.getPageable().getPageNumber()-5);
        int endPage = Math.max(Math.min(items.getTotalPages(), items.getPageable().getPageNumber()+5),1);

        model.addAttribute("username",SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("items",items);

        model.addAttribute("oPlfmCodes", oPlfmCodes);
        model.addAttribute("oSdkVers",oSdkVers);
        model.addAttribute("oCntryCodes",oCntryCodes);


        return "items";
    }

}
