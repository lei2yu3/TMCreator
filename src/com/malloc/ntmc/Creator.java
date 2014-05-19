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
    static final String TMObject = "";
    // static final String TMResource = "X:\\EclipseWorkspace\\TMResrouce";

    File dir = new File(TMResource);
    // �洢
    static ArrayList<Resource> resourceArray = new ArrayList<Resource>();

    // Store & Builder
    static TopicMapStoreIF store = new InMemoryTopicMapStore();
    static TopicMapIF topicmap = store.getTopicMap();
    static TopicMapBuilderIF builder = topicmap.getBuilder();

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
                //System.out.println("-" + fileArray[i].getName() + "\n");

                // getAbsolutePath���ش�·�����Ĺ淶·�����ַ�����getPath��·����ת��Ϊһ��·�����ַ���
                reourceSearch(fileArray[i].getAbsolutePath());

            } else {
                //System.out.println("---" + fileArray[i].getName());

                Resource rt = new Resource(fileArray[i].getName());

                // ���ļ���Resource���󱣴浽ArrayList
                resourceArray.add(rt);

            }
        }
    }
    
    public static void baseTopicCreator(){
        
        
        

        
        new BaseResource();
        for(String i[] : BaseResource.ResourceArray2){
            for(String j : i){
                System.out.println(j);
                
            }
        }
    }
    

    public static void main(String[] args) {
        TopicIF topicResource = builder.makeTopic();
        builder.makeTopicName(topicResource, "Resource");
        
        long START = System.currentTimeMillis();

        reourceSearch(TMResource);System.out.println();


        
        
        for (Resource r : resourceArray) {
            //r.resourcePrint();
            //System.out.println(r.getResourceFileName());
            // TopicIF topicResource = builder.makeTopic();
            // builder.makeTopicName(topicResource, r.getResourceFileName());
            r.resourceTopic =  builder.makeTopic();
            builder.makeTopicName(r.resourceTopic, r.getResourceFileName());
            r.resourceTopic.addType(topicResource);
        }

//        String[][] ssss = new BaseTopic().getResourceArray();
//        for(String[] sss : ssss){
//            int a = 0;
//            for(String ss : sss){
//                TopicIF btTopic =  builder.makeTopic();
//                builder.makeTopicName(btTopic, ss);
//                    
//                if(a != 0){
//                    btTopic.addType(arg0);
//                }
//               ss[][a]
//                a++;
//            }
//            
//        }

        baseTopicCreator();
        // having created the topic map we are now ready to export it
        try {
            new XTMTopicMapWriter("newTM.xtm").write(topicmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long END = System.currentTimeMillis();
        System.out.println("Time Cost: " + (END - START) + "ms (" + END + "-"
                + "" + START + ")\n");

        System.out.println("I have a topic map with "
                + topicmap.getTopics().size() + " TOPICS");
    }
}
