package com.txy.client;

import com.google.gson.JsonObject;
import com.txy.client.utils.AES;
import com.txy.client.utils.HttpUtil;

import com.txy.client.utils.RSA;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientApplicationTests {

	@Test
	public void test() {

		File file=new File("D:\\upload\\声波.jpg");

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String time=df.format(new Date());
		System.out.println(time);

	}

	@Test
	public void RSA() throws Exception{
		KeyPair keyPair = RSA.getKeyPair();
		String publicKeyStr = RSA.getPublicKey(keyPair);
		String privateKeyStr = RSA.getPrivateKey(keyPair);
		System.out.println("RSA公钥Base64编码:" + publicKeyStr);
		System.out.println("RSA私钥Base64编码:" + privateKeyStr);

	}

	@Test
	public void AESRSA() throws Exception{
		String publicKeyStr="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwATptj0176s6LwtOP/u4EJmUY6FDKLwd\n" +
				"qowqMyea2dazgoy9QhuWyPnVn5xTIYPbC7ykyGEj5TQ+5GkM/poST93A7oafdZwTZ3S8DAg3DqGO\n" +
				"kk4b1hyFAR6iP1lqlHFmpg7esW3OADDTJcsc/Unu/4MD8PYx+hxevFzqHg1Fv6BOKgEQZ4WS2I8/\n" +
				"250on4CaAZvISXQ1ySTM0jQjH+tpdnboCIZhDhzZAa5JOA3XjBscyX7a8fNh3Vfk5t6HlL9zXN8R\n" +
				"NQ6cqdSMacxHILW99e6WYmvVDRz84HocMuzEWVrxD6DvEDFxiy8AqcESt84TMy7oo9BJPxalMwH4\n" +
				"Fe6fawIDAQAB";
		String privateKeyStr="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDABOm2PTXvqzovC04/+7gQmZRj\n" +
				"oUMovB2qjCozJ5rZ1rOCjL1CG5bI+dWfnFMhg9sLvKTIYSPlND7kaQz+mhJP3cDuhp91nBNndLwM\n" +
				"CDcOoY6SThvWHIUBHqI/WWqUcWamDt6xbc4AMNMlyxz9Se7/gwPw9jH6HF68XOoeDUW/oE4qARBn\n" +
				"hZLYjz/bnSifgJoBm8hJdDXJJMzSNCMf62l2dugIhmEOHNkBrkk4DdeMGxzJftrx82HdV+Tm3oeU\n" +
				"v3Nc3xE1Dpyp1IxpzEcgtb317pZia9UNHPzgehwy7MRZWvEPoO8QMXGLLwCpwRK3zhMzLuij0Ek/\n" +
				"FqUzAfgV7p9rAgMBAAECggEAJmTVFktG7ZUfHSj1jHWYaDlNIl6sPwjeJavBBFl27PexDPl3HjDG\n" +
				"BYlu3Ws0dA8ZTU9641YJB1ta9/DJWF8WKkH8wbTJTYrXlmgQ8Zby60oCZ96WUkmBjqfBaUlHtka2\n" +
				"b52piXCbMA6TKYtjUIHn988KirOvaoMhrigl57xfpeDe+AKhQ6FbtSn+hrZUiu5gu1YTtU2LWU7V\n" +
				"ixvJ/nZlV+Vmdm0AVnFC9JztXHuFw4YA2ZndQQ8SxgbStiOL5HzWvFmO3ZVmnzCam/gRBZdicS0V\n" +
				"R0mxBnJ3UDr5SaJIekBCZBb83wljDLNGb2ulyEvCv64MaM/7BaaJes1n7VuNgQKBgQDpc9EWsC13\n" +
				"GYZ81jeqI7BSsKwM64JLgZ5El+inpM6jq/yQ2TW1CGmT3S2yCify6vNjHkOedlY7H1NHLzqIcrmF\n" +
				"PVra0PZEdTx9iPtYV59UMLPzWTZUM6rck8Ke076sVYhpJoPxE4W8ubjDNEF+nntBK/Zk4dXzwXhz\n" +
				"BLVCEo/S7QKBgQDSkKWispqzN+POQpEI7ijSiREyPHmPf5cfAgFNVPhwfa/Su+gjq/WqTXO8gS6t\n" +
				"8oH5cTIJodHm4IbKKZvUdy+YnDqJtZgbKdNBlR7SswsA497tB/qTmeyzKGnI7N0YZSzuWcUjsOeu\n" +
				"IpuwHy/FGKsQUekyWpXsCt86mCDloIc4twKBgCpzOekDPjEy/gaDOXoBpVVT9OUgif/K4QmeCds6\n" +
				"F60hX/rC1IZHslMF3AJM4HjGil1Sqn8NC58SkiUWjHE1U1DMGFJDvxQxsT8ugm3M+RbpCD/O0Bc9\n" +
				"kessHy6cWEakAvsD2ugiYaYwq00rmmE41jbClLpBapM5cfqQN5l9IJP9AoGBAK7wsDyRKQg3KhQm\n" +
				"p/sEa3Qnntre9ZvsRHDlqJFIRDSpp9dseLySVMOOH9cAePjaH3RPwGPSRuMLrdDuauJFt4ySV+wr\n" +
				"rFj9EeBN9VLWoBC4QXErDNtuqgJbVDAz4qku/fp1KN2zT3kUlcbo0jZtfUpM03MbYa2/hqFW3RYK\n" +
				"ne3XAoGAR4AIhCX0OCgYSeHOXc5ey5J+OC1sxKRi1IMPc2QYOQa0Chk5vnD5r/3oPKOmMToFHYga\n" +
				"kvtnVr1ojMYel8IJVGXhpZJpjhyGDL+gpiyn5hT+fXkKpwwqbDictFvYUPVfovqza5ObyXfThvcO\n" +
				"IFI/wIrgjNEueG3hzVEKsEE6F30=";

		PublicKey publicKey = RSA.string2PublicKey(publicKeyStr);
		String aesKeyStr = AES.genKeyAES();
		SecretKey aesKey = AES.loadKeyAES(aesKeyStr);
		AES.encryptFile("D:\\upload\\语音.jpg","D:\\upload\\999",aesKey);
		//AES.decryptedFile("D:\\upload\\999","D:\\upload\\语音999.jpg",aesKey);
		byte[] publicEncrypt = RSA.publicEncrypt(aesKeyStr.getBytes(), publicKey);
		//公钥加密AES秘钥后的内容Base64编码
		String publicEncryptStr = RSA.byte2Base64(publicEncrypt);
		System.out.println(publicEncryptStr);

		PrivateKey privateKey = RSA.string2PrivateKey(privateKeyStr);
		//公钥加密AES秘钥后的内容(Base64编码)，进行Base64解码
		byte[] publicEncrypt2 = RSA.base642Byte(publicEncryptStr);
		//用私钥解密,得到aesKey
		byte[] aesKeyStrBytes = RSA.privateDecrypt(publicEncrypt2, privateKey);
		//解密后的aesKey
		String aesKeyStr2 = new String(aesKeyStrBytes);
		SecretKey aesKey1 = AES.loadKeyAES(aesKeyStr2);
		//AES.encryptFile("D:\\upload\\语音.jpg","D:\\upload\\999",aesKey1);
		//AES.decryptedFile("D:\\upload\\999","D:\\upload\\语音999.jpg",aesKey1);




	}
	@Test
	public void whyerror() throws Exception{
		String publicKeyStr="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwATptj0176s6LwtOP/u4EJmUY6FDKLwd\n" +
				"qowqMyea2dazgoy9QhuWyPnVn5xTIYPbC7ykyGEj5TQ+5GkM/poST93A7oafdZwTZ3S8DAg3DqGO\n" +
				"kk4b1hyFAR6iP1lqlHFmpg7esW3OADDTJcsc/Unu/4MD8PYx+hxevFzqHg1Fv6BOKgEQZ4WS2I8/\n" +
				"250on4CaAZvISXQ1ySTM0jQjH+tpdnboCIZhDhzZAa5JOA3XjBscyX7a8fNh3Vfk5t6HlL9zXN8R\n" +
				"NQ6cqdSMacxHILW99e6WYmvVDRz84HocMuzEWVrxD6DvEDFxiy8AqcESt84TMy7oo9BJPxalMwH4\n" +
				"Fe6fawIDAQAB";
		String privateKeyStr="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDABOm2PTXvqzovC04/+7gQmZRj\n" +
				"oUMovB2qjCozJ5rZ1rOCjL1CG5bI+dWfnFMhg9sLvKTIYSPlND7kaQz+mhJP3cDuhp91nBNndLwM\n" +
				"CDcOoY6SThvWHIUBHqI/WWqUcWamDt6xbc4AMNMlyxz9Se7/gwPw9jH6HF68XOoeDUW/oE4qARBn\n" +
				"hZLYjz/bnSifgJoBm8hJdDXJJMzSNCMf62l2dugIhmEOHNkBrkk4DdeMGxzJftrx82HdV+Tm3oeU\n" +
				"v3Nc3xE1Dpyp1IxpzEcgtb317pZia9UNHPzgehwy7MRZWvEPoO8QMXGLLwCpwRK3zhMzLuij0Ek/\n" +
				"FqUzAfgV7p9rAgMBAAECggEAJmTVFktG7ZUfHSj1jHWYaDlNIl6sPwjeJavBBFl27PexDPl3HjDG\n" +
				"BYlu3Ws0dA8ZTU9641YJB1ta9/DJWF8WKkH8wbTJTYrXlmgQ8Zby60oCZ96WUkmBjqfBaUlHtka2\n" +
				"b52piXCbMA6TKYtjUIHn988KirOvaoMhrigl57xfpeDe+AKhQ6FbtSn+hrZUiu5gu1YTtU2LWU7V\n" +
				"ixvJ/nZlV+Vmdm0AVnFC9JztXHuFw4YA2ZndQQ8SxgbStiOL5HzWvFmO3ZVmnzCam/gRBZdicS0V\n" +
				"R0mxBnJ3UDr5SaJIekBCZBb83wljDLNGb2ulyEvCv64MaM/7BaaJes1n7VuNgQKBgQDpc9EWsC13\n" +
				"GYZ81jeqI7BSsKwM64JLgZ5El+inpM6jq/yQ2TW1CGmT3S2yCify6vNjHkOedlY7H1NHLzqIcrmF\n" +
				"PVra0PZEdTx9iPtYV59UMLPzWTZUM6rck8Ke076sVYhpJoPxE4W8ubjDNEF+nntBK/Zk4dXzwXhz\n" +
				"BLVCEo/S7QKBgQDSkKWispqzN+POQpEI7ijSiREyPHmPf5cfAgFNVPhwfa/Su+gjq/WqTXO8gS6t\n" +
				"8oH5cTIJodHm4IbKKZvUdy+YnDqJtZgbKdNBlR7SswsA497tB/qTmeyzKGnI7N0YZSzuWcUjsOeu\n" +
				"IpuwHy/FGKsQUekyWpXsCt86mCDloIc4twKBgCpzOekDPjEy/gaDOXoBpVVT9OUgif/K4QmeCds6\n" +
				"F60hX/rC1IZHslMF3AJM4HjGil1Sqn8NC58SkiUWjHE1U1DMGFJDvxQxsT8ugm3M+RbpCD/O0Bc9\n" +
				"kessHy6cWEakAvsD2ugiYaYwq00rmmE41jbClLpBapM5cfqQN5l9IJP9AoGBAK7wsDyRKQg3KhQm\n" +
				"p/sEa3Qnntre9ZvsRHDlqJFIRDSpp9dseLySVMOOH9cAePjaH3RPwGPSRuMLrdDuauJFt4ySV+wr\n" +
				"rFj9EeBN9VLWoBC4QXErDNtuqgJbVDAz4qku/fp1KN2zT3kUlcbo0jZtfUpM03MbYa2/hqFW3RYK\n" +
				"ne3XAoGAR4AIhCX0OCgYSeHOXc5ey5J+OC1sxKRi1IMPc2QYOQa0Chk5vnD5r/3oPKOmMToFHYga\n" +
				"kvtnVr1ojMYel8IJVGXhpZJpjhyGDL+gpiyn5hT+fXkKpwwqbDictFvYUPVfovqza5ObyXfThvcO\n" +
				"IFI/wIrgjNEueG3hzVEKsEE6F30=";
		PrivateKey privateKey = RSA.string2PrivateKey(privateKeyStr);
		String a= UUID.randomUUID().toString();
		System.out.println("随机字符串为"+a);
		String b=RSA.sign(a,privateKey);
		System.out.println("签名后的数据为"+b);

		PublicKey publicKey = RSA.string2PublicKey(publicKeyStr);
		Boolean t=RSA.verify(a,publicKey,b);
		System.out.println(t);


	}



}
