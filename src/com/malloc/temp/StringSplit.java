package com.malloc.temp;

public class StringSplit {

    public static void main(String[] args){
        String[] ss = new String[50];
        
//        ss = "2|33|4".split("\\|");
//        for (int i = 0; i < ss.length; i++)
//            System.out.println(ss[i]);
//        
//        ss = ":ab:cd:ef::".split(":");      //ĩβ�ָ���ȫ������ 
//        for (int i = 0; i < ss.length; i++)
//            System.out.println(ss[i]);
//        
//        ss = ":ab:cd:ef::".split(":",-1); //�������κ�һ���ָ���
//        for (int i = 0; i < ss.length; i++)
//            System.out.println(ss[i]);
//        
//        
        ss = "WWE-Z-20140515-45mb-rmvb".split("-", 5); //�������κ�һ���ָ���
        for (int i = 0; i < ss.length; i++)
            System.out.print(ss[i] + " | ");
        //System.out.println(StringUtils.split(":ab:cd:ef::",":").length);//��ǰ��ĺ�ĩβ�ķָ���ȫ��������,apache commons  

    }
}
/**
 * 
1�������"."��"|"Ϊ�ָ��Ļ�������ü�"\\"���磺String.split("\\.")��String.split("\\|")��
2�������һ���ַ������ж���ָ����������á�|����Ϊ���ַ������磺��a=1 and b=2 or c=3 and d=4��,���ĸ����ָ�������������String.split("and|or");
 */
