package com.malloc.tmc;

import java.io.File;
import java.util.ArrayList;

public class TMCreator {

    /**
     * All the resource Video视频，"rmvb", "avi", "mp4", "flv", "wmv"
     * Audio音频，"mp3", "wav", "ape", "ogg" Diagram图片，"jpg", "bmp", "png", "git"
     * Book书籍，"txt", "pdf", "doc", "ppt" Code代码，"cpp", "h", "java", "cs", "py",
     * "xml" Other其他，"exe", "plist", "bat"
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

    // 存储
    static ArrayList<ResourceType> RTArray = new ArrayList<ResourceType>();

    // 以递归的方式遍历目录及其子目录的所有文件
    public static void directoryTraverseByRecursion(String sPath) {

        File resourceDir = new File(sPath);
        // isDirectory判断此路径所表示的文件是否是一个文件夹，isFile判断此路径所表示的文件是否是一个标准文件
        if (!resourceDir.isDirectory()) {
            System.out.println(sPath + "is not a directory!");
            return;
        }

        // listFiles返回一个File对象数组，每个数组保存对应目录下的每个文件或目录
        File[] arrayFile = resourceDir.listFiles();

        for (int i = 0; i < arrayFile.length; i++) {
            if (arrayFile[i].isDirectory()) {
                // getAbsolutePath返回此路径名的规范路径名字符串，getPath将路径名转换为一个路径名字符串
                directoryTraverseByRecursion(arrayFile[i].getAbsolutePath());

                // getName返回由此路径名表示的文件或目录的名称
                System.out.println("-" + arrayFile[i].getName() + "\n");

            } else {
                // 将文件的ResourceType对象保存到ArrayList
                ResourceType rt = new ResourceType(arrayFile[i].getName());
                RTArray.add(rt);

                System.out.println("---" + arrayFile[i].getName());
            }
        }
    }

    // 将读取到的资源信息存储到指定文件
    // public static void resourceAppend(String filePath, String appendContent)
    // {
    // try {
    // // 打开一个写文件器，FileWriter构造函数中的第二个参数true表示以追加形式写文件
    // FileWriter fw = new FileWriter(filePath, true);
    // fw.write(appendContent);
    // fw.close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // main for test
    public static void main(String[] args) {
        // currentTimeMillis返回当前时间距离1970-01-01零点的时间差，单位ms
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
