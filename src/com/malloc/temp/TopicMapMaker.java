package com.malloc.temp;

import java.io.IOException;

import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.ScopedIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;
import net.ontopia.topicmaps.xml.XTMTopicMapWriter;

public class TopicMapMaker {

    public static void main(String[] args) {

        // 创建TM
        TopicMapStoreIF store = new InMemoryTopicMapStore();
        TopicMapIF topicmap = store.getTopicMap();
        TopicMapBuilderIF builder = topicmap.getBuilder();

        // 添加Topic
        TopicIF topicDeveloper = builder.makeTopic();
        builder.makeTopicName(topicDeveloper, "Developer");

        TopicIF topicMalloc = builder.makeTopic();
        builder.makeTopicName(topicMalloc, "Malloc");
        topicMalloc.addType(topicDeveloper); // add type(association of
                                             // InstenceOf)
        // 添加Occurrence
        TopicIF occurenceCharming = builder.makeTopic();
        builder.makeTopicName(occurenceCharming, "Charming");
        OccurrenceIF o1 = builder.makeOccurrence(topicMalloc,
                occurenceCharming, "I am charming!");

        TopicIF topicTreeZ = builder.makeTopic();
        builder.makeTopicName(topicTreeZ, "TreeZ");
        topicTreeZ.addType(topicDeveloper); // add type(association of
                                            // InstenceOf)
        // 添加Occurrence
        TopicIF occurenceCute = builder.makeTopic();
        builder.makeTopicName(occurenceCharming, "Cute");
        OccurrenceIF o2 = builder.makeOccurrence(topicTreeZ, occurenceCute,
                "I am CUTE!");

        TopicIF topicCompany = builder.makeTopic();
        builder.makeTopicName(topicCompany, "Company");

        TopicIF topicOntopia = builder.makeTopic();
        builder.makeTopicName(topicOntopia, "Ontopia");
        topicOntopia.addType(topicCompany); // add type(association of
                                            // InstenceOf)
        // 添加Occurrence
        TopicIF occurenceWebSite = builder.makeTopic();
        builder.makeTopicName(occurenceWebSite, "WebSite");
        OccurrenceIF o3 = builder.makeOccurrence(topicOntopia,
                occurenceWebSite, "http://g.cn"); // 外部实例 makeOccurrence(TopicIF
                                                  // topic, TopicIF occurs_type,
                                                  // LocatorIF locator)

        TopicIF topicEmployment = builder.makeTopic();
        builder.makeTopicName(topicEmployment, "Employment");

        TopicIF topicEmployee = builder.makeTopic();
        builder.makeTopicName(topicEmployee, "Employee");

        TopicIF topicEmployer = builder.makeTopic();
        builder.makeTopicName(topicEmployer, "Employer");

        // 添加Association
        AssociationIF aEmployment = builder.makeAssociation(topicEmployment);
        AssociationRoleIF rEmployee1 = builder.makeAssociationRole(aEmployment,
                topicEmployee, topicMalloc);
        AssociationRoleIF rEmployer1 = builder.makeAssociationRole(aEmployment,
                topicEmployer, topicOntopia);

        AssociationIF aEmployment1 = builder.makeAssociation(topicEmployment);
        AssociationRoleIF rEmployee2 = builder.makeAssociationRole(
                aEmployment1, topicEmployee, topicTreeZ);
        AssociationRoleIF rEmployer2 = builder.makeAssociationRole(
                aEmployment1, topicEmployer, topicOntopia);

        // 添加Scope
        // TopicIF themeEmploys = builder.makeTopic();
        // builder.makeTopicName(themeEmploys, "Emloys");
        // TopicIF themeEmploed = builder.makeTopic();
        // builder.makeTopicName(themeEmploed, "Emploed");
        //
        // ScopedIF sEmploys = null;
        // ScopedIF sEmployed = null;
        //
        // sEmploys.addTheme(themeEmploys);
        // sEmployed.addTheme(themeEmploed);

        // 添加InstenceOf关联
        // TopicIF ATInstenceOf = builder.makeTopic();
        // builder.makeTopicName(ATInstenceOf, "InstenceOf");
        //
        // TopicIF ARTInstence = builder.makeTopic();
        // builder.makeTopicName(ARTInstence, "Instence");
        //
        // TopicIF ARTClass = builder.makeTopic();
        // builder.makeTopicName(ARTClass, "Class");
        //
        // AssociationIF a1 = builder.makeAssociation(ATInstenceOf);
        // AssociationRoleIF ar1 = builder.makeAssociationRole(a1, ARTInstence,
        // topicMalloc);
        // AssociationRoleIF ar2 = builder.makeAssociationRole(a1, ARTClass,
        // topicDeveloper);
        //
        // AssociationIF a2 = builder.makeAssociation(ATInstenceOf);
        // AssociationRoleIF ar3 = builder.makeAssociationRole(a2, ARTInstence,
        // topicTreeZ);
        // AssociationRoleIF ar4 = builder.makeAssociationRole(a2, ARTClass,
        // topicDeveloper);

        // 添加Association范例
        // AssociationIF a3 = builder.makeAssociation(ATInstenceOf);
        // AssociationRoleIF ar5 = builder.makeAssociationRole(a3, ARTInstence,
        // topicOntopia);
        // AssociationRoleIF ar6 = builder.makeAssociationRole(a3, ARTClass,
        // topicCompany);

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

        // having created the topic map we are now ready to export it
        try {
            new XTMTopicMapWriter("1234.xtm").write(topicmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("I have a topic map with "
                + topicmap.getTopics().size() + " TOPICS");
    }
}
