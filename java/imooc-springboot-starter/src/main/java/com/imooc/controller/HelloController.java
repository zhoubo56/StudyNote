package com.imooc.controller;

import com.imooc.exception.CustomException;
import com.imooc.pojo.MyConfig;
import com.imooc.pojo.Student;
import com.imooc.utils.MyAsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
//@Controller
public class HelloController {

    @GetMapping("/hello")
//    @RequestMapping("hello")
//    @ResponseBody
    public String Hello() {
        return "Hello world!!!";
    }

    @Autowired
    private Student student;

    @GetMapping("/student/1")
    public Object getStudent() {
        return student;
    }

    @Autowired
    private MyConfig myConfig;

    @GetMapping("/configs")
    public Object getConfigs() {
        return myConfig;
    }

    @Value("${self.custom.config.sdkSecret}")
    private String sdkSecret;
    @Value("${self.custom.config.port}")
    private String port;

    @GetMapping("/sdkSecret")
    public String getSdkSecret() {
        return sdkSecret + "\t" + port;
    }

    @Autowired
    private MyAsyncTask myAsyncTask;

    @PostMapping("/startTask")
    public String startTask() {
        myAsyncTask.publishMsg();
        return "success";
    }

    @GetMapping("/error")
    public String error() throws CustomException {
        throw new CustomException("custom exception");
//        return "";
    }
}
