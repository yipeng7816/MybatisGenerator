package com.yp.springdatabase.controller;

import com.yp.springdatabase.bean.BasicUser;
import com.yp.springdatabase.service.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping("/add")
    public Object add() {
        for (int i = 0; i < 10; i++) {
            BasicUser user = new BasicUser();
            user.setId(Long.valueOf(i));
            user.setAge(i+2);
            user.setName("笨笨"+i);
            user.setPhone(1865485);
            user.setSex("男");
            orderRepository.save(user);
        }
        for (int i = 10; i < 20; i++) {
            BasicUser user = new BasicUser();
            user.setId(Long.valueOf(i));
            user.setAge(i+2);
            user.setName("笨笨"+i);
            user.setPhone(1865485);
            user.setSex("男");
            orderRepository.save(user);
        }
        return "success";
    }

    @RequestMapping("query")
    private Object queryAll() {
        return orderRepository.findAll();
    }
}
