package com.gdiot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author ZhouHR
 */
public class FileUtils {

    /**
     * 文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean ifFileExists(File file) {
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件长度
     *
     * @param file
     */
    public static long getFileSize1(File file) {
        if (file.exists() && file.isFile()) {
            String fileName = file.getName();
            System.out.println("文件" + fileName + "的大小是：" + file.length());
            return file.length();
        }
        return -1;
    }

    /**
     * 根据java.io.*的流获取文件大小
     *
     * @param file
     */
    public static int getFileSize2(File file) {
        FileInputStream fis = null;
        try {
            if (file.exists() && file.isFile()) {
                String fileName = file.getName();
                fis = new FileInputStream(file);
                System.out.println("文件" + fileName + "的大小是：" + fis.available() + "\n");
                return fis.available();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}
