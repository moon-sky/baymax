package com.dreamfac.xiaobin.mail;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;

public class MailUtil {
/**
 * @param user 用户�?
 * @param qq QQ�?
 * @param content 反馈内容
 * @return 1 失败  0 成功
 */
public static int sendEmail(String user,String qq,String content){
    //这个类主要是设置邮件   
	try{
		MailSenderInfo mailInfo = new MailSenderInfo();    
		 mailInfo.setMailServerHost("smtp.163.com");    
		 mailInfo.setMailServerPort("25");    
		 mailInfo.setValidate(true);    
		 mailInfo.setUserName("moonvsky888@163.com");    
		 mailInfo.setPassword("wd613923");//您的邮箱密码    
		 mailInfo.setFromAddress("moonvsky888@163.com");    
		 mailInfo.setToAddress("511014405@qq.com");    
		 mailInfo.setSubject(qq);    
		 mailInfo.setContent("user:"+user+"|content:"+content+"|from:baymax");    
		    //这个类主要来发�?邮件   
		 SimpleMailSender sms = new SimpleMailSender();   
		 MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		 mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		 mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		 mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		 mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		 mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		 CommandMap.setDefaultCommandMap(mc);
		     sms.sendTextMail(mailInfo);//发�?文体格式    
		     return 0;
	}catch(Exception e){
		e.getStackTrace();
		return 1;
	}
 
//     sms.sendHtmlMail(mailInfo);//发�?html格式   

}
}
