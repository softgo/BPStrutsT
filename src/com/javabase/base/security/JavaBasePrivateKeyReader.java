package com.javabase.base.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取加密的配置信息.
 * 
 * @author admin
 *
 */
public class JavaBasePrivateKeyReader {

	public static byte[] getRsaPrivateKey() throws IOException {
		//InputStream in = PrivateKeyReader.class.getResourceAsStream("/csyy_java_private_key.pem");
		//InputStream in = ClassLoader.getSystemResourceAsStream("csyy_java_private_key.pem");
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/configPros/java_private_key.pem");
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] tmpbuf = new byte[1024];
		int count = 0;
		while ((count = in.read(tmpbuf)) != -1) {
			bout.write(tmpbuf, 0, count);
			tmpbuf = new byte[1024];
		}
		in.close();
		return bout.toByteArray();
	}

}
