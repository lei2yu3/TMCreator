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

public class QueryMe {

    final static int VALUE = 1;
    final static int VALUE_LIKE = 2;
    final static int VALUE_STRING = 3;
    final static int VALUE_LIKE_STRING = 4;
    final static int OCCURRENCE_all = 5;
    final static int TOPIC_all = 6;
    final static int ASSOCIATION_all = 7;
    final static int OCCURRENCE_specified = 8;
    final static int ASSOCIATION_specified = 9;

    final static int OccurrenceHehe = 11;
    final static int AssociationHehe = 12;
    final static int TopicAssociationHehe = 13;
    final static int TopicOccurrenceHehe = 14;

    final static int OccurrenceTypeHehe = 15;
    final static int AssociationTypeHehe = 16;

    // enum Mode {
    // VALUE, VALUE_LIKE, VALUE_STRING, ASSOCIATION, OCCURRENCE, TOPIC
    // };

    public static void main(String[] args) {

        long START, END;
        int MODE = 0;
        String ss = null;
        // Mode m = Mode.VALUE;

        try {

            TopicMapStoreIF rdbmsSrore = new InMemoryTopicMapStore();
            TopicMapIF tm = rdbmsSrore.getTopicMap();
            TopicMapImporterIF reader = new XTMTopicMapReader(new File(
            // "dbtest.xtm"));
                    "hehetest.xtm"));
            reader.importInto(tm);

            System.out.println("/////////////////////////////");

            System.out.println("Imported (id " + tm.getObjectId() + ").");
            System.out.println("size = " + tm.getTopics().size());

            System.out.println("=============================");

            // query
            QueryWrapper wrapper = new QueryWrapper(tm);

            MODE = 13;

            switch (MODE) {
            case VALUE:
                // 1 value谓词，大小写敏感，需全词匹配。仅搜索出TopicName与指定NAME字符串完全匹配的Topic
                ss = "select $name from topic-name($topic, $name), value($name, \"王春\")?";
                break;
            case VALUE_LIKE:
                // 2 value-like谓词，大小写不敏感，需全词匹配。搜索出所有包含指定NAME字符串的Topic
                ss = "select $name from topic-name($topic, $name), value-like($name, \"王春\")?";
                break;
            case VALUE_STRING:
                // 3 value +
                // string模块，大小写敏感，无需全词匹配，可根据部分字符串查询。搜索出所有包含指定STR字符串（也就是NAME字符串）的Topic
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str  topic-name($TOPIC, $n ), value($n ,$SI), str:contains($SI, \"王春\")?\"";
                break;
            case VALUE_LIKE_STRING:// ERROR！
                // 4 value-like + string模块，value-like 的第二个变量必须被绑定，故不能使用string
                // module
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str  topic-name($TOPIC, $n ), value-like($n ,$SI), str:contains($SI, \"王春\")?\"";
                break;
            case OCCURRENCE_all:
                // 5 find topic which have occurrence
                ss = "select $s from occurrence($topic, $occ), value($occ, $s)?";
                break;
            case TOPIC_all:
                // 6 find all topic in TM
                ss = "select $s from topic-name($topic, $name), value($name, $s)?";
                break;
            case ASSOCIATION_all:
                // 7 find all association in TM
                ss = "association($ass)?";
                break;
            case OCCURRENCE_specified:
                // 8
                ss = "select $s,$occ from occurrence($topic, $occ), value-like($occ, \"川西致密气藏水平井控水压裂技术研究\"), topic-name($topic, $name), value($name, $s)?";
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str   occurrence($topic, $occ), value($occ, $SI), str:contains($SI, \"川西致密\"), topic-name($topic, $name), value($name, $s)?";
                break;
            case ASSOCIATION_specified:
                // 9
                ss = "select $s from association($ASSOC), type($ASSOC, $TYPE), topic-name($TYPE,$n), value($n,$s)?";
                ss = "select $n from association($ASSOC), type($ASSOC, $TYPE), topic-name($TYPE,$n), value($n, \"Employment\")?";
                break;

            case OccurrenceHehe:
                // 11
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str select $Occurrence, $OccurrenceType, $OccurrenceValue, $Topic, $TopicName from occurrence($Topic, $Occurrence), type($Occurrence,$OccurrenceType), {resource($Occurrence,$OccurrenceValue) | value($Occurrence,$OccurrenceValue)}, str:contains($OccurrenceValue, \"2010年区块登记与\"), topic-name($Topic, $name), value($name, $TopicName) order by $Occurrence?";
                break;

            case AssociationHehe:
                // 12
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str select $RoleTopic1, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from association($Association), type($Association, $AssociationType), topic-name($AssociationType, $AssociationName), value($AssociationName, $AssociationString), str:contains($AssociationString, \"Employment\"), role-player($role1, $RoleTopic1),association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $RoleTopic2), $RoleTopic1 /= $RoleTopic2, type($role1, $RoleType1), type($role2, $RoleType2) order by $Association?";
                break;

            case TopicAssociationHehe:
                // 13
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str select $Topic, $TopicName, $RoleType1, $Association, $AssociationType, $RoleTopic2, $RoleType2 from topic-name($Topic, $name), value($name, $TopicName), str:contains($TopicName, \"李亚文\"), role-player($role1, $Topic), association-role($Association, $role1), association-role($Association, $role2), role-player($role2, $RoleTopic2), $Topic /= $RoleTopic2, type($role1, $RoleType1), type($role2, $RoleType2), type($Association, $AssociationType) order by $Topic?";
                break;

            case TopicOccurrenceHehe:
                // 14
                ss = "import \"http://psi.ontopia.net/tolog/string/\" as str select $Topic, $TopicName, $Occurrence, $OccurrenceType, $OccurrenceValue from topic-name($Topic, $name), value($name, $TopicName), str:contains($TopicName, \"李亚文\"), occurrence($Topic, $Occurrence), type($Occurrence,$OccurrenceType), {resource($Occurrence,$OccurrenceValue) | value($Occurrence,$OccurrenceValue)} order by $Topic?";
                break;

            default:
                // other mode
                ss = "select * from hahaha";
                break;
            }

            // System.out.println(ss);

            String sss = "association($ASSOC)?";
            // sss = "topic($topic)?";
            // sss = "select $name from topic-name($topic, $name),"
            // +"value($name, \"王春\")?";
            // sss =
            // "select $name from topic-name($topic, $name), value-like($name, \"王春\")?";
            // sss =
            // "select $s from occurrence($topic, $OCC), value($OCC, \"川西致密气藏水平井控水压裂技术研究及应用.pdf\"), topic-name($topic, $name), value($name, $s)?";
            // sss =
            // "import \"http://psi.ontopia.net/tolog/string/\" as str  topic-name($TOPIC, $n ), value($n ,$SI), str:contains($SI, \"王春\")?\"";
            // sss =
            // "import \"http://psi.ontopia.net/tolog/string/\" as str select $s from occurrence($topic, $OCC), value($OCC,$ss), str:contains($ss, \"川西致密气藏水平井控水压裂技术研究及应用\"), topic-name($topic, $name), value($name, $s)?\"";
            sss = ss;
            System.out.println("search for : \n" + sss);

            // System.out.println(wrapper.queryForMaps(sss).size());

            START = System.currentTimeMillis();
            @SuppressWarnings("unchecked")
            List list = wrapper.queryForMaps(sss);
            // List list = wrapper.queryForList(sss);

            END = System.currentTimeMillis();
            System.out.println("Query Time Cost: " + (END - START) + "ms ("
                    + END + "-" + "" + START + ")");

            System.out.println("=============================");

            System.out.println("Query result : \n");

            START = System.currentTimeMillis();
            for (int q = 0; q < list.size(); q++) {
                System.out.println(list.get(q));
            }
            System.out.println("\nresult SIZE = " + list.size());
            // END = System.currentTimeMillis();
            // System.out.println("Print Time Cost: " + (END - START)
            // + "ms (" + END + "-" + "" + START + ")\n");

            rdbmsSrore.commit();
            rdbmsSrore.close();

            // BufferedReader br = new BufferedReader(
            // new InputStreamReader(System.in));
            // String query = br.readLine();
            //
            // br.close();

            System.out.println("/////////////////////////////");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
