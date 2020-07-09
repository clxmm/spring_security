package org.clxmm.springsecurity.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clx
 * @date 2020/7/9 10:31
 */
@RestController
public class TestController {





    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }









}
