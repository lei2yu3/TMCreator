package com.malloc.temp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileContainer {

    public static void main(String[] args) {

        // ����ArrayList
        ArrayList<String> list = new ArrayList<String>();

        // ������
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        // �������Ԫ����ӵ���1��λ��
        list.add(0, "5");

        // ��ȡ��1��Ԫ��
        System.out.println("the first element is: " + list.get(0));
        // ɾ����3��
        list.remove("3");
        // ��ȡArrayList�Ĵ�С
        System.out.println("Arraylist size=: " + list.size());
        // �ж�list���Ƿ����"3"
        System.out.println("ArrayList contains 3 is: " + list.contains(3));
        // ���õ�2��Ԫ��Ϊ10
        list.set(1, "10");

        // ͨ��Iterator����ArrayList
        for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
            System.out.println("next is: " + iter.next());
        }

        // ��ArrayListת��Ϊ����
        String[] arr = (String[]) list.toArray(new String[0]);
        for (String str : arr)
            System.out.println("str: " + str);

        // ���ArrayList
        list.clear();
        // �ж�ArrayList�Ƿ�Ϊ��
        System.out.println("ArrayList is empty: " + list.isEmpty());
    }
}