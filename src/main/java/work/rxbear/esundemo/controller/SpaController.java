package work.rxbear.esundemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    @RequestMapping(value = {
            "/",               // 根路徑
            "/{x:[\\w\\-]+}",  // ex: /dashboard
            "/{x:^(?!api$).*$}/**" // 排除 /api 路由
    })
    public String forwardSPA() {
        return "forward:/index.html";
    }
}
