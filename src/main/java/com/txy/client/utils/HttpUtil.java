package com.txy.client.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import javax.servlet.ServletOutputStream;


@Slf4j
public class HttpUtil
{

   //上传文件
	public static String filePost(String url,InputStream in,String fileName ){
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		String a= UUID.randomUUID().toString();
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

		try{
			PrivateKey privateKey = RSA.string2PrivateKey(privateKeyStr);
			String b=RSA.sign(a,privateKey);
			httpClient=HttpClients.createDefault();
			HttpPost httpPost=new HttpPost(url);
			httpPost.addHeader("SID",a);
			httpPost.addHeader("Signature",b);
			MultipartEntityBuilder builder=MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.RFC6532);
			builder.addBinaryBody(fileName,in,ContentType.DEFAULT_BINARY,fileName);
			HttpEntity Entity=builder.build();
			httpPost.setEntity(Entity);
			response=httpClient.execute(httpPost);
			HttpEntity resEntity=response.getEntity();
			String result=EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
			System.out.println(result);
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (response != null) {
					response.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				if(httpClient!=null){
					httpClient.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return "";

	}

	//下载文件
	public static void  fileGet(String url,OutputStream out,String password) throws IOException{
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		try {
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
			String b=RSA.sign(a,privateKey);
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("SID",a);
			httpGet.addHeader("Signature",b);
			response = httpClient.execute(httpGet);
			HttpEntity httpEntity=response.getEntity();
			InputStream in=httpEntity.getContent();

			System.out.println("password为"+password);
			//公钥加密AES秘钥后的内容(Base64编码)，进行Base64解码
			byte[] publicEncrypt2 = RSA.base642Byte(password);
			//用私钥解密,得到aesKey
			byte[] aesKeyStrBytes = RSA.privateDecrypt(publicEncrypt2, privateKey);
			//解密后的aesKey
			String aesKeyStr2 = new String(aesKeyStrBytes);
			SecretKey aesKey1 = AES.loadKeyAES(aesKeyStr2);
			AES.decryptedFile(in,out,aesKey1);
			/*int len = 0;
			byte[] buffer = new byte[1024];
			while((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();*/

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (response != null) {
					response.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				if(httpClient!=null){
					httpClient.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}

	}

	//获取元数据
	public static String  messageGet(String url) throws IOException{
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		try {
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
			String b=RSA.sign(a,privateKey);
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("SID",a);
			httpGet.addHeader("Signature",b);
			response = httpClient.execute(httpGet);
			HttpEntity httpEntity=response.getEntity();
			String result=EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (response != null) {
					response.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				if(httpClient!=null){
					httpClient.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;

	}

	//获取最新10条数据
	public static String  listGet(String url) throws IOException{
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse response=null;
		try {
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
			String b=RSA.sign(a,privateKey);
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("SID",a);
			httpGet.addHeader("Signature",b);
			response = httpClient.execute(httpGet);
			HttpEntity httpEntity=response.getEntity();
			String result=EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (response != null) {
					response.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			try{
				if(httpClient!=null){
					httpClient.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;

	}

}
