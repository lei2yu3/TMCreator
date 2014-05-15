package com.malloc.temp;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
//import junit.framework.TestCase;
import net.ontopia.topicmaps.core.AssociationIF;
import net.ontopia.topicmaps.core.AssociationRoleIF;
import net.ontopia.topicmaps.core.OccurrenceIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.core.TopicMapBuilderIF;
import net.ontopia.topicmaps.core.TopicMapIF;
import net.ontopia.topicmaps.core.index.ClassInstanceIndexIF;
import net.ontopia.topicmaps.impl.basic.InMemoryTopicMapStore;

public class ClassInstanceTest
// extends TestCase
{
    
    
    protected ClassInstanceIndexIF index;
    protected TopicMapBuilderIF builder;
    protected TopicMapIF topicmap;

    public ClassInstanceTest(String name) {
        // super(name);
    }

    protected void setUp() {
        this.topicmap = makeTopicMap();
        this.index = ((ClassInstanceIndexIF) this.topicmap
                .getIndex("net.ontopia.topicmaps.core.index.ClassInstanceIndexIF"));
    }

    protected TopicMapIF makeTopicMap() {
        InMemoryTopicMapStore store = new InMemoryTopicMapStore();
        this.builder = store.getTopicMap().getBuilder();
        return store.getTopicMap();
    }

    public void testAssociationRoles() {
        // assertTrue("index finds role types in empty topic map",
        // this.index.getAssociationRoleTypes().size() == 0);

        TopicIF at = this.builder.makeTopic();
        TopicIF art1 = this.builder.makeTopic();
        TopicIF art2 = this.builder.makeTopic();
        TopicIF art3 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();
        TopicIF t2 = this.builder.makeTopic();

        AssociationIF a1 = this.builder.makeAssociation(at);
        AssociationRoleIF r1 = this.builder.makeAssociationRole(a1, art1, t1);
        AssociationRoleIF r2 = this.builder.makeAssociationRole(a1, art2, t2);

        AssociationIF a2 = this.builder.makeAssociation(at);
        AssociationRoleIF r3 = this.builder.makeAssociationRole(a2, art3, t1);
        AssociationRoleIF r4 = this.builder.makeAssociationRole(a2, art3, t2);

        // assertTrue("role type not found",
        // this.index.getAssociationRoles(art1).size() == 1);

        // assertTrue("roles not found via type",
        // ((AssociationRoleIF)this.index.getAssociationRoles(art1).iterator().next()).equals(r1));

        // assertTrue("index claims role type not used",
        // this.index.usedAsAssociationRoleType(art1));

        // assertTrue("role type not found",
        // this.index.getAssociationRoles(art2).size() == 1);

        // assertTrue("roles not found via type",
        // ((AssociationRoleIF)this.index.getAssociationRoles(art2).iterator().next()).equals(r2));

        // assertTrue("index claims role type not used",
        // this.index.usedAsAssociationRoleType(art2));

        // assertTrue("spurious association roles found",
        // this.index.getAssociationRoles(t1).size() == 0);

        // assertTrue("index claims ordinary topic used as role type",
        // !this.index.usedAsAssociationRoleType(t1));

        // assertTrue("roles with art3 types not found",
        // this.index.getAssociationRoles(art3).size() == 2);

        // assertTrue("index claims art3 not used as role type",
        // this.index.usedAsAssociationRoleType(art3));

        // assertTrue("index loses or invents role types",
        // this.index.getAssociationRoleTypes().size() == 3);

        // assertTrue("index forgets that topic is used as a type",
        // this.index.usedAsType(art1));

        AssociationIF a3 = this.builder.makeAssociation(at);
        AssociationRoleIF r5 = this.builder.makeAssociationRole(a3, art1, t1);
        AssociationRoleIF r6 = this.builder.makeAssociationRole(a3, art2, t2);

        // assertTrue("role type not found",
        // this.index.getAssociationRoles(art1).size() == 2);

        // assertTrue("roles not found via type",
        // this.index.getAssociationRoles(art1).contains(r5));

        // assertTrue("duplicate role types not suppressed",
        // this.index.getAssociationRoleTypes().size() == 3);
    }

    public void testAssociations() {
        // assertTrue("index finds association types in empty topic map",
        // this.index.getAssociationTypes().size() == 0);

        TopicIF at1 = this.builder.makeTopic();
        TopicIF at2 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();
        TopicIF t2 = this.builder.makeTopic();

        AssociationIF a1 = this.builder.makeAssociation(at1);
        AssociationRoleIF r1 = this.builder.makeAssociationRole(a1,
                this.builder.makeTopic(), t1);
        AssociationRoleIF r2 = this.builder.makeAssociationRole(a1,
                this.builder.makeTopic(), t2);

        AssociationIF a2 = this.builder.makeAssociation(at2);
        AssociationRoleIF r3 = this.builder.makeAssociationRole(a2,
                this.builder.makeTopic(), t1);
        AssociationRoleIF r4 = this.builder.makeAssociationRole(a2,
                this.builder.makeTopic(), t2);

        // assertTrue("association type not found",
        // this.index.getAssociations(at1).size() == 1);

        // assertTrue("associations not found via type",
        // ((AssociationIF)this.index.getAssociations(at1).iterator().next()).equals(a1));

        // assertTrue("index claims association type not used",
        // this.index.usedAsAssociationType(at1));

        // assertTrue("spurious association types found",
        // this.index.getAssociations(t1).size() == 0);

        // assertTrue("index claims ordinary topic used as association type",
        // !this.index.usedAsAssociationType(t1));

        // assertTrue("associations with at2 type not found",
        // this.index.getAssociations(at2).size() == 1);

        // assertTrue("associations not found via at2 type",
        // ((AssociationIF)this.index.getAssociations(at2).iterator().next()).equals(a2));

        // assertTrue("index claims at2 not used as association type",
        // this.index.usedAsAssociationType(at2));

        // assertTrue("index loses or invents association types",
        // this.index.getAssociationTypes().size() == 2);

        // assertTrue("index forgets that topic is used as a type",
        // this.index.usedAsType(at1));

        AssociationIF a3 = this.builder.makeAssociation(at1);

        // assertTrue("association type not found",
        // this.index.getAssociations(at1).size() == 2);

        // assertTrue("associations not found via type",
        // this.index.getAssociations(at1).contains(a3));

        // assertTrue("duplicate association types not suppressed",
        // this.index.getAssociationTypes().size() == 2);
    }

    public void testOccurrences() {
        // assertTrue("index finds occurrence types in empty topic map",
        // this.index.getOccurrenceTypes().size() == 0);

        TopicIF ot1 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();

        OccurrenceIF o1 = this.builder.makeOccurrence(t1, ot1, "");

        // assertTrue("occurrence type not found",
        // this.index.getOccurrences(ot1).size() == 1);

        // assertTrue("occurrence not found via type",
        // ((OccurrenceIF)this.index.getOccurrences(ot1).iterator().next()).equals(o1));

        // assertTrue("index claims occurrence type not used",
        // this.index.usedAsOccurrenceType(ot1));

        // assertTrue("spurious occurrence types found",
        // this.index.getOccurrences(t1).size() == 0);

        // assertTrue("index claims ordinary topic used as occurrence type",
        // !this.index.usedAsOccurrenceType(t1));

        // assertTrue("index forgets that topic is used as a type",
        // this.index.usedAsType(ot1));

        OccurrenceIF o3 = this.builder.makeOccurrence(t1, ot1, "");

        // assertTrue("occurrence type not found",
        // this.index.getOccurrences(ot1).size() == 2);

        // assertTrue("occurrence not found via type",
        // this.index.getOccurrences(ot1).contains(o3));

        // assertTrue("duplicate occurrence types not suppressed",
        // this.index.getOccurrenceTypes().size() == 1);
    }

    public void testTopics() {
        // assertTrue("index finds spurious topic types",
        // this.index.getTopics(null).size() == 0);

        // assertTrue("null used as topic type in empty topic map",
        // !this.index.usedAsTopicType(null));

        // assertTrue("index finds topic types in empty topic map",
        // this.index.getTopicTypes().size() == 0);

        TopicIF tt1 = this.builder.makeTopic();
        TopicIF tt2 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();
        TopicIF t2 = this.builder.makeTopic();
        t1.addType(tt1);
        t1.addType(tt2);

        // assertTrue("topic type not found", this.index.getTopics(tt1).size()
        // == 1);

        // assertTrue("topic not found via type",
        // ((TopicIF)this.index.getTopics(tt1).iterator().next()).equals(t1));

        // assertTrue("index claims topic type not used",
        // this.index.usedAsTopicType(tt1));

        // assertTrue("topic type not found", this.index.getTopics(tt2).size()
        // == 1);

        // assertTrue("topic not found via type",
        // ((TopicIF)this.index.getTopics(tt2).iterator().next()).equals(t1));

        // assertTrue("index claims topic type not used",
        // this.index.usedAsTopicType(tt2));

        // assertTrue("spurious topic types found",
        // this.index.getTopics(t1).size() == 0);

        // assertTrue("index claims ordinary topic used as topic type",
        // !this.index.usedAsTopicType(t1));

        // assertTrue("topic with null type found",
        // this.index.getTopics(null).size() == 3);

        // assertTrue("index claims null used as topic type",
        // this.index.usedAsTopicType(null));

        // assertTrue("index loses or invents topic types",
        // this.index.getTopicTypes().size() == 2);

        // assertTrue("index forgets that topic is used as a type",
        // this.index.usedAsType(tt1));

        // assertTrue("index forgets that topic is used as a type",
        // this.index.usedAsType(tt2));

        TopicIF t3 = this.builder.makeTopic();
        t3.addType(tt1);

        // assertTrue("topic type not found", this.index.getTopics(tt1).size()
        // == 2);

        // assertTrue("topic not found via type",
        // this.index.getTopics(tt1).contains(t3));

        // assertTrue("duplicate topic types not suppressed",
        // this.index.getTopicTypes().size() == 2);
    }

    public void testTopicsDynamic() {
        TopicIF tt1 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();
        t1.addType(tt1);
        // assertTrue("TopicMapIF.addTopic does not update index",
        // this.index.getTopics(tt1).size() == 1);

        t1.removeType(tt1);
        // assertTrue("TopicIF.removeType does not update index",
        // this.index.getTopics(tt1).size() == 0);

        t1.addType(tt1);
        // assertTrue("TopicIF.addType does not update index",
        // this.index.getTopics(tt1).size() == 1);

        t1.remove();
        // assertTrue("TopicMapIF.removeTopic does not update index",
        // this.index.getTopics(tt1).size() == 0);
    }

    public void testAssociationsDynamic() {
        TopicIF at1 = this.builder.makeTopic();
        TopicIF at2 = this.builder.makeTopic();
        AssociationIF a = this.builder.makeAssociation(at1);
        // assertTrue("TopicMapIF.addAssociation does not update index",
        // this.index.getAssociations(at1).size() == 1);

        a.setType(at2);
        // assertTrue("AssociationIF.setType(at2) does not update index",
        // this.index.getAssociations(at1).size() == 0);

        a.setType(at1);
        // assertTrue("AssociationIF.addType does not update index",
        // this.index.getAssociations(at1).size() == 1);

        a.remove();
        // assertTrue("TopicMapIF.removeAssociation does not update index",
        // this.index.getAssociations(at1).size() == 0);
    }

    public void testAssociationRolesDynamic() {
        TopicIF at1 = this.builder.makeTopic();
        TopicIF art1 = this.builder.makeTopic();
        TopicIF art2 = this.builder.makeTopic();
        TopicIF player = this.builder.makeTopic();
        AssociationIF a = this.builder.makeAssociation(at1);
        AssociationRoleIF r1 = this.builder
                .makeAssociationRole(a, art1, player);
        // assertTrue("TopicMapIF.addAssociation does not update role type index",
        // this.index.getAssociationRoles(art1).size() == 1);

        r1.setType(art2);
        // assertTrue("AssociationRoleIF.setType(art2) does not update index",
        // this.index.getAssociationRoles(art1).size() == 0);

        // assertTrue("AssociationRoleIF.setType(art2) does not update index",
        // this.index.getAssociationRoles(art2).size() == 1);

        r1.setType(art1);
        // assertTrue("AssociationRoleIF.setType does not update index",
        // this.index.getAssociationRoles(art1).size() == 1);

        // assertTrue("AssociationRoleIF.setType does not update index",
        // this.index.getAssociationRoles(art2).size() == 0);

        a.remove();
        // assertTrue("TopicMapIF.removeAssociation does not update role type index",
        // this.index.getAssociationRoles(art1).size() == 0);
    }

    public void testOccurrencesDynamic() {
        TopicIF ot1 = this.builder.makeTopic();
        TopicIF ot2 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();
        OccurrenceIF o1 = this.builder.makeOccurrence(t1, ot1, "");
        // assertTrue("OccurrenceIF.setType does not update index",
        // this.index.getOccurrences(ot1).size() == 1);

        o1.setType(ot2);
        // assertTrue("OccurrenceIF.setType(ot2) does not update index",
        // this.index.getOccurrences(ot1).size() == 0);

        OccurrenceIF o2 = this.builder.makeOccurrence(t1, ot1, "");
        // assertTrue("TopicIF.addOccurrence does not update index",
        // this.index.getOccurrences(ot1).size() == 1);

        t1.remove();
        // assertTrue("TopicMapIF.removeTopic does not update occurrence index",
        // this.index.getOccurrences(ot1).size() == 0);
    }

    public void testConcurrentModification() {
        TopicIF ot1 = this.builder.makeTopic();
        TopicIF ot2 = this.builder.makeTopic();
        TopicIF t1 = this.builder.makeTopic();
        OccurrenceIF occ = this.builder.makeOccurrence(t1, ot1, "");
        try {
            Iterator it = this.index.getOccurrences(ot1).iterator();
            occ.setType(ot2);
            it.next();
        } catch (ConcurrentModificationException e) {
            // fail("ClassInstanceIndex returns live collections");
        }
    }
    public static void main(String[] args){}
    
}
