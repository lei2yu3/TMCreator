package com.malloc.ntmc;

import java.io.IOException;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class BaseResource {
    /**
     * All the resource Video视频，"rmvb", "avi", "mp4", "flv", "wmv"
     * Audio音频，"mp3", "wav", "ape", "ogg" Diagram图片，"jpg", "bmp", "png", "git"
     * Book书籍，"txt", "pdf", "doc", "ppt" Code代码，"cpp", "h", "java", "cs", "py",
     * "xml" Other其他，"exe", "plist", "bat"
     */
    static final String VideoArray[] = { "rmvb", "avi", "mp4", "flv", "wmv" };
    static final String AudioArray[] = { "mp3", "wav", "ape", "ogg" };
    static final String DiagramArray[] = { "jpg", "bmp", "png", "git" };
    static final String BookArray[] = { "txt", "pdf", "doc", "ppt" };
    static final String CodeArray[] = { "cpp", "h", "java", "cs", "py", "xml" };
    static final String OtherArray[] = { "exe", "plist", "bat" };
    static final String tempArray[] = { "ResourceArray", "VideoArray",
            "AudioArray", "DiagramArray", "BookArray", "CodeArray",
            "OtherArray" };

    static final String ResourceArray3[][] = {
            { "Video", "rmvb", "avi", "mp4", "flv", "wmv" },
            { "Audio", "mp3", "wav", "ape", "ogg" },
            { "Diagram", "jpg", "bmp", "png", "git" },
            { "Book", "txt", "pdf", "doc", "ppt" },
            { "Code", "cpp", "h", "java", "cs", "py", "xml" },
            { "Other", "exe", "plist", "bat" } };

    public static final String ResourceArray[] = { "Video", "Audio", "Diagram",
            "Book", "Code", "Other" };

    public static final String ResourceArray2[][] = {
            { "rmvb", "avi", "mp4", "flv", "wmv" },
            { "mp3", "wav", "ape", "ogg" }, { "jpg", "bmp", "png", "git" },
            { "txt", "pdf", "doc", "ppt" },
            { "cpp", "h", "java", "cs", "py", "xml" },
            { "exe", "plist", "bat" } };

    private String sResourceName;
    public TopicIF resourceTopic;

    public BaseResource() {
        sResourceName = null;
        resourceTopic = null;
    }

    public BaseResource(String srN) {
        sResourceName = srN;
        resourceTopic = null;
    }

    static ArrayList<BaseResource> brArray = new ArrayList<BaseResource>();

    static TopicMapStoreIF store = new InMemoryTopicMapStore();
    static TopicMapIF topicmap = store.getTopicMap();
    static TopicMapBuilderIF builder = topicmap.getBuilder();

    /**在Creator中
     * BaseResource br = new BaseResource();
     * TopicIF[] a = br.getArray();
     * Resource r = new Resource(getFileName());
     * if(r.getSuiffx() == br.getName)
     * r.getTopic.addType(br.getTopic());
    */
    public static void main(String[] args) {
        TopicIF tr = builder.makeTopic();
        builder.makeTopicName(tr, "Resource");

        // having created the topic map we are now ready to export it
        try {
            new XTMTopicMapWriter("newTM.xtm").write(topicmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
