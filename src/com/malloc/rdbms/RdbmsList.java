package com.malloc.rdbms;

import java.util.Iterator;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMapReferenceIF;
import net.ontopia.topicmaps.impl.rdbms.RDBMSTopicMapSource;

import java.util.List;
import net.ontopia.topicmaps.query.utils.QueryWrapper;

/**
 * EXAMPLE: A simple command line tool that lists the topic maps that are stored
 * in a relational database.
 * 
 * 读取指定数据库中的TM并输出
 */

public class RdbmsList {

    public static void main(String[] argv) throws Exception {

        // Usage:
        //
        // java RdbmsList <propfile>
        // java RdbmsList /tmp/myprops.xml

        System.out.println("Connecting...");
        //String propfile = argv[0];
		//String propfile = "tm-sources.xml";
		String propfile = "db.semantic.properties";
		
        // create a source which can enumerate the topic maps
        RDBMSTopicMapSource source = new RDBMSTopicMapSource();
        source.setPropertyFile(propfile);
        Iterator tms = source.getReferences().iterator();
		
		System.out.println(tms.toString());
        
		while (tms.hasNext()) {
		System.out.println("hasNext");
            TopicMapReferenceIF ref = null;
            TopicMapStoreIF store = null;

            try {
                ref = (TopicMapReferenceIF) tms.next();
                store = ref.createStore(true);
                TopicMapIF tm = store.getTopicMap();

                System.out.println("Topic Map ID: " + tm.getObjectId());
                System.out.println("  Topics: " + tm.getTopics().size());
                System.out.println("  Associations: "
                        + tm.getAssociations().size());
						
				
		QueryWrapper wrapper = new QueryWrapper(tm);
        //System.out.println(wrapper.queryForMaps("association($ASSOC)?").size());

        String s = "occurrence(puccini, $occ) order by $occ?";
        //String s = "topic-name(puccini,$T1)?";
        List list = wrapper.queryForMaps(s);

        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i));
        }
		
        System.out.println("Done.");		
						
						
						
						
                store.close();
            } finally {
                if (store != null)
                    store.close();
                if (ref != null)
                    ref.close();
            }
        }


    }
}
