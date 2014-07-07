package com.test.rdbms;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;

public class DBQuery {
    public static void main(String[] args) {

        long START, END;

        try {
            String propfile = "db.xxx.props";
            long name = 1;
            // TopicMapStoreIF rdbmsSrore = new RDBMSTopicMapStore(propfile,
            // name);

            TopicMapStoreIF rdbmsSrore = new InMemoryTopicMapStore();

            // Get the new topic map object
            TopicMapIF tm = rdbmsSrore.getTopicMap();

            TopicMapImporterIF reader = new XTMTopicMapReader(new File(
                    "dbtest.xtm"));
            reader.importInto(tm);

            System.err.println("Imported (id " + tm.getObjectId() + ").");
            System.err.println("size = " + tm.getTopics().size());

            System.out.println("=============================");
            START = System.currentTimeMillis();
            // query
            QueryWrapper wrapper = new QueryWrapper(tm);
            String sss = "association($ASSOC)?";
            // sss = "topic($topic)?";
            // sss = "topic-name(id58660, $topic)?";
            // sss = "occurrence($r, $OCC)?";

            System.out.println(sss);

            System.out.println(wrapper.queryForMaps(sss).size());
            @SuppressWarnings("unchecked")
            List list = wrapper.queryForList(sss);

            END = System.currentTimeMillis();
            System.out.println("QueryWrapper Time Cost: " + (END - START)
                    + "ms (" + END + "-" + "" + START + ")\n");

            System.out.println("=============================");
            START = System.currentTimeMillis();
            for (int q = 0; q < list.size(); q++) {
                // System.out.println(list.get(q));
            }
            END = System.currentTimeMillis();
            // System.out.println("Print Time Cost: " + (END - START)
            // + "ms (" + END + "-" + "" + START + ")\n");

            // System.out.println(wrapper.queryForMaps(sss).size());
            System.out.println("hahaahahahahahah");

            rdbmsSrore.commit();
            rdbmsSrore.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
