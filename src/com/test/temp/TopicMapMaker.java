package com.test.temp;

import java.io.IOException;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.core.TopicNameIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

/* 
 * create a diamond-liked TM
 * 
 *      Developer ←→ Jill
 *          ↑         ↑
 *          ↓         ↓  
 *         Tom ←→ Ontopia ←→ Company
 * 
 * */

public class TopicMapMaker {

    public static void main(String[] args) throws IllegalAccessException {

        // 创建TM
        TopicMapStoreIF store = new InMemoryTopicMapStore();
        TopicMapIF topicmap = store.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        // 添加Topic
        TopicIF topicDeveloper = builder.makeTopic();
        builder.makeTopicName(topicDeveloper, "Developer");

        TopicIF topicJill = builder.makeTopic();
        builder.makeTopicName(topicJill, "Jill");
        topicJill.addType(topicDeveloper); // association of InstenceOf
        // 添加Occurrence
        TopicIF occurenceCharming = builder.makeTopic();
        builder.makeTopicName(occurenceCharming, "Charming");
        OccurrenceIF oCharming = builder.makeOccurrence(topicJill,
                occurenceCharming, "I am charming!");

        TopicIF topicTom = builder.makeTopic();
        builder.makeTopicName(topicTom, "Tom");
        topicTom.addType(topicDeveloper); // association of InstenceOf
        // 添加Occurrence
        TopicIF occurenceCute = builder.makeTopic();
        builder.makeTopicName(occurenceCute, "Cute");
        OccurrenceIF oCute = builder.makeOccurrence(topicTom, occurenceCute,
                "I am CUTE!"); // 内部实例makeOccurrence(TopicIF topic, TopicIF
                               // occurs_type, String value)

        TopicIF topicCompany = builder.makeTopic();
        builder.makeTopicName(topicCompany, "Company");

        TopicIF topicOntopia = builder.makeTopic();
        builder.makeTopicName(topicOntopia, "Ontopia");
        topicOntopia.addType(topicCompany); // association of InstenceOf
        // 添加Occurrence
        TopicIF occurenceWebSite = builder.makeTopic();
        builder.makeTopicName(occurenceWebSite, "WebSite");
        OccurrenceIF oWebSite = builder.makeOccurrence(topicOntopia,
                occurenceWebSite, "http://g.cn"); // 外部实例 makeOccurrence(TopicIF
                                                  // topic, TopicIF occurs_type,
                                                  // LocatorIF locator)

        TopicIF topicEmployment = builder.makeTopic();
        TopicNameIF tn1 = builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        TopicNameIF tn2 = builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        TopicNameIF tn3 = builder.makeTopicName(topicEmployer, "Employer");

        // 添加Association
        AssociationIF aEmployment1 = builder.makeAssociation(topicEmployment);
        AssociationRoleIF arEmployee1 = builder.makeAssociationRole(
                aEmployment1, topicEmployee, topicJill);
        AssociationRoleIF arEmployer1 = builder.makeAssociationRole(
                aEmployment1, topicEmployer, topicOntopia);

        AssociationIF aEmployment2 = builder.makeAssociation(topicEmployment);
        AssociationRoleIF arEmployee2 = builder.makeAssociationRole(
                aEmployment2, topicEmployee, topicTom);
        AssociationRoleIF arEmployer2 = builder.makeAssociationRole(
                aEmployment2, topicEmployer, topicOntopia);

        // 添加Association范例
        // TopicIF at1 = builder.makeTopic();
        // builder.makeTopicName(at1, "Ass_Type1");
        // TopicIF at2 = builder.makeTopic();
        // builder.makeTopicName(at2, "Ass_Type2");
        //
        // TopicIF art1 = builder.makeTopic();
        // builder.makeTopicName(art1, "Ass_RoleType1");
        // TopicIF art2 = builder.makeTopic();
        // builder.makeTopicName(art2, "Ass_RoleType2");
        // TopicIF art3 = builder.makeTopic();
        // builder.makeTopicName(art3, "Ass_RoleType3");
        //
        // TopicIF t1 = builder.makeTopic();
        // builder.makeTopicName(t1, "Topic1");
        // TopicIF t2 = builder.makeTopic();
        // builder.makeTopicName(t2, "Topic2");
        // TopicIF t3 = builder.makeTopic();
        // builder.makeTopicName(t3, "Topic3");
        //
        // AssociationIF Ass1 = builder.makeAssociation(at1);
        // AssociationRoleIF r1 = builder.makeAssociationRole(Ass1, art1, t1);
        // AssociationRoleIF r2 = builder.makeAssociationRole(Ass1, art2, t2);
        //
        // AssociationIF Ass2 = builder.makeAssociation(at1);
        // AssociationRoleIF r3 = builder.makeAssociationRole(Ass2, art1, t1);
        // AssociationRoleIF r4 = builder.makeAssociationRole(Ass2, art2, t3);

        try {
            new XTMTopicMapWriter("a diamond-liked TM.xtm").write(topicmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("I have a topic map with "
                + topicmap.getTopics().size() + " TOPICS");
    }
}
