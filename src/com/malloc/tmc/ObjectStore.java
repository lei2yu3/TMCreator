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

        // // 将下面的元素添加到第1个位置
        // list.add(0, "5");
        // // 获取第1个元素
        // list.get(0);
        // // 删除“3”
        // list.remove("3");
        // // 获取ArrayList的大小
        // list.size();
        // // 判断list中是否包含"3"
        // list.contains(3);
        // // 设置第2个元素为10
        // list.set(1, "10");
        //
        // // 将ArrayList转换为数组
        // String[] arr = (String[]) list.toArray(new String[0]);
        // for (String str : arr)
        // System.out.println("str: " + str);
        //
        // // 清空ArrayList
        // list.clear();
    }
}
