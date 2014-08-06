package com.test.query;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapImporterIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.query.utils.QueryWrapper;
import net.ontopia.topicmaps.xml.XTMTopicMapReader;

// query the topic map
public class QueryMe {

    public enum Tolog {

        // 11 value谓词，大小写敏感，需全词匹配。仅搜索出TopicName与指定NAME字符串完全匹配的Topic
        VALUE(11, 
            "select $name from topic-name($topic, $name), value($name, \"" + keyWord + "\")?"), 
        // 12 value-like谓词，大小写不敏感，需全词匹配。搜索出所有包含指定NAME字符串的Topic
        VALUE_LIKE(12, 
            "select $name from topic-name($topic, $name), value-like($name, \"" + keyWord + "\")?"), 
        // 13 value + string模块，大小写敏感，无需全词匹配，可根据部分字符串查询。搜索出所有包含指定STR字符串（也就是NAME字符串）的Topic    
        VALUE_STRING(13, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str  topic-name($TOPIC, $n ), value($n ,$SI), str:contains($SI, \"" + keyWord + "\")?\""), 
        // 14 value-like + string模块，value-like 的第二个变量必须被绑定，故不能使用string module
        VALUE_LIKE_STRING(14, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str  topic-name($TOPIC, $n ), value-like($n ,$SI), str:contains($SI, \"" + keyWord + "\")?\""), 
        

        // 21 find all topic in TM    
        TOPIC_All(21, 
            "select $s from topic-name($topic, $name), value($name, $s)?"), 
        // 22 find all association in TM
        ASSOCIATION_All(22, 
            "association($ass)?"),
        // 23 find topic which have occurrence
        OCCURRENCE_All(23, 
            "select $s from occurrence($topic, $occ), value($occ, $s)?"),
        // 24
        TOPIC_Part(24, 
                ""),
        // 25
        ASSOCIATION_Part(25, 
            "select $n from association($ASSOC), type($ASSOC, $TYPE), topic-name($TYPE,$n), value($n, \"" + keyWord + "\")?"),
        // 26
        OCCURRENCE_Part(26, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str select $s,$occ from occurrence($topic, $occ), value($occ, $SI), str:contains($SI, \"" + keyWord + "\"), topic-name($topic, $name), value($name, $s)?"),
        
        // 31 
        TOPIC_Association(31, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str select $Topic, $TopicName, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from topic-name($Topic, $name), value($name, $TopicName), str:contains($TopicName, \"" + keyWord + "\"), role-player($role1, $Topic), association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $RoleTopic2), $Topic /= $RoleTopic2, type($role1, $RoleType1), type($role2, $RoleType2), type($Association, $AssociationType) order by $Topic?"),
        // 32 
        TOPIC_Occurrence(32, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str select $Topic, $TopicName, $Occurrence, $OccurrenceType, $OccurrenceValue from topic-name($Topic, $name), value($name, $TopicName), str:contains($TopicName, \"" + keyWord + "\"), occurrence($Topic, $Occurrence), type($Occurrence,$OccurrenceType), {resource($Occurrence,$OccurrenceValue) | value($Occurrence,$OccurrenceValue)} order by $Topic?"),
        // 33 
        OCCURRENCE(33, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str select $Occurrence, $OccurrenceType, $OccurrenceValue, $Topic, $TopicName from occurrence($Topic, $Occurrence), type($Occurrence,$OccurrenceType), {resource($Occurrence,$OccurrenceValue) | value($Occurrence,$OccurrenceValue)}, str:contains($OccurrenceValue, \"" + keyWord + "\"), topic-name($Topic, $name), value($name, $TopicName) order by $Occurrence?"), 
        // 34 
        OCCURRENCE_Type(34, 
            ""), 
        // 35 
        ASSOCIATION(35, 
            "import \"http://psi.ontopia.net/tolog/string/\" as str select $RoleTopic1, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from association($Association), type($Association, $AssociationType), topic-name($AssociationType, $AssociationName), value($AssociationName, $AssociationString), str:contains($AssociationString, \"" + keyWord + "\"), role-player($role1, $RoleTopic1),association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $RoleTopic2), $RoleTopic1 /= $RoleTopic2, type($role1, $RoleType1), type($role2, $RoleType2) order by $Association?"), 
        // 36 
        ASSOCIATION_Type(36, 
            "");

        private int index;
        private String statement;

        private Tolog(int i, String s) {
            this.index = i;
            this.statement = s;
        }

        public int getIndex() {
            return index;
        }

        // private void setIndex(int ii) {
        // this.index = ii;
        // }
        

        public String getStatement() {
            return statement;
        }

        // private void setStatement(String ss) {
        // this.index = ss;
        // }
    };

    // query key word and statement
    static String keyWord = "川西致密气藏水平井控水压裂技术研究";
    static Tolog tologStatement = Tolog.OCCURRENCE;
    
    public static void main(String[] args) throws IOException {

        // for(Tolog t : Tolog.values())
        // System.out.println(t.index + " | " + t.statement);
         
        long START, END;
        String ss = null;

        TopicMapStoreIF rdbmsSrore = new InMemoryTopicMapStore();
        TopicMapIF tm = rdbmsSrore.getTopicMap();
        TopicMapImporterIF reader = new XTMTopicMapReader(new File(
         "dbtest.xtm"));
        //      "hehetest.xtm"));
        
        reader.importInto(tm);

        System.out.println("Imported (id " + tm.getObjectId() + ").");
        System.out.println("size = " + tm.getTopics().size());

        System.out.println("=============================");

        // query
        QueryWrapper wrapper = new QueryWrapper(tm);

        switch (tologStatement) {
            // value & value-like & string module
            case VALUE:
            case VALUE_LIKE:
            case VALUE_STRING:
            case VALUE_LIKE_STRING:// ERROR！
            
            // 基本TM元素的搜索
            case TOPIC_All:
            case ASSOCIATION_All:
            case OCCURRENCE_All:
            case TOPIC_Part:
            case ASSOCIATION_Part:
            case OCCURRENCE_Part:
            
            // 搜索结果及其相关结果
            case TOPIC_Association:
            case TOPIC_Occurrence:
            case ASSOCIATION:
            case ASSOCIATION_Type:
            case OCCURRENCE:
            case OCCURRENCE_Type:
                
                ss = tologStatement.getStatement();
                System.out.println("index = " + tologStatement.getIndex() + "\nstatement = " + ss);
                break;
    
            default:
                System.out.println("-----!!!ERROR Tolog Statement!!!-----");
                break;
        }

        System.out.println("search for : \n" + ss);
        // System.out.println(wrapper.queryForMaps(ss).size());

        START = System.currentTimeMillis();
        
        @SuppressWarnings("rawtypes")
        List list = wrapper.queryForMaps(ss);
        // List list = wrapper.queryForList(ss);

        END = System.currentTimeMillis();
        
        System.out.println("queryForMaps() Time Cost: " + (END - START) + "ms ("
                + END + "-" + "" + START + ")");

        System.out.println("=============================");

        System.out.println("Query result : \n");

        START = System.currentTimeMillis();
        
        for (int q = 0; q < list.size(); q++) {
            System.out.println(list.get(q));
        }
        
        System.out.println("\nresult SIZE = " + list.size());
        
        END = System.currentTimeMillis();
        
        System.out.println("println(list.get(q)) Time Cost: " + (END - START) + "ms ("
                + END + "-" + "" + START + ")\n");

        rdbmsSrore.commit();
        rdbmsSrore.close();

        // BufferedReader br = new BufferedReader(
        // new InputStreamReader(System.in));
        // String query = br.readLine();
        //
        // br.close();

    }
}
