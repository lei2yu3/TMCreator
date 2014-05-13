package com.malloc.temp;

public class StringSplit {

    public static void main(String[] args){
        String[] ss = new String[50];
        
//        ss = "2|33|4".split("\\|");
//        for (int i = 0; i < ss.length; i++)
//            System.out.println(ss[i]);
//        
//        ss = ":ab:cd:ef::".split(":");      //末尾分隔符全部忽略 
//        for (int i = 0; i < ss.length; i++)
//            System.out.println(ss[i]);
//        
//        ss = ":ab:cd:ef::".split(":",-1); //不忽略任何一个分隔符
//        for (int i = 0; i < ss.length; i++)
//            System.out.println(ss[i]);
//        
//        
        ss = "WWE-Z-20140515-45mb-rmvb".split("-", 5); //不忽略任何一个分隔符
        for (int i = 0; i < ss.length; i++)
            System.out.print(ss[i] + " | ");
        //System.out.println(StringUtils.split(":ab:cd:ef::",":").length);//最前面的和末尾的分隔符全部都忽略,apache commons  

    }
}
/**
 * 
1、如果用"."或"|"为分隔的话，必须得加"\\"，如：String.split("\\.")或String.split("\\|")；
2、如果在一个字符串中有多个分隔符，可以用“|”作为连字符，比如：“a=1 and b=2 or c=3 and d=4”,把四个都分隔出来，可以用String.split("and|or");
 */
