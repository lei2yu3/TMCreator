package com.malloc.ntmc;

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
    /**
     * All the resource Video视频，"rmvb", "avi", "mp4", "flv", "wmv"
     * Audio音频，"mp3", "wav", "ape", "ogg" Diagram图片，"jpg", "bmp", "png", "git"
     * Book书籍，"txt", "pdf", "doc", "ppt" Code代码，"cpp", "h", "java", "cs", "py",
     * "xml" Other其他，"exe", "plist", "bat"
     */

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
    private String baseResourceName;
    public TopicIF baseResourceTopic;

    public BaseResource() {
        baseResourceName = null;
        baseResourceTopic = null;
    }

    // 将FileName分解
    public BaseResource(String sName) {
        setName(sName);
        baseResourceTopic = null;
    }

    // get&set resourceName
    public String getName() {
        return baseResourceName;
    }

    private void setName(String brName) {
        baseResourceName = brName;
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
        br.baseResourceTopic = brBuilder.makeTopic();
        brBuilder.makeTopicName(br.baseResourceTopic, "Resource");
        brArrayList.add(br);

        for (int i = 0; i < BaseResourceArray.length; i++) {
            for (int j = 0; j < BaseResourceArray[i].length; j++) {

                BaseResource brx = new BaseResource(BaseResourceArray[i][j]);
                brx.baseResourceTopic = brBuilder.makeTopic();
                brBuilder.makeTopicName(brx.baseResourceTopic,
                        BaseResourceArray[i][j]);
                if (i == 0) {
                    brx.baseResourceTopic
                            .addType(brArrayList.get(0).baseResourceTopic);
                } else {
                    brx.baseResourceTopic
                            .addType(brArrayList.get(i).baseResourceTopic);
                }
                brArrayList.add(brx);
            }
        }
        
        return brArrayList;

        // 使用tArrayList创建BaseResource ArrayList
        // TopicIF tR = brBuilder.makeTopic();
        // brBuilder.makeTopicName(tR, "Resource");
        // tArrayList.add(tR);

        // for (int i = 0; i < BaseResourceArray.length; i++) {
        // for (int j = 0; j < BaseResourceArray[i].length; j++) {
        //
        // TopicIF tt = brBuilder.makeTopic();
        // brBuilder.makeTopicName(tt, BaseResourceArray[i][j]);
        // if (i == 0) {
        // tt.addType(tR);
        // } else {
        // tt.addType(tArrayList.get(i));
        // }
        // tArrayList.add(tt);
        // }
        // }
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

    static final String VideoArray[] = { "rmvb", "avi", "mp4", "flv", "wmv" };
    static final String AudioArray[] = { "mp3", "wav", "ape", "ogg" };
    static final String DiagramArray[] = { "jpg", "bmp", "png", "git" };
    static final String BookArray[] = { "txt", "pdf", "doc", "ppt" };
    static final String CodeArray[] = { "cpp", "h", "java", "cs", "py", "xml" };
    static final String OtherArray[] = { "exe", "plist", "bat" };

    static final String BaseResourceArray1[][] = {
            { "Video", "rmvb", "avi", "mp4", "flv", "wmv" },
            { "Audio", "mp3", "wav", "ape", "ogg" },
            { "Diagram", "jpg", "bmp", "png", "git" },
            { "Book", "txt", "pdf", "doc", "ppt" },
            { "Code", "cpp", "h", "java", "cs", "py", "xml" },
            { "Other", "exe", "plist", "bat" } };
}
