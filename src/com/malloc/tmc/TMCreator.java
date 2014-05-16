package com.malloc.tmc;

import java.io.File;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;

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
                // getName�����ɴ�·������ʾ���ļ���Ŀ¼������
                System.out.println("-" + arrayFile[i].getName() + "\n");

                // getAbsolutePath���ش�·�����Ĺ淶·�����ַ�����getPath��·����ת��Ϊһ��·�����ַ���
                directoryTraverseByRecursion(arrayFile[i].getAbsolutePath());

            } else {
                System.out.println("---" + arrayFile[i].getName());

                ResourceType rt = new ResourceType(arrayFile[i].getName());

                // �ж��ļ����Ƿ���������淶
                // if (rt.getFullName() == null || rt.getName() == null
                // || rt.getCreator() == null || rt.getDate() == null
                // || rt.getSize() == null || rt.getSuffix() == null)
                // continue;

                // ���ļ���ResourceType���󱣴浽ArrayList
                RTArray.add(rt);

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

    public TMCreator() {

    }

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

        // ����TM
        TopicMapStoreIF store = new InMemoryTopicMapStore();
        TopicMapIF topicmap = store.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        // ���Topic
        for(String ss : Video){
            builder.makeTopicName(builder.makeTopic(), ss);
        }
        
        
        //�и�String���飬�������������ÿ��String�����Ǳ�������
        //������Ϊǰ׺p���String������Ҫ��ôʵ�ְ������������p#SS�Ĳ���
//        String name[] = {...};
//        for (String SS : name) {
//            Person p#SS = new Person(SS);
//            getNickname(p#SS);
//        }

      
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
