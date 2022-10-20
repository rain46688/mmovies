package mmovie.mmovie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public String Test(){
        log.info(" =========== Test ===========");
        return "test";
    }

}
