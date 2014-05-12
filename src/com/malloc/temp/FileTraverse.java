package com.malloc.temp;

import java.io.File;
import java.util.ArrayList;

public class FileTraverse {
    private static ArrayList filelist = new ArrayList();

    // FileTraverse
    public static void refreshFileList(String strPath) {

        File dir = new File(strPath);
        if (!dir.isDirectory()) {
            System.out.println(strPath + " is not a dir!");
            return;
        }

        File[] files = dir.listFiles();

        if (files == null) {
            System.out.println(strPath + " is empty!");
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                refreshFileList(files[i].getAbsolutePath());
            } else {
                //String strFileName = files[i].getAbsolutePath();// .toLowerCase();
                //files[i].getPath();
                // System.out.println("---" + strFileName);
                System.out.println("---" + files[i].getName());
                filelist.add(files[i].getAbsolutePath());
            }
        }
    }

    //
    public static void main(String[] args) {

        long a = System.currentTimeMillis();

        refreshFileList("X:\\EclipseWorkspace\\TMResrouce");
        
        System.out.println("There" + (filelist.size() > 1 ? " are " : " is ") + filelist.size() + (filelist.size() > 1 ? " files" : " file"));
        
        System.out.println("Time Cost : " + (System.currentTimeMillis() - a));
    }
}