package com.seven.config;

/**
 * @author: seven
 * @since: 2024/7/11 16:41
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //如果controller仅仅用于转发页面，那在当前方法中配置映射即可
        registry.addViewController("/login").setViewName("login");
    }
}

//TODO 等同于
//@Controller
//public class IndexController {
//
//
//    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }
//}