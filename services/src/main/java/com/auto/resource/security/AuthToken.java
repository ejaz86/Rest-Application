package com.auto.resource.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AuthToken {
	static public String computeSignature(String baseString, String keyString)
			throws GeneralSecurityException, UnsupportedEncodingException {
		SecretKey secretKey = null;
		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(secretKey);
		byte[] text = baseString.getBytes();
		byte[] rawHmac = mac.doFinal(text);
		return Hex.encodeHexString(rawHmac);
	}

}
