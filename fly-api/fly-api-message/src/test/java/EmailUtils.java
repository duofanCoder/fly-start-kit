import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

/**
 * 测试邮件
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
public class EmailUtils {

    public static void main(String[] args) throws GeneralSecurityException {
        ArrayList<String> tos = CollUtil.newArrayList(
                "duofancc@qq.com");
        MailAccount mailAccount = new MailAccount();

        mailAccount.setHost("smtp.qq.com");
        mailAccount.setPort(465);
        mailAccount.setAuth(true);
        mailAccount.setSslEnable(true);
        mailAccount.setFrom("2633320940@qq.com");
        mailAccount.setPass("owrvhhfavcrmdigi");

        MailUtil.send(mailAccount, tos, "测试", "邮件来自Hutool群发测试", false);
    }
}
