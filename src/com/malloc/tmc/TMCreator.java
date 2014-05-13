package com.malloc.tmc;

import java.io.File;
import java.util.ArrayList;

public class TMCreator {

    /**
     * All the resource Video��Ƶ��"rmvb", "avi", "mp4", "flv", "wmv"
     * Audio��Ƶ��"mp3", "wav", "ape", "ogg" DiagramͼƬ��"jpg", "bmp", "png", "git"
     * Book�鼮��"txt", "pdf", "doc", "ppt" Code���룬"cpp", "h", "java", "cs", "py",
     * "xml" Other������"exe", "plist", "bat"
     */
    static final String Video[] = { "rmvb", "avi", "mp4", "flv", "wmv" };
    static final String Audio[] = { "mp3", "wav", "ape", "ogg" };
    static final String Diagram[] = { "jpg", "bmp", "png", "git" };
    static final String Book[] = { "txt", "pdf", "doc", "ppt" };
    static final String Code[] = { "cpp", "h", "java", "cs", "py", "xml" };
    static final String Other[] = { "exe", "plist", "bat" };

    // path of TMResource
    static final String TMResource = "X:/EclipseWorkspace/TMResrouce";
    static final String TMObject = "";
    // static final String TMResource = "X:\\EclipseWorkspace\\TMResrouce";

    File dir = new File(TMResource);

    // �洢
    static ArrayList<ResourceType> RTArray = new ArrayList<ResourceType>();

    // �Եݹ�ķ�ʽ����Ŀ¼������Ŀ¼�������ļ�
    public static void directoryTraverseByRecursion(String sPath) {

        File resourceDir = new File(sPath);
        // isDirectory�жϴ�·������ʾ���ļ��Ƿ���һ���ļ��У�isFile�жϴ�·������ʾ���ļ��Ƿ���һ����׼�ļ�
        if (!resourceDir.isDirectory()) {
            System.out.println(sPath + "is not a directory!");
            return;
        }

        // listFiles����һ��File�������飬ÿ�����鱣���ӦĿ¼�µ�ÿ���ļ���Ŀ¼
        File[] arrayFile = resourceDir.listFiles();

        for (int i = 0; i < arrayFile.length; i++) {
            if (arrayFile[i].isDirectory()) {
                // getAbsolutePath���ش�·�����Ĺ淶·�����ַ�����getPath��·����ת��Ϊһ��·�����ַ���
                directoryTraverseByRecursion(arrayFile[i].getAbsolutePath());

                // getName�����ɴ�·������ʾ���ļ���Ŀ¼������
                System.out.println("-" + arrayFile[i].getName() + "\n");

            } else {
                // ���ļ���ResourceType���󱣴浽ArrayList
                ResourceType rt = new ResourceType(arrayFile[i].getName());
                RTArray.add(rt);

                System.out.println("---" + arrayFile[i].getName());
            }
        }
    }

    // ����ȡ������Դ��Ϣ�洢��ָ���ļ�
    // public static void resourceAppend(String filePath, String appendContent)
    // {
    // try {
    // // ��һ��д�ļ�����FileWriter���캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
    // FileWriter fw = new FileWriter(filePath, true);
    // fw.write(appendContent);
    // fw.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // main for test
    public static void main(String[] args) {
        // currentTimeMillis���ص�ǰʱ�����1970-01-01����ʱ����λms
        long START = System.currentTimeMillis();

        directoryTraverseByRecursion(TMResource);

        long END = System.currentTimeMillis();
        System.out.println("Time Cost: " + (END - START) + "ms (" + END + "-"
                + "" + START + ")\n");

        System.out.println("RTArray size = " + RTArray.size());
        for (ResourceType rt : RTArray) {
            rt.printRType();
        }

        RTArray.clear();

        // ResourceType rt1 = new ResourceType("WWE-Z-20140515-45mb-rmvb");
        // rt1.printRType();
        // rt1.printRType(rt1.getFullName());
        //
        //
        // ResourceType rt = new ResourceType("a5", "1a", "a2", "3a", "4a");
        // rt.printRType();
        // rt.printRType(rt.getFullName());

        // for (String x : Video) {
        // System.out.println(x);
        // }
        // for (int i = 0; i < Video.length; i++) {
        // System.out.println(Video[i]);
        // }
        // System.out.println(TMResource);
    }
}
