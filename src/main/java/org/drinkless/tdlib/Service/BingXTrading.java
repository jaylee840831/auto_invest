package org.drinkless.tdlib.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.Logger;
import org.drinkless.tdlib.Dao.BingXDao;
import org.drinkless.tdlib.Dto.TDlibDto;
import org.json.JSONArray;
import org.json.JSONObject;

import net.bytebuddy.asm.Advice.This;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class BingXTrading {
	private static final String url = "https://api-swap-rest.bingbon.pro";
	private String apiKey = "";
	private String secretKey="";
	private final ReentrantLock lock = new ReentrantLock();
	
//    public BingXTrading(BingXDao userInfo) {
//		super();
//    	this.apiKey=userInfo.getApiKey();
//    	this.secretKey=userInfo.getSecretKey();
//	}
    
    public BingXTrading(String apiKey,String secretKey) {
		super();
		this.apiKey=apiKey;
		this.secretKey=secretKey;
	}

    String generateHmac256(String message) {
    	try {
    		byte[] bytes = hmac("HmacSHA256", secretKey.getBytes(), message.getBytes());

            // base64 encode
            Encoder codec = Base64.getEncoder();
            String b64Str = codec.encodeToString(bytes);

            // url encode
            String signature = URLEncoder.encode(b64Str,"utf-8");
            return signature;

    	} catch (Exception e) {
        	System.out.println("generateHmac256 expection: {}"+e);
        }
    	return "";
    }

    byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    String getMessageToDigest(String method, String path, TreeMap<String, String> parameters) {
    	Boolean first = true;
        String valueToDigest = method + path;
        for (Map.Entry<String, String> e : parameters.entrySet()) {
        	if (!first) {
        		valueToDigest += "&";
        	}
        	first = false;
        	valueToDigest += e.getKey() + "=" + e.getValue();
        }
        return valueToDigest;
    }

    String getRequestUrl(String path, TreeMap<String, String> parameters) {
    	String urlStr = url + path + "?";

    	Boolean first = true;
        for (Map.Entry<String, String> e : parameters.entrySet()) {
        	if (!first) {
        		urlStr += "&";
        	}
        	first = false;
        	urlStr += e.getKey() + "=" + e.getValue();
        }

    	return urlStr;
    }

    String post(String requestUrl) {
    	
    	String result="";
    	
    	try {
        	URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)conn;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
//            http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            String line = "";
            BufferedReader in = new BufferedReader(
            		new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null) {
            	result += line;
            }

        } catch (Exception e) {
        	System.out.println("post exception: {}"+e.toString());
        }
    	
    	return result;
    }

    String getBalance() {
    	String method = "POST";
    	String path = "/api/v1/user/getBalance";
    	String currency = "USDT";
        String timestamp = ""+new Timestamp(System.currentTimeMillis()).getTime();

        TreeMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("timestamp", timestamp);
        parameters.put("apiKey", this.apiKey);
        parameters.put("currency", currency);

        String valueToDigest = getMessageToDigest(method, path, parameters);
        String messageDigest = generateHmac256(valueToDigest);
        parameters.put("sign", messageDigest);
        String requestUrl = getRequestUrl(path, parameters);
        
        return post(requestUrl);
    }

    void getPositions(String symbol) {
    	String method = "POST";
    	String path = "/api/v1/user/getPositions";
        String timestamp = ""+new Timestamp(System.currentTimeMillis()).getTime();

        TreeMap<String, String> parameters = new TreeMap<String, String>();
        parameters.put("timestamp", timestamp);
        parameters.put("apiKey", this.apiKey);
        parameters.put("symbol", symbol);

        String valueToDigest = getMessageToDigest(method, path, parameters);
        String messageDigest = generateHmac256(valueToDigest);
        parameters.put("sign", messageDigest);
        String requestUrl = getRequestUrl(path, parameters);

        post(requestUrl);
    }

    boolean placeOrder(String symbol, String side, String price, String volume,
    		String tradeType, String action, String threadName,TDlibDto.newMessageRes messageInfo) {
    	
    	lock.lock();
    	
    	try{
//    		System.out.println(threadName);
//    		System.out.println(messageInfo.toString());
//	    	String method = "POST";
//	    	String path = "/api/v1/user/trade";
//	    	String timestamp = ""+new Timestamp(System.currentTimeMillis()).getTime();
//	
//	    	TreeMap<String, String> parameters = new TreeMap<String, String>();
//	    	parameters.put("symbol", symbol);
//	    	parameters.put("apiKey", this.apiKey);
//	    	parameters.put("side", side);
//	    	parameters.put("entrustPrice", price);
//	    	parameters.put("entrustVolume", volume);
//	    	parameters.put("tradeType", tradeType);
//	    	parameters.put("action", action);
//	    	parameters.put("timestamp", timestamp);
//	
//	    	String valueToDigest = getMessageToDigest(method, path, parameters);
//	        String messageDigest = generateHmac256(valueToDigest);
//	        parameters.put("sign", messageDigest);
//	        String requestUrl = getRequestUrl(path, parameters);
//	
//	        String result= post(requestUrl);
//	        JSONObject result2 = new JSONObject(result);
//	        JSONArray key = result2.names();
//	        
//	        for (int i = 0; i < key.length(); ++i) {
//	           String keys = key.getString(i);
//	           
//	           if(keys.equals("code")) {
//	        	   int value = result2.getInt(keys);
//	        	   if(value!=0) {
//	        		   return false;
//	        	   }
//	           }
//	        }
    	}
    	finally {
    		lock.unlock();
    	}

        return true;
    }

//    public static void main(String[] args) {
//    	TradeDemo h = new TradeDemo();
//
//    	System.out.println("getBalance:");
//    	h.getBalance();
//
//    	System.out.println("placeOpenOrder:");
//    	h.placeOrder("BTC-USDT", "Bid", "0", "0.0004", "Market", "Open");
//
//    	System.out.println("getPositions:");
//    	h.getPositions("BTC-USDT");
//
//    	System.out.println("placeCloseOrder:");
//    	h.placeOrder("BTC-USDT", "Ask", "0", "0.0004", "Market", "Close");
//    }
}
