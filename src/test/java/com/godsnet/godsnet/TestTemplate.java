package com.godsnet.godsnet;

import com.godsnet.godsnet.exception.GodsException;
import com.godsnet.godsnet.freemark.FreemarkerCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTemplate {

    @Test
    public void test() throws GodsException{
        String tableName = "t_host_logon";
        Map<String,String> map = FreemarkerCreator.createEntity(tableName);
    }
}
