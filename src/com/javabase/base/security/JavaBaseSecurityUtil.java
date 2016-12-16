package com.javabase.base.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.javabase.base.app.LOG4jUtils;

/**
 * 加密算法的 util 工具
 * 
 * @author admin
 *
 */
public class JavaBaseSecurityUtil {

	public static final String KEY_ALGORTHM_RSA = "RSA";
	public static final String KEY_ALGORTHM_DES = "DES";
	
	private static byte[] RSA_PRIVATE_KEYS;
	private static KeyFactory keyFactory;
	private static Key privateKey;
	static {
		try {
			RSA_PRIVATE_KEYS = JavaBasePrivateKeyReader.getRsaPrivateKey();
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(RSA_PRIVATE_KEYS);
			keyFactory = KeyFactory.getInstance(KEY_ALGORTHM_RSA);
			privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			LOG4jUtils.error("加载私钥成功");
		} catch ( NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
			LOG4jUtils.error("加载私钥失败", e);
			RSA_PRIVATE_KEYS = null;
			keyFactory = null;
			privateKey = null;
		}
	}
	
	/**
	 * 用私钥解密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(byte[] data) throws Exception {
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return new String(cipher.doFinal(data));
	}

	/**
	 * 用公钥解密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		// 对私钥解密
		byte[] keyBytes = decryptBASE64(key);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM_RSA);
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密方法
	 * 
	 * @param rawKeyData
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static byte[] desEncrypt(byte rawKeyData[], String str) throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORTHM_DES);
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(KEY_ALGORTHM_DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		// 现在，获取数据并加密
		return cipher.doFinal(str.getBytes());
	}

	/**
	 * 解密方法
	 * 
	 * @param rawKeyData
	 * @param encryptedData
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 */
	public static String desDecrypt(String securityKey, byte[] data)
			throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(securityKey.getBytes());
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORTHM_DES);
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(KEY_ALGORTHM_DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		// 正式执行解密操作
		byte decryptedData[] = cipher.doFinal(data);
		return new String(decryptedData);
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) {
		return Base64Utils.decodeFromString(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) {
		return Base64Utils.encodeToString(key);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		try {
			byte[] desEnString  = desEncrypt("12345678".getBytes(),"abcdef");
			String deStr = desDecrypt("12345678", desEnString);
			System.out.println(deStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
