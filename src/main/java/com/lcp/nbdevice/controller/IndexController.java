package com.lcp.nbdevice.controller;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;

import java.io.*;
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
            fos.flush();
        }catch (Throwable e){
            e.printStackTrace();
        }
        return "{\"msg\":\"OK\"}";
    }

    @GetMapping({"/", ""})
    public Object getBb() {
        int i = 0;
        String ret = "";
        int j = 0;
        int cmd = 0;
        StringBuilder s = new StringBuilder();
        int lenth = 0;
        try (InputStream is = new BufferedInputStream(new FileInputStream(new File(path + fileName)))) {
            int len = is.available();
            byte[] bytes = new byte[len];
            is.read(bytes, 0, len);
            s.append(new String(bytes));
        } catch (Throwable e) {
            e.printStackTrace();
        }

        for (; i < s.toString().length() - 31; i++) {
            if (s.charAt(i) == 0x68) {
                cmd = (int) ((s.charAt(i + 26) << 8) + s.charAt(i + 27));
                switch (cmd) {
                    case 0x0A01:
                        lenth = s.charAt(i + 30);
                        i += 31;
                        ret += "<p>" + String.format("数据%d条   \n", lenth);
                        for (j = 0; j < lenth && i < s.toString().length() - 7; j++) {
                            ret = ret + String.format("%s 时间:%02x-%02x-%02x %02x:%02x:%02x", s.charAt(i + 1) > 0 ? "B->A" : "A->B", (int) s.charAt(i + 2),
                                    (int) s.charAt(i + 3), (int) s.charAt(i + 4), (int) s.charAt(i + 5), (int) s.charAt(i + 6), (int) s.charAt(i + 7)) + "\n";
                            i += 8;
                        }
                        ret += "</p>";
                        break;
                    case 0x0A02:
                        break;
                }
            }
        }
        return ret;
    }
}
