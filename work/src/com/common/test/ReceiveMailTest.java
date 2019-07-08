package com.common.test;

import com.common.util.email.ReciveMail;
import com.logger.Log4jKit;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Properties;

public class ReceiveMailTest extends Log4jKit {

    private static final String SSl_FACTRY = "java.net.ssl.SSLSocketFactory";

    /**
     * pop3协议连接邮箱
     * @param username 邮箱用户名
     * @param password 邮箱密码
     * @throws Exception
     */
    private static void connectPop3Mail(String username,String password) throws MessagingException, IOException {
        // 连接pop3服务器的主机名、协议、用户名、密码
        String pop3Server = "pop.163.com";
        String protocol = "pop3";
        String user = username;
        String pwd = password;

        // 创建一个有具体连接信息的Properties对象
        Properties props = new Properties();
        //协议
        props.setProperty("mail.store.protocol", protocol);
        //主机名
        props.setProperty("mail.pop3.host", pop3Server);

        // 使用Properties对象获得Session对象
        Session session = Session.getInstance(props);
        session.setDebug(true);

        // 利用Session对象获得Store对象，并连接pop3服务器
        Store store = session.getStore();
        store.connect(pop3Server, user, pwd);

        // 获得邮箱内的邮件夹Folder对象，以"只读"打开
        Folder folder = store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        // 获得邮件夹Folder内的所有邮件Message对象
        Message[] messages = folder.getMessages();
        ReciveMail rm = null;
        for(int i=0;i< messages.length ;i++){
            rm = new ReciveMail((MimeMessage) messages[i]);
            rm.recive(messages[i],i);
        }
        folder.close(false);
        store.close();
    }

    private static void connectImapMail(String username,String password) throws MessagingException, IOException {
        // 连接pop3服务器的主机名、协议、用户名、密码
        String pop3Server = "imap.exmail.qq.com";
        String port = "993";
        String protocol = "imap";

        // 创建一个有具体连接信息的Properties对象
        Properties props = new Properties();
        // 协议
        props.setProperty("mail.imap.host", pop3Server);
        // imap服务器
        props.setProperty("mail.store.protocol", protocol);
        // 端口
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.auth.login.disable", "false");
        // ssl登陆
        props.setProperty("mail.imap.ssl.enable", "true");
//        props.setProperty("mail.imap.auth.login.disable", "true");
//        props.setProperty("mail.imap.socketFactory.class", SSl_FACTRY);
//        props.setProperty("mail.imap.socketFactory.port", port);
        // 创建Session实例对象
        Session session = Session.getInstance(props);
        //调试用true
        session.setDebug(false);
        IMAPStore store = null;
        IMAPFolder folder = null;
        // 创建IMAP协议的Store对象
        store = (IMAPStore) session.getStore(protocol);
        // 连接邮件服务器
        store.connect(username, password);
        info("创建邮件会话完成，打开收件箱开始读取邮件");
        // false代表未读，true代表已读
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        // 获得收件箱
        folder = (IMAPFolder) store.getFolder("INBOX");
        // 打开收件箱，设置为只读状态
        folder.open(Folder.READ_ONLY);

        // 获得邮件夹Folder内的所有邮件Message对象
        Message[] messages = folder.getMessages();
        ReciveMail rm = null;
        /*for(int i=0;i< messages.length ;i++){
            rm = new ReciveMail((MimeMessage) messages[i]);
            rm.recive(messages[i],i);
        }*/
        rm = new ReciveMail((MimeMessage) messages[1]);
        rm.recive(messages[1],1);
        folder.close(false);
        store.close();
    }

    public static void main(String[] args) throws Exception {
        connectImapMail("quanzhengzheng@zealinfo.net","QZ249478abc");
    }
}
