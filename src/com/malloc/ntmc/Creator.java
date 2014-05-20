package com.malloc.ntmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class Creator {

    // path of TMResource
    static final String TMResource = "X:/EclipseWorkspace/TMResrouce";
    // static final String TMObject = "";
    // static final String TMResource = "X:\\EclipseWorkspace\\TMResrouce";

    File dir = new File(TMResource);
    // �洢
    static ArrayList<Resource> resourceArray = new ArrayList<Resource>();
    static ArrayList<BaseResource> baseResourceArray = new ArrayList<BaseResource>();

    // Store & Builder
    // static TopicMapStoreIF store = new InMemoryTopicMapStore();
    // static TopicMapIF topicmap = store.getTopicMap();
    // static TopicMapBuilderIF builder = topicmap.getBuilder();

    //
    public static void reourceSearch(String sPath) {

        File resourceDir = new File(sPath);
        if (!resourceDir.isDirectory()) {
            System.out.println(sPath + "is not a directory!");
            return;
        }

        // listFiles����һ��File�������飬ÿ�����鱣���ӦĿ¼�µ�ÿ���ļ���Ŀ¼
        File[] fileArray = resourceDir.listFiles();

        for (int i = 0; i < fileArray.length; i++) {
            if (fileArray[i].isDirectory()) {
                // getName�����ɴ�·������ʾ���ļ���Ŀ¼������
                // System.out.println("-" + fileArray[i].getName() + "\n");

                // getAbsolutePath���ش�·�����Ĺ淶·�����ַ�����getPath��·����ת��Ϊһ��·�����ַ���
                reourceSearch(fileArray[i].getAbsolutePath());

            } else {
                // System.out.println("---" + fileArray[i].getName());

                Resource rt = new Resource(fileArray[i].getName());

                // ���ļ���Resource���󱣴浽ArrayList
                resourceArray.add(rt);
            }
        }
    }

    public static void main(String[] args) {
        // TopicIF topicResource = builder.makeTopic();
        // builder.makeTopicName(topicResource, "Resource");

        long START = System.currentTimeMillis();

        reourceSearch(TMResource);
        System.out.println();

        BaseResource br = new BaseResource();
        baseResourceArray = br.BaseResourceInit();

        // for(BaseResource bb:baseResourceArray){
        // System.out.println("bb.getName = " + bb.getName().toString());
        // }

        for (Resource r : resourceArray) {
            // r.resourcePrint();
            // System.out.println(r.getResourceFileName());
            // TopicIF topicResource = builder.makeTopic();
            // builder.makeTopicName(topicResource, r.getResourceFileName());
            r.resourceTopic = br.brBuilder.makeTopic();
            br.brBuilder
                    .makeTopicName(r.resourceTopic, r.getResourceFileName());

            for (BaseResource bb : baseResourceArray) {
                if (r.getResourceSuffix().equals(bb.getName())) {
                    r.resourceTopic.addType(bb.baseResourceTopic);
                    // System.out.println("bbbb");
                } else {
                    // System.out.println("aaaa");
                }
                // System.out.println(r.getResourceSuffix().equals(bb.getName()));
                // System.out.println("r.Suffix = " + r.getResourceSuffix());
                // System.out.println("bb.getName = " + bb.getName());
            }
        }

        // having created the topic map we are now ready to export it
        try {
            new XTMTopicMapWriter("newTM.xtm").write(br.brTM);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long END = System.currentTimeMillis();
        System.out.println("Time Cost: " + (END - START) + "ms (" + END + "-"
                + "" + START + ")\n");

        System.out.println("I have a topic map with "
                + br.brTM.getTopics().size() + " TOPICS");
        System.out.println(resourceArray.size());
        System.out.println(br.brArrayList.size());
    }
}
