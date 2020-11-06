package com.lcp.nbdevice.controller;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Map;

@RequestMapping
@RestController
public class IndexController {

    private static String path = "";

    private static String fileName = "AppData";

    static {
        try {
            path = new ApplicationHome(IndexController.class).getSource().getParentFile().toString() + "/";
        }catch (Throwable e){
        }
    }

    @PostMapping({"/",""})
    public Object index(@RequestBody Map<String, Object> map){
        //解密
        File file = new File(path + fileName);
        try (FileOutputStream fos = new FileOutputStream(file,true);){
            Map<String, String> payload = (Map)map.get("payload");
            String appData = payload.get("APPdata");
            byte[] bytes = Base64.getDecoder().decode(appData);
            if(!file.isFile()){
                file.createNewFile();
            }
            fos.write(bytes);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return "{\"msg\":\"OK\"}";
    }

    @GetMapping({"/",""})
    public Object getBb(){
        StringBuilder s = new StringBuilder();
        try {
            FileInputStream is = new FileInputStream(new File(path + fileName));
            byte[] bytes = new byte[1024];
            int n = 0;
            while ((n = is.read(bytes)) != -1){
                s.append(new String(bytes));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
