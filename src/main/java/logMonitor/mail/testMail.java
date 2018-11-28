package logMonitor.mail;

import logMonitor.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class testMail {
    public static void main(String[] args) {
        String date = DateUtils.getDateTime();
        String content = "系统【订单】在 " + date + " 触发规则 1 ，过滤关键字为：error  错误内容：NullPointException" ;
        List<String> receiver = new ArrayList<>();
        receiver.add("2767873941@qq.com");
        MailInfo mailInfo = new MailInfo("系统运行日志监控", content, receiver, null);
        boolean b = MessageSender.sendMail(mailInfo);
        System.out.println("result : "+b);
    }
}
