package lv.ctco.cukes.rabbitmq.sample.controllers;

import lv.ctco.cukes.rabbitmq.sample.example.ExampleSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("example")
public class SendMessageExampleController {

    @Autowired
    ExampleSender sender;

    @RequestMapping("string")
    public String sendHello(@RequestParam("name") String name) {
        sender.send("Hello, " + name);
        return "OK";
    }

}
