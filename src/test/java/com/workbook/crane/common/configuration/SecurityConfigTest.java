package com.workbook.crane.common.configuration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(profiles = {"local"})
@RunWith(SpringRunner.class)
public class SecurityConfigTest {

//  @Test
//  public void test() {
//    System.out.println(StringUtils.leftPad("1", 3, "0"));
//  }


   @Test
  public void test2(){
     Set<String> set = new HashSet<>();
     set.add("1");

     set.remove("2");
     System.out.println(set);

     set.remove("1");
     System.out.println(set);

   }
}