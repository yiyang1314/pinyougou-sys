package com.pingyougou.utilscommons;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * md5加密工具类
 * 
 * @author Administrator
 * 
 */
//public class MD5Util {
//
//	/**
//	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
//	 */
//	public static String createSign(SortedMap<String, String> packageParams,
//			String appid, String key) {
//		StringBuffer sb = new StringBuffer();
//		Set es = packageParams.entrySet();
//		Iterator it = es.iterator();
//		while (it.hasNext()) {
//			Map.Entry entry = (Map.Entry) it.next();
//			String k = (String) entry.getKey();
//			String v = (String) entry.getValue();
//			if (null != v && !"".equals(v) && !"sign".equals(k)
//					&& !"key".equals(k)) {
//				sb.append(k + "=" + v + "&");
//			}
//		}
//		sb.append("key=" + key);
//		String sign = MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//		return sign;
//	}
//
//	private static String byteArrayToHexString(byte b[]) {
//		StringBuffer resultSb = new StringBuffer();
//		for (int i = 0; i < b.length; i++)
//			resultSb.append(byteToHexString(b[i]));
//
//		return resultSb.toString();
//	}
//
//	private static String byteToHexString(byte b) {
//		int n = b;
//		if (n < 0)
//			n += 256;
//		int d1 = n / 16;
//		int d2 = n % 16;
//		return hexDigits[d1] + hexDigits[d2];
//	}
//
//	public static String MD5Encode(String origin, String charsetname) {
//		String resultString = null;
//		try {
//			resultString = new String(origin);
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			if (charsetname == null || "".equals(charsetname))
//				resultString = byteArrayToHexString(md.digest(resultString
//						.getBytes()));
//			else
//				resultString = byteArrayToHexString(md.digest(resultString
//						.getBytes(charsetname)));
//		} catch (Exception exception) {
//		}
//		return resultString;
//	}
//
//	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
//			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
//	
//	
//	
//	public static void  main(String h[]) {
//		String n=MD5Encode("123456","utf-8");
//		System.out.println("n: "+n);
//		 n=MD5Encode(n,"utf-8");
//		System.out.println("n: "+n);
//		
//		
////		Long b=new Long(256);
////		int a=256;
////		System.out.println(b.intValue());
////		System.out.println(a==b.intValue());
////		
////		
////		IdWorker id=new IdWorker();
////		long sequenceId=id.nextId();
////		System.out.println("sequenceId: "+sequenceId);
////		System.out.println("------------------0---0-----------------------------");
////		IdWorker id2=new IdWorker(0,0);
////		for(int i=0;i<5;i++) {
////			long sequenceId2=id2.nextId();
////			System.out.println("sequenceId2: "+sequenceId2);
////		}
////		System.out.println("------------------1---0-----------------------------");
////		id2=new IdWorker(1,0);
////		for(int i=0;i<5;i++) {
////			long sequenceId2=id2.nextId();
////			System.out.println("sequenceId2: "+sequenceId2);
////		}
////		System.out.println("------------------1---1-----------------------------");
////		id2=new IdWorker(1,1);
////		for(int i=0;i<5;i++) {
////			long sequenceId2=id2.nextId();
////			System.out.println("sequenceId2: "+sequenceId2);
////		}
////		System.out.println("------------------0---1-----------------------------");
////		id2=new IdWorker(0,1);
////		for(int i=0;i<5;i++) {
////			long sequenceId2=id2.nextId();
////			System.out.println("sequenceId2: "+sequenceId2);
////		}
//	}
//
//}
import java.security.MessageDigest;
public class MD5Utils {
	
  
    /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray(); 
        System.out.println("charArray: "+charArray);
        byte[] byteArray = new byte[charArray.length];  

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
            System.out.print(byteArray[i]+"\t");
        }
        System.out.println("\nbyteArray: "+byteArray);
        byte[] md5Bytes = md5.digest(byteArray); 
        System.out.println("md5Bytes: "+md5Bytes.toString());
        StringBuffer hexValue = new StringBuffer();
        System.out.println(md5Bytes.length);
        for (int i = 0; i < md5Bytes.length; i++){
        	System.out.print(md5Bytes[i]+" \t");
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val)); 
            System.out.println("hexValue: "+hexValue);
        } 
        return hexValue.toString();  

    }  

    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  

        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  

    }  

    public static String[] DecodeMD5(String s) {
		String stc="";
		for(int i=0;i<s.length();i++) {
			if(i%2==0) {
				stc=",";
			}
			stc+=s.charAt(i);
		}
    	System.out.print(stc.toString());
    	String []t=stc.split(",");
//    	for(String h:t) {
//    		Integer p=new Integer(h.charAt(0));
//    		
//    	}
//    	
    	return t;
    	
    }
    
    
    
    
    // 测试主函数  
    public static void main(String args[]) {  
        String s = new String("123456");  
        System.out.println("原始：" + s);  
        System.out.println("MD5后：" + string2MD5(s)); 
        System.out.println("ji的：" + DecodeMD5(string2MD5(s)));
        
        System.out.println("加密的：" + convertMD5(s));  
        System.out.println("解密的：" + convertMD5(convertMD5(s))); 
        System.out.println("原始：" + (char)49+(char)50+(char)51+(char)52); 

    }  

}
