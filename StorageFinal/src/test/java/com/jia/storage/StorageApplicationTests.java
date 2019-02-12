package com.jia.storage;

import com.alibaba.fastjson.JSON;
import com.jia.storage.entity.Inventory;
import com.jia.storage.entity.Record;
import com.jia.storage.entity.Users;
import com.jia.storage.mapper.InventoryMapper;
import com.jia.storage.mapper.RecordMapper;
import com.jia.storage.mapper.TenantMapper;
import com.jia.storage.mapper.UsersMapper;
import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageApplicationTests {


    private static final Logger log = LoggerFactory.getLogger(StorageApplicationTests.class);
    @Autowired
    UsersMapper usersMapper;
    
    @Autowired
    TenantMapper tenantMapper;

    @Test
    public void contextLoads()  {
        int i = usersMapper.selectRowsNum();
        System.out.println(i);
    }

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Test
    public void testMail() throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("注册成功！");
        helper.setText("this is your applicaiton");
        helper.setTo("15619279129@163.com");
        helper.setFrom("2294161634@qq.com");

        javaMailSender.send(mimeMessage);

    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitMQ(){

        Users user = new Users();

        user.setUsername("123456");
        user.setPassword("123456");
        rabbitTemplate.convertAndSend("exchange.direct","send", user);


    }

    @RabbitListener(queues = "send")
    public void receive(Message message){

        System.out.println("收到消息");
        System.out.println(message);

        String str = new String(message.getBody());
        User user = JSON.parseObject(str, User.class);
        System.out.println(user);
    }


    @Test
    public void testLog(){
        log.info("this is first !");
    }

    @Autowired
    InventoryMapper inventoryMapper;

    @Test
    public void testInventoryMapper(){
        HashMap<String, Object> map = new HashMap();

        map.put("undertaker", 1);
        List<Inventory> inventories = inventoryMapper.selectByMap(map);

        for (Inventory i : inventories) {
            System.out.println(i);
        }

        Inventory inventory = inventoryMapper.selectByPrimaryKey(14L);

        System.out.println("-----------------");
        System.out.println(inventory);

    }

    @Autowired
    RecordMapper recordMapper;
    @Test
    public void testRecordMapper(){
        HashMap<String, Object> map = new HashMap();

        map.put("inventoryId", 18);
        List<Record> records = recordMapper.selectByMap(map);

        for (Record i : records) {
            System.out.println(i);
        }

        Record record = recordMapper.selectByPrimaryKey(30L);

        System.out.println("-----------------");
        System.out.println(record);

    }

    @Test
    public void testUserMapper(){
        HashMap<String, Object> map = new HashMap();

        map.put("inventoryId", 18);
        List<Users> users = usersMapper.selectByMap(map);

        for (Users i : users) {
            System.out.println(i);
        }

        Users users1 = usersMapper.selectByPrimaryKey(1L);

        System.out.println("-----------------");
        System.out.println(users1);

    }
}
