package com.gdu.app02.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {

  public static void main(String[] args) {
    
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/appCtx.xml");
    
    Person person = ctx.getBean("friend", Person.class);
    System.out.println(person.getName());
    System.out.println(person.getAddress());
    System.out.println(person.getContact());

  }

}
