package com.thoughtworks.io;

import java.io.*;

public class FileUtil {

    /**
     * 完成复制文件夹方法:
     * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
     * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
     * <p>
     * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
     */
    public static void copyDirectory(File from, File to) throws IOException {
        File[] fromDir = from.listFiles();
        boolean isExist = true;
        if (!to.isDirectory()) {
            isExist = to.mkdir();
        }
        if (isExist && fromDir != null) {
            File[] toDir = to.listFiles();
            if (toDir != null) {
                for (File i: toDir) {
                    i.delete();
                }
            }
            for (File i: fromDir) {
                String newFile = File.separator + i.getName();
                if (i.isFile()) {
                    try (InputStream input = new FileInputStream(i.getPath());
                         OutputStream output = new FileOutputStream(to.getPath() + newFile)) {
                        byte[] buffer = new byte[1000];
                        int len;
                        while ((len = input.read(buffer)) != -1) {
                            output.write(buffer, 0, len);
                            output.flush();
                        }
                    }
                } else {
                    copyDirectory(i, new File(to.getPath() + newFile));
                }
            }
        }
    }
}
