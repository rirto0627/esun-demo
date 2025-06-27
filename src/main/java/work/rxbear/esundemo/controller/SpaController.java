package work.rxbear.esundemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    @RequestMapping(value = {
            "/",                         // 根路徑
            "/{path:[^\\.]*}",           // 不包含點（例如 /about）
            "/**/{path:[^\\.]*}"         // 不包含副檔名的深層路由（例如 /foo/bar）
    })
    public String forwardSPA() {
        return "forward:/index.html";
    }
}

