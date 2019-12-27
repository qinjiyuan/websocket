package top.mixedinfos.websocket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/demoController")
@RestController
public class DemoController {

    @RequestMapping(value = "/index")
    public String index(){
        return "demo";
    }
}
