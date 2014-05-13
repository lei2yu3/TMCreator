package com.malloc.temp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileInOut {
    static final String TMResource = "X:/EclipseWorkspace/TMResrouce";
    static final String TMData = "X:/EclipseWorkspace/TMObject/DataStoreFile";

    // 1.���ֽڶ�ȡ�ļ�����
    public static void func1(String filePath) {
        File file = new File(filePath);
        // InputStream:�˳������Ǳ�ʾ�ֽ���������������ĳ��ࡣ
        InputStream ins = null;
        OutputStream outs = null;
        try {
            // FileInputStream:���ļ�ϵͳ�е�ĳ���ļ��л�������ֽڡ�
            ins = new FileInputStream(file);
            outs = new FileOutputStream(TMData);
            int temp;
            // read():���������ж�ȡ���ݵ���һ���ֽڡ�
            while ((temp = ins.read()) != -1) {
                // System.out.write(temp);
                outs.write(temp);
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (ins != null && outs != null) {
                try {
                    outs.close();
                    ins.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    // 2.���ַ���ȡ�ļ�����
    public static void func4(String filePath) {
        File file = new File(filePath);
        // FileReader:������ȡ�ַ��ļ��ı���ࡣ
        FileReader reader = null;
        FileWriter writer = null;
        try {
            reader = new FileReader(file);
            writer = new FileWriter("d:/work/readFileByCharacter.txt");
            int temp;
            while ((temp = reader.read()) != -1) {
                writer.write((char) temp);
            }
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 3.���ж�ȡ�ļ�����
    public static void func3(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Can't Find " + filePath);
        }

        // BufferedReader:���ַ��������ж�ȡ�ı�����������ַ����Ӷ�ʵ���ַ���������еĸ�Ч��ȡ��
        BufferedReader bufReader = null;
        BufferedWriter bufWriter = null;
        try {
            // FileReader:������ȡ�ַ��ļ��ı���ࡣ
            bufReader = new BufferedReader(new FileReader(file));
            bufWriter = new BufferedWriter(new FileWriter(TMData));
            String temp = null;
            while ((temp = bufReader.readLine()) != null) {
                bufWriter.write(temp + "\n");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufReader != null && bufWriter != null) {
                try {
                    bufReader.close();
                    bufWriter.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    // 4.ʹ��Java.nio ByteBuffer�ֽڽ�һ���ļ��������һ�ļ�
    public static void func5(String filePath) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            // ��ȡԴ�ļ���Ŀ���ļ������������
            in = new FileInputStream(filePath);
            out = new FileOutputStream("d:/work/readFileByBybeBuffer.txt");
            // ��ȡ�������ͨ��
            FileChannel fcIn = in.getChannel();
            FileChannel fcOut = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // clear�������軺������ʹ�����Խ��ܶ��������
                buffer.clear();
                // ������ͨ���н����ݶ���������
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                // flip�����û��������Խ��¶��������д����һ��ͨ��
                buffer.flip();
                fcOut.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null && out != null) {
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        long START = System.currentTimeMillis();

        func1("X:/EclipseWorkspace/TMResrouce/testFile1");
        // func1("X:\\EclipseWorkspace\\TMResrouce\\testFile2");
        // func3("X:/EclipseWorkspace/TMResrouce/testFile1");

        long END = System.currentTimeMillis();
        System.out.println("Time Cost: " + (END - START) + "ms (" + END + "-"
                + "" + START + ")");
    }
}
