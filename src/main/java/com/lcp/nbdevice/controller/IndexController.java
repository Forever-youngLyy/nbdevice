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
    static int lan = 0,yu=0,cai=0,huan=0,le=0,hong=0;
    static {
        try {
            path = new ApplicationHome(IndexController.class).getSource().getParentFile().toString() + "/";
        }catch (final Throwable e){
        }
    }

    @PostMapping({"/",""})
    public Object index(@RequestBody final Map<String, Object> map){
        //解密
        final File file = new File(path + fileName);
        try (FileOutputStream fos = new FileOutputStream(file,true);){
            final Map<String, String> payload = (Map)map.get("payload");
            final String appData = payload.get("APPdata");
            final byte[] bytes = Base64.getDecoder().decode(appData);
            if(!file.isFile()){
                file.createNewFile();
            }
            fos.write(bytes);
            fos.flush();
        }catch (final Throwable e){
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
        final StringBuilder s = new StringBuilder();
        int lenth = 0;
        try (InputStream is = new BufferedInputStream(new FileInputStream(new File(path + fileName)))) {
            final int len = is.available();
            final byte[] bytes = new byte[len];
            is.read(bytes, 0, len);
            s.append(new String(bytes));
        } catch (final Throwable e) {
            e.printStackTrace();
        }

        for (; i < s.toString().length() - 31; i++) {
            if (s.charAt(i) == 0x68) {
                cmd = (int) ((s.charAt(i + 26) << 8) + s.charAt(i + 27));
                switch (cmd) {
                    case 0x0A01:
                        lenth = s.charAt(i + 30);
                        if(lenth == 0)
                            continue;
                            
                            i += 20;
                        ret += "<p>上报时间:" + String.format("%02x-%02x-%02x %02x:%02x:%02x",(int) s.charAt(i), (int) s.charAt(i + 1), (int) s.charAt(i + 2),
                        (int) s.charAt(i + 3), (int) s.charAt(i + 4), (int) s.charAt(i + 5)) + "</p>";

                        i += 11;
                        
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

    @GetMapping({"/babafarm", ""})
    public Object getlink() {
            //String ret_link = "";
            final StringBuffer  htmlpage = new StringBuffer();
            // try (InputStream is = new BufferedInputStream(new FileInputStream(new File("html/link.html")))) {
     
            //     is.read(ret_link);
            // } catch (Throwable e) {
            //     e.printStackTrace();
            // }
            final File file = new File("html/link.html");

            //ret_link = file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();
            //file.toString();

            htmlpage.append("<html>");
            htmlpage.append("<body>");
            htmlpage.append("<head>各位的农场链接 </head>");
            //htmlpage.append("<p><a href=\"/lan\">兰的链接</a>  已点次数：" );
            htmlpage.append(String.format("<p><a href=\"/lan\">兰的链接</a>  已点次数：%d</p>", lan));
            htmlpage.append(String.format("<p><a href=\"/yu\">玉的链接</a>  已点次数：%d</p>", yu));
            htmlpage.append(String.format("<p><a href=\"/cai\">菜的链接</a>  已点次数：%d</p>", cai));
            htmlpage.append(String.format("<p><a href=\"/huan\">欢的链接</a>  已点次数：%d</p>", huan));
            htmlpage.append(String.format("<p><a href=\"/le\">乐的链接</a>  已点次数：%d</p>", le));
            htmlpage.append(String.format("<p><a href=\"/hong\">红的链接</a>  已点次数：%d</p>", hong));
            htmlpage.append("</body>");
            htmlpage.append("</html>");
            //file.
        return htmlpage.toString();

    }
    @GetMapping({"/lan", ""})
    public Object lanlink() {

        String ret_link = "";
        
        ret_link += "<html>";
        ret_link += "<body>";
        ret_link+="<script> \r\n";   


        ret_link += "function copyTxt0(){ document.getElementById(\"txtContent0\").select();  document.execCommand(\"Copy\"); } \r\n";
        ret_link += "function copyTxt1(){ document.getElementById(\"txtContent1\").select();  document.execCommand(\"Copy\"); }";

        ret_link +="</script>";

        ret_link += "<p><input value=\"9.0 http:// $3ZdYcNsPTjD₴🍑‰Ьáò【←═╬∞ミ噈快種峸啦！緊葽關頭！濡葽妳錒！~`】\" id=\"txtContent0\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt0()\" value=\"复制\" /> </p>";

        ret_link += "<p><input value=\"14点，我一下，肥料到手！ http:/5S84lIm36O5拜托帮我助力一下，使、用此文本前~往~支~付~宝~看一看，动动小手得奖励，一起来给果树施肥领水果！\" id=\"txtContent1\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt1()\" value=\"复制\" /> </p>";
        ret_link += "</body>";
        ret_link += "</html>";


        //ret_link +="</p>14点，我一下，肥料到手！ http:/5S84lIm36O5拜托帮我助力一下，使、用此文本前~往~支~付~宝~看一看，动动小手得奖励，一起来给果树施肥领水果！";
    lan++;
    return ret_link;
}
    @GetMapping({"/yu", ""})
    public Object yulink() {

        String ret_link = "";
        
        ret_link += "<html>";
       
        ret_link += "<body>";
        ret_link+="<script> \r\n";   


        ret_link += "function copyTxt0(){ document.getElementById(\"txtContent0\").select();  document.execCommand(\"Copy\"); } \r\n";
        ret_link += "function copyTxt1(){ document.getElementById(\"txtContent1\").select();  document.execCommand(\"Copy\"); }";

        ret_link +="</script>";

        ret_link += "<p><input value=\"70小手、一抖肥料到手！ http:/bc1ajSt59x9一起互相点击，复、制此内、容去支、付、宝看看，你也会得奖、励一起来水果包、邮、到、家\" id=\"txtContent0\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt0()\" value=\"复制\" /> </p>";

        ret_link += "<p><input value=\"9€ZSdkcnZcrxU€táo️Ьáò或點缶url链 https://m.tb.cn/h.44tXGXq?sm=70b206 至liulanqi【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】\" id=\"txtContent1\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt1()\" value=\"复制\" /> </p>";
        ret_link += "</body>";
        ret_link += "</html>";
    yu++;
    return ret_link;
}
    @GetMapping({"/cai", ""})
    public Object cailink() {

        String ret_link = "";
        
        ret_link += "<html>";
       
        ret_link += "<body>";
        ret_link+="<script> \r\n";   


        ret_link += "function copyTxt0(){ document.getElementById(\"txtContent0\").select();  document.execCommand(\"Copy\"); } \r\n";
        ret_link += "function copyTxt1(){ document.getElementById(\"txtContent1\").select();  document.execCommand(\"Copy\"); }";

        ret_link +="</script>";

        ret_link += "<p><input value=\"0.0¢KirpcnZ01Tn₴🍑づЬáò或點̸击̸链节 https://m.tb.cn/h.44tcMNR?sm=afb589 至流蓝琪【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】\" id=\"txtContent0\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt0()\" value=\"复制\" /> </p>";

        ret_link += "<p><input value=\"95小手一抖，肥~料~到~手 http:/v5vdN7080Jo拜托帮我助力一下，使用此消息来支~付~宝看一下，动动小手领肥~料一、起领水果。\" id=\"txtContent1\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt1()\" value=\"复制\" /> </p>";
        ret_link += "</body>";
        ret_link += "</html>";

        //String ret_link = "<p>0.0¢KirpcnZ01Tn₴🍑づЬáò或點̸击̸链节 https://m.tb.cn/h.44tcMNR?sm=afb589 至流蓝琪【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】" ;
        //ret_link +="</p>95小手一抖，肥~料~到~手 http:/v5vdN7080Jo拜托帮我助力一下，使用此消息来支~付~宝看一下，动动小手领肥~料一、起领水果。";
    cai++;
    return ret_link;
    
}

    @GetMapping({"/huan", ""})
    public Object huanlink() {

        String ret_link = "";
        
        ret_link += "<html>";
       
        ret_link += "<body>";
        ret_link+="<script> \r\n";   


        ret_link += "function copyTxt0(){ document.getElementById(\"txtContent0\").select();  document.execCommand(\"Copy\"); } \r\n";
        ret_link += "function copyTxt1(){ document.getElementById(\"txtContent1\").select();  document.execCommand(\"Copy\"); }";

        ret_link +="</script>";

        ret_link += "<p><input value=\"7.0₴L5ZLcnZ5ojX₤táoづЬáò或點几url链 https://m.tb.cn/h.44YngRR?sm=ebdee1 至liulanqi【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】\" id=\"txtContent0\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt0()\" value=\"复制\" /> </p>";

        ret_link += "<p><input value=\"97小手、一抖肥料到手！ http:/6639zVR7629快来帮我点击，ィ寸淛此消息去支、付、宝查看，点一点领肥、料一起兑、换水果。\" id=\"txtContent1\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt1()\" value=\"复制\" /> </p>";

        ret_link += "</body>";
        ret_link += "</html>";

        //String ret_link = "<p>7.0₴L5ZLcnZ5ojX₤táoづЬáò或點几url链 https://m.tb.cn/h.44YngRR?sm=ebdee1 至liulanqi【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】" ;
        //ret_link +="</p>97小手、一抖肥料到手！ http:/6639zVR7629快来帮我点击，ィ寸淛此消息去支、付、宝查看，点一点领肥、料一起兑、换水果。";
    huan++;
    return ret_link;
}


    @GetMapping({"/le", ""})
    public Object lelink() {

        String ret_link = "";
        
        ret_link += "<html>";
       
        ret_link += "<body>";
        ret_link+="<script> \r\n";   


        ret_link += "function copyTxt0(){ document.getElementById(\"txtContent0\").select();  document.execCommand(\"Copy\"); } \r\n";
        ret_link += "function copyTxt1(){ document.getElementById(\"txtContent1\").select();  document.execCommand(\"Copy\"); }";

        ret_link +="</script>";

        ret_link += "<p><input value=\"3 http:// £BEsIcNQtDIN₤ταΘ*Ьáò【\\️LOVEの閖淉灬 ️LOVEの閖淉灬 ！快看過來！】\" id=\"txtContent0\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt0()\" value=\"复制\" /> </p>";

        ret_link += "<p><input value=\"76点，我一下，肥~料~到~手 http:/FkokBfz75ac一起互相帮助，使、用此文本前~往~支~付~宝~查看，你也会得奖励，一、起种、水、果\" id=\"txtContent1\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt1()\" value=\"复制\" /> </p>";

        ret_link += "</body>";
        ret_link += "</html>";


        //String ret_link = "<p> 3 http:// £BEsIcNQtDIN₤ταΘ*Ьáò【\\️LOVEの閖淉灬 ️LOVEの閖淉灬 ！快看過來！】";
        //ret_link +="</p>76点，我一下，肥~料~到~手 http:/FkokBfz75ac一起互相帮助，使、用此文本前~往~支~付~宝~查看，你也会得奖励，一、起种、水、果";
    le++;
    return ret_link;
}
    @GetMapping({"/hong", ""})
    public Object honglink() {

        String ret_link = "";
        
        ret_link += "<html>";
       
        ret_link += "<body>";
        ret_link+="<script> \r\n";   


        ret_link += "function copyTxt0(){ document.getElementById(\"txtContent0\").select();  document.execCommand(\"Copy\"); } \r\n";
        ret_link += "function copyTxt1(){ document.getElementById(\"txtContent1\").select();  document.execCommand(\"Copy\"); }";

        ret_link +="</script>";

        ret_link += "<p><input value=\"4 http:// €MQyycngH3he£🍑~Ьáò【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】\" id=\"txtContent0\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt0()\" value=\"复制\" /> </p>";

        ret_link += "<p><input value=\"27小手一抖，肥~料~到~手 http:/iE4libe06k5快来帮我点击，复、制此内、容去支、付、宝查看，你也可以得奖、励一起加、入兑、换水果。\" id=\"txtContent1\" />";
        ret_link += "<input type=\"button\" onClick=\"copyTxt1()\" value=\"复制\" /> </p>";

        ret_link += "</body>";
        ret_link += "</html>";

        //String ret_link = "<p4 http:// €MQyycngH3he£🍑~Ьáò【.≮ミ免曊？！氺裹！敢來蹴ηεηɡ嗱！℅o。】" ;
        //ret_link +="</p>27小手一抖，肥~料~到~手 http:/iE4libe06k5快来帮我点击，复、制此内、容去支、付、宝查看，你也可以得奖、励一起加、入兑、换水果。";
    hong++;
    return ret_link;
}

}
