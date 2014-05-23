package com.malloc.nrtmc;

import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;
import net.ontopia.topicmaps.utils.MergeUtils;

public class BaseResource {

    public static final String BaseResourceArray[][] = {
            { "Video", "Audio", "Diagram", "Book", "Code", "Other" },
            { "rmvb", "avi", "mp4", "flv", "wmv" },
            { "mp3", "wav", "ape", "ogg" }, { "jpg", "bmp", "png", "git" },
            { "txt", "pdf", "doc", "ppt" },
            { "cpp", "h", "java", "cs", "py", "xml" },
            { "exe", "plist", "bat" } };

    public static final String brTypeArray[] = { "Video", "Audio", "Diagram",
            "Book", "Code", "Other" };

    // public static final String brArray[][] = {
    public final String brArray[][] = { { "rmvb", "avi", "mp4", "flv", "wmv" },
            { "mp3", "wav", "ape", "ogg" }, { "jpg", "bmp", "png", "git" },
            { "txt", "pdf", "doc", "ppt" },
            { "cpp", "h", "java", "cs", "py", "xml" },
            { "exe", "plist", "bat" } };

    public static ArrayList<TopicIF> tArrayList = new ArrayList<TopicIF>();
    public static ArrayList<BaseResource> brArrayList = new ArrayList<BaseResource>();

    // base resource attribute
    private String resourceBaseName;
    public TopicIF resourceTopic;

    public BaseResource() {
        resourceBaseName = null;
        resourceTopic = null;
    }

    // 将FileName分解
    public BaseResource(String sName) {
        setResourceBaseName(sName);
        resourceTopic = null;
    }

    // get&set resourceBaseName
    public String getResourceBaseName() {
        return resourceBaseName;
    }

    private void setResourceBaseName(String brName) {
        resourceBaseName = brName;
    }

//    static TopicMapStoreIF brStore = new InMemoryTopicMapStore();
//    static TopicMapIF brTM = brStore.getTopicMap();
//    static TopicMapBuilderIF brBuilder = brTM.getBuilder();

     TopicMapStoreIF brStore = new InMemoryTopicMapStore();
     TopicMapIF brTM = brStore.getTopicMap();
     TopicMapBuilderIF brBuilder = brTM.getBuilder();

    public ArrayList<BaseResource> BaseResourceInit() {
    //public static ArrayList<BaseResource> BaseResourceInit() {

        // 使用brArrayList创建BaseResource ArrayList
        BaseResource br = new BaseResource("Resource");
        br.resourceTopic = brBuilder.makeTopic();
        brBuilder.makeTopicName(br.resourceTopic, "Resource");
        brArrayList.add(br);

        for (int i = 0; i < BaseResourceArray.length; i++) {
            for (int j = 0; j < BaseResourceArray[i].length; j++) {

                BaseResource brx = new BaseResource(BaseResourceArray[i][j]);
                brx.resourceTopic = brBuilder.makeTopic();
                brBuilder.makeTopicName(brx.resourceTopic,
                        BaseResourceArray[i][j]);
                if (i == 0) {
                    brx.resourceTopic
                            .addType(brArrayList.get(0).resourceTopic);
                } else {
                    brx.resourceTopic
                            .addType(brArrayList.get(i).resourceTopic);
                }
                brArrayList.add(brx);
            }
        }
        
        return brArrayList;
    }

    public static void main(String[] args) {

        // TopicIF tR = brBuilder.makeTopic();
        // brBuilder.makeTopicName(tR, "Resource");
        //
        // // 使用两个数组brTypeArray & brArray创建TM
        // for (String s : brTypeArray) {
        // TopicIF tt = brBuilder.makeTopic();
        // brBuilder.makeTopicName(tt, s);
        // tt.addType(tR);
        // tArrayList.add(tt);
        // }
        //
        // for (int i = 0; i < brArray.length; i++) {
        // for (int j = 0; j < brArray[i].length; j++) {
        //
        // TopicIF ttt = brBuilder.makeTopic();
        // brBuilder.makeTopicName(ttt, brArray[i][j]);
        // ttt.addType(tArrayList.get(i));
        // tArrayList.add(ttt);
        // }
        // }
        //
        // for (TopicIF t : tArrayList) {
        // System.out.println(t.getTopicNames());
        // }

        //BaseResourceInit();

        // having created the topic map we are now ready to export it
//        try {
//            new XTMTopicMapWriter("newTM.xtm").write(brTM);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("I have a topic map with " + brTM.getTopics().size()
//                + " TOPICS");
    }
}
