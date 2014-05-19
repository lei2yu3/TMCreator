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
    // 存储
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

        // listFiles返回一个File对象数组，每个数组保存对应目录下的每个文件或目录
        File[] fileArray = resourceDir.listFiles();

        for (int i = 0; i < fileArray.length; i++) {
            if (fileArray[i].isDirectory()) {
                // getName返回由此路径名表示的文件或目录的名称
                //System.out.println("-" + fileArray[i].getName() + "\n");

                // getAbsolutePath返回此路径名的规范路径名字符串，getPath将路径名转换为一个路径名字符串
                reourceSearch(fileArray[i].getAbsolutePath());

            } else {
                //System.out.println("---" + fileArray[i].getName());

                Resource rt = new Resource(fileArray[i].getName());

                // 将文件的Resource对象保存到ArrayList
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
