package com.javabase.base.mail.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.javabase.base.util.PropertiesUtils;
import com.javabase.base.util.StringDefaultValue;

/**
 * bruce 发送邮件.
 */
public class SendMailUtil {

	/**
	 * test.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {

			String mailHost = "mail.nonobank.com";
			String mailPort = "25";
			String from = "suping@maizijf.com";
			String user = "suping";
			String password = "com.Xaccp.521";
			String auth = "true";
			String protocol = "smtp";
			String[] toList = { "sunping521@126.com", "chenlei@maizijf.com" };
			String[] ccList = { "1577620678@qq.com", "chenlei@maizijf.com" };
			String title = "test";
			String content = "this is a test email";
			String[] files = null;

			/*
			 * email.setMailHost("mail.nonobank.com"); email.setMailPort("25");
			 * email.setFrom("suping@maizijf.com"); email.setUser("suping");
			 * email.setPassword("com.Xaccp.521"); email.setAuth("true");
			 * email.setProtocol("smtp"); email.setToList("sunping521@126.com");
			 * email.setCcList(null); email.setSubject("新添加应用");
			 * email.setContent(
			 * "<meta http-equiv=Content-Type content=text/html;charset=UTF-8>"
			 * + "苏将军您好：<br><br>请你转告吴秀,我苏平很喜欢她,并且想和她白首到老,相伴一生");
			 * email.setFiles(null);
			 */
			EmailInfo email = SendMailUtil.initEmailInfo(mailHost, mailPort, from, user, password, auth, protocol,
					toList, ccList, title, content, files);
			SendMailUtil.sendMails(email);
			System.out.println("发送成功了...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final Logger logger = Logger.getLogger(SendMailUtil.class);
	private static SendMailUtil instance = null;

	private SendMailUtil() {
	}

	public static SendMailUtil getInstance() {
		if (instance == null) {
			instance = new SendMailUtil();
		}
		return instance;
	}

	public static EmailInfo initEmailInfo(String mailHost, String mailPort, String from, String user, String password,
			String auth, String protocol, String[] toList, String[] ccList, String title, String content,
			String[] files) {
		EmailInfo email = new EmailInfo();
		email.setMailHost(mailHost);
		email.setMailPort(mailPort);
		email.setFrom(from);
		email.setUser(user);
		email.setPassword(password);
		email.setAuth(auth);
		email.setProtocol(protocol);
		email.setToList(toList);
		email.setCcList(ccList);
		email.setSubject(title);
		email.setContent("<meta http-equiv=Content-Type content=text/html;charset=UTF-8>" + content);
		email.setFiles(files);
		return email;
	}

	/**
	 * send messages.
	 * 
	 * @param email
	 */
	public static void sendMails(EmailInfo email) {
		Message msg = null;
		String message = "";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", email.getAuth());
			props.put("mail.transport.protocol", email.getProtocol() == null ? "" : email.getProtocol());
			props.put("mail.smtp.host", email.getMailHost());
			props.put("mail.smtp.port", email.getMailPort() == null ? "25" : email.getMailPort());
			// 建立会话
			Session session = Session.getInstance(props);
			msg = new MimeMessage(session); // 建立信息
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			msg.setFrom(new InternetAddress(email.getFrom())); // 发件人

			// 发送,
			if (email.getToList() != null && email.getToList().length > 0) {
				InternetAddress[] iaToList = new InternetAddress[email.getToList().length];
				for (int i = 0; i < email.getToList().length; i++) {
					iaToList[i] = new InternetAddress(email.getToList()[i]);
				}
				msg.setRecipients(Message.RecipientType.TO, iaToList);
			}
			// 抄送
			if (email.getCcList() != null && email.getCcList().length > 0) {
				InternetAddress[] iaToListcs = new InternetAddress[email.getCcList().length];
				for (int i = 0; i < email.getCcList().length; i++) {
					iaToListcs[i] = new InternetAddress(email.getCcList()[i]);
				}
				msg.setRecipients(Message.RecipientType.CC, iaToListcs);
			}

			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject(email.getSubject()); // 主题
			msg.setText(email.getContent()); // 内容

			// 显示以html格式的文本内容
			messageBodyPart.setContent(email.getContent(), "text/html;charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			// 附件
			if (email.getFiles() != null) {
				for (int i = 0; i < email.getFiles().length; i++) {
					addTach(email.getFiles()[i], multipart);
				}
			}
			msg.setContent(multipart);
			// 邮件服务器进行验证
			Transport tran = session.getTransport("smtp");
			tran.connect(email.getMailHost(), email.getUser(), email.getPassword());
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			message = "邮件已经发送成功,邮件内容:" + msg.toString();
			logger.info(message);
			System.out.println(message);
		} catch (Exception e) {
			message = "邮件已经发送失败,邮件内容:" + msg.toString() + ",error:" + e;
			logger.error(message);
			System.out.println(message);
		}
	}

	// 添加一个附件
	private static void addTach(String file, Multipart multipart)
			throws MessagingException, UnsupportedEncodingException {
		MimeBodyPart mailArchieve = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(file);
		mailArchieve.setDataHandler(new DataHandler(fds));
		mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
		multipart.addBodyPart(mailArchieve);
	}

	/**
	 * 获取邮件发送列表
	 * 
	 * @return
	 */
	public static String getMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}
}
