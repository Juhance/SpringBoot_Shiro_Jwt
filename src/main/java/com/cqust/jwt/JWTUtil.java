package com.cqust.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	 // ����ʱ��
    private static final long EXPIRE_TIME = 24 * 60 * 30 * 1000;
	public static final long EXPIRE_DAY = 1;
	public static final long EXPIRE_SECONDS = 1800;
    /**
     * У��token�Ƿ���ȷ
     *
     * @param token    ��Կ
     * @param username ��¼��
     * @param password ����
     * @return
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();

            DecodedJWT jwt = verifier.verify(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ��ȡ��¼��
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * ����ǩ��
     *
     * @param username
     * @param password
     * @return
     */
    public static String sign(String username, String password) throws UnsupportedEncodingException {
        // ָ������ʱ��
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

		Algorithm algorithm = Algorithm.HMAC256(password);

		return JWT.create()
		        .withClaim("username", username)
		        .withExpiresAt(date)
		        .sign(algorithm);
    }

}
