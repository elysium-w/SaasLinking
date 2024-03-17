package org.saas.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 不存在跳转控制器
 */
@Controller
public class ShortLinkNotFoundController {
    /**
     * 短链接不存在跳转页面
     * @return 跳转到notfound页面
     */
    @RequestMapping("/page/notfound")
    public String notfound(){
        return "notfound";
    }

}
