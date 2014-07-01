package com.malloc.tolog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ontopoly.model.QueryMapper;
import ontopoly.model.Topic;
import ontopoly.model.TopicMap;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.ParsedQueryIF;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.impl.basic.QueryProcessor;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.utils.ltm.LTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class tologTest {

    public TopicMapIF topicmap;
    public TopicMapBuilderIF builder;
    public static QueryProcessorIF processor;

    public static void main(String[] args) throws IOException,
            InvalidQueryException {

        long START = System.currentTimeMillis();

        // read TM from a ltm file
        LTMTopicMapReader ltmReader = new LTMTopicMapReader(new File(
                "ItalianOpera.ltm"));
        TopicMapIF tm = ltmReader.read();
        System.out.println("I have a topic map with " + tm.getTopics().size()
                + " topics!");

        // write TM to a xtm
        new XTMTopicMapWriter("io.xtm").write(tm);
        // new XTMTopicMapWriter(xxx.xtm).write(new LTMTopicMapReader(new
        // File(xxx.ltm)).read());

        // read TM from a xtm
        XTMTopicMapReader xtmReader = new XTMTopicMapReader(new File("io.xtm"));
        TopicMapIF tm1 = xtmReader.read();
        System.out.println("I have a topic map with " + tm.getTopics().size()
                + " topics!");

        System.out.println("I have a topic map with "
                + tm.getAssociations().size() + " association!");

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        // query
        // QueryProcessorIF processor = new QueryProcessor(tm);
        // String s = "association($ASSOC)?";
        // QueryResultIF result = processor.execute(s);
        // System.out.println(result.getIndex("ASSOC"));
        // ParsedQueryIF query = processor.parse("association($ASSOC)?");
        // result.close();

        QueryWrapper wrapper = new QueryWrapper(tm);
        System.out.println(wrapper.queryForMaps("association($ASSOC)?").size());

        // String s =
        // "select $PERSON from born-in($PERSON : person, $CITY : place), located-in($CITY : containee, italy : container) order by $PERSON?";
        //String s = queryTopicNameByNameString("puccini"); 
        String s = "occurrence(puccini, $OCC)?";
        @SuppressWarnings("unchecked")
        List list = wrapper.queryForMaps(s);

        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i));
        }

        findTopicByName("aida", tm1);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        // QueryMapper test

        // String query = "association($ASSOC)?";
        // TopicMap tmxxxxx = new TopicMap(tm1, "madonna-imperia-c");
        // System.out.println("@@@@@@@@@@@@" + tmxxxxx.getId());
        // QueryMapper<Topic> qm = tmxxxxx.newQueryMapper(Topic.class);
        // Topic big = qm.queryForObject(query);
        // System.out.println(big.toString());

        long END = System.currentTimeMillis();
        System.out.println("Time Cost: " + (END - START) + "ms (" + END + "-"
                + "" + START + ")\n");

    }

    public static String queryTopicNameByNameString(String NameString) {
        return "topic-name(" + NameString + " , $TopicName)?";
    }

    public static void findTopicByName(String ss, TopicMapIF tt) {
        QueryWrapper wrapper = new QueryWrapper(tt);
        String s = "topic-name(" + ss + " , $TopicName)?";
        List list = wrapper.queryForMaps(s);

        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {

            System.out.println(list.get(i));
        }
    }

    // @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    // public static void verifyQuery(List matches, String query, String
    // ruleset,
    // Map args) throws InvalidQueryException {
    // matches = new ArrayList(matches);
    // if (ruleset != null) {
    // processor.load(ruleset);
    // }
    // QueryResultIF result = null;
    // if (args != null) {
    // result = processor.parse(query).execute(args);
    // } else {
    // result = processor.execute(query);
    // }
    // try {
    // while (result.next()) {
    // Map match = getMatch(result);
    //
    // matches.remove(match);
    // }
    // } finally {
    // result.close();
    // }
    // }
    //
    // @SuppressWarnings({ "unchecked", "rawtypes" })
    // public static Map getMatch(QueryResultIF result) {
    // Map match = new HashMap();
    // for (int ix = 0; ix < result.getWidth(); ix++) {
    // String vname = result.getColumnName(ix);
    // match.put(vname, result.getValue(ix));
    // }
    // return match;
    // }
    //

}
