package com.project.xiaodong.fflibrary.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密工具
 * @author Tommy
 *
 */
public class SecurityEncoderUtil {
	    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	    private final String encodingAlgorithm;

	    private String characterEncoding;

	    private SecurityEncoderUtil(final String encodingAlgorithm) {
	        this.encodingAlgorithm = encodingAlgorithm;
	    }
	    
	    /**
	     * 返回工具实例
	     * @param encodingAlgorithm  加密类型 (MD5\..)
	     * @return
	     */
	    public static SecurityEncoderUtil getInstance(final String encodingAlgorithm){
	    	return new SecurityEncoderUtil(encodingAlgorithm);
	    }
	    
	    /**
	     * 加密码方法
	     * @param password
	     * @return
	     */
	    public String encode(final String password) {
	        if (password == null) {
	            return null;
	        }

	        try {
	            MessageDigest messageDigest = MessageDigest
	                .getInstance(this.encodingAlgorithm);

	            if (this.characterEncoding!=null) {
	                messageDigest.update(password.getBytes(this.characterEncoding));
	            } else {
	                messageDigest.update(password.getBytes());
	            }


	            final byte[] digest = messageDigest.digest();

	            return getFormattedText(digest);
	        } catch (final NoSuchAlgorithmException e) {
	            throw new SecurityException(e);
	        } catch (final UnsupportedEncodingException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    /**
	     * Takes the raw bytes from the digest and formats them correct.
	     * 
	     * @param bytes the raw bytes from the digest.
	     * @return the formatted bytes.
	     */
	    private String getFormattedText(byte[] bytes) {
	        final StringBuilder buf = new StringBuilder(bytes.length * 2);

	        for (int j = 0; j < bytes.length; j++) {
	            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
	            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
	        }
	        return buf.toString();
	    }
	    
	    /**
	     * 设置加密字符集
	     * @param characterEncoding
	     */
	    public final void setCharacterEncoding(final String characterEncoding) {
	        this.characterEncoding = characterEncoding;
	    }
	    
	    /**
	     * 专用加密用户密码
	     * @param password
	     * @return
	     */
	    public String encodePassword(String password){
	    	String result = encode(password);
//	    	result = encode(result+userName);
	    	return result;
	    }
	    
//	    public static void main(String[] args) {
//	    	SecurityEncoderUtil securityEncoderUtils = SecurityEncoderUtil.getInstance("MD5");
//			String enCodedPasswd = securityEncoderUtils.encodePassword("admin");
//			//System.out.println(enCodedPasswd);
//		}
}
