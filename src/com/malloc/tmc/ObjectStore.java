package com.malloc.tmc;

import java.util.ArrayList;

//import java.util.Iterator;

public class ObjectStore {

    static ArrayList<ResourceType> OSArray = new ArrayList<ResourceType>();

    public static void main(String[] args) {
        ResourceType rt = new ResourceType();
        OSArray.add(rt);
        System.out.println("RTArray size = " + OSArray.size());
        for (ResourceType a : OSArray) {
            a.printRType();
        }

        // // �������Ԫ����ӵ���1��λ��
        // list.add(0, "5");
        // // ��ȡ��1��Ԫ��
        // list.get(0);
        // // ɾ����3��
        // list.remove("3");
        // // ��ȡArrayList�Ĵ�С
        // list.size();
        // // �ж�list���Ƿ����"3"
        // list.contains(3);
        // // ���õ�2��Ԫ��Ϊ10
        // list.set(1, "10");
        //
        // // ��ArrayListת��Ϊ����
        // String[] arr = (String[]) list.toArray(new String[0]);
        // for (String str : arr)
        // System.out.println("str: " + str);
        //
        // // ���ArrayList
        // list.clear();
    }
}
