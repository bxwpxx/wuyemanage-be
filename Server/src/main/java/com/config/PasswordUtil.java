package com.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 密码安全工具类（使用PBKDF2WithHmacSHA1算法）
 */
public class PasswordUtil {
    // 算法参数
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256; // 位
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 生成加盐哈希密码
     * @param password 明文密码
     * @return 格式：算法$迭代次数$盐$哈希值
     */
    public static String hashPassword(String password) {
        try {
            // 生成随机盐（建议每个用户不同）
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // 计算哈希
            KeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // 组合存储信息
            return String.format(
                    "%s$%d$%s$%s",
                    ALGORITHM,
                    ITERATIONS,
                    Base64.getEncoder().encodeToString(salt),
                    Base64.getEncoder().encodeToString(hash)
            );
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码哈希失败", e);
        }
    }

    /**
     * 验证密码
     * @param inputPassword 用户输入的密码
     * @param storedPassword 数据库存储的哈希密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        String[] parts = storedPassword.split("\\$");
        if (parts.length != 4) return false;

        try {
            // 解析存储的参数
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            int iterations = Integer.parseInt(parts[1]);

            // 用相同参数计算输入密码的哈希
            KeySpec spec = new PBEKeySpec(
                    inputPassword.toCharArray(),
                    salt,
                    iterations,
                    KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(parts[0]);
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            // 比较哈希值
            byte[] originalHash = Base64.getDecoder().decode(parts[3]);
            return Arrays.equals(testHash, originalHash);
        } catch (Exception e) {
            return false;
        }
    }
    public static void main(String[] args) {
        System.out.println(PasswordUtil.hashPassword("123456"));
        System.out.println(PasswordUtil.verifyPassword("123456",PasswordUtil.hashPassword("123456")));
    }
}
