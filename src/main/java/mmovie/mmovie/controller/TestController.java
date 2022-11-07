package mmovie.mmovie.controller;

import lombok.extern.slf4j.Slf4j;
import mmovie.mmovie.common.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public String Test(){
        log.info(" =========== Test ===========");
        return "test";
    }

    @GetMapping("/login")
    public String Login(Model model){
        log.info(" =========== Login ===========");
        String value = SecurityUtil.getCurrentMemberId();
        log.info("getCurrentMemberId : "+value);

        model.addAttribute("value",value);
        return "login";
    }

}
