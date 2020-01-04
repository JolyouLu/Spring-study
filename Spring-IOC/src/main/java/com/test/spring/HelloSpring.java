package com.test.spring;

/**
 * @Author: LZJ
 * @Date: 2019/12/28 20:54
 * @Version 1.0
 */
public class HelloSpring {
    private FineSpring fineSpring;

    public HelloSpring() {
        System.out.println("1111");
    }

    public HelloSpring(FineSpring fineSpring) {
        this.fineSpring = fineSpring;
    }

    public void setFineSpring(FineSpring fineSpring) {
        this.fineSpring = fineSpring;
    }

    public FineSpring getFineSpring() {
        return fineSpring;
    }

    //    private String name;
//    private int sex;
//
//    public HelloSpring() {
//    }
//
//    public HelloSpring(String name,int sex) {
//        this.name = name;
//        this.sex = sex;
//    }
//
//    public static HelloSpring build(String type){
//        if ("A".equals(type)){
//            return new HelloSpring("testA",1);
//        }else if ("B".equals(type)){
//            return new HelloSpring("testB",0);
//        }else {
//            throw new IllegalArgumentException("type must A or B");
//        }
//    }

}
