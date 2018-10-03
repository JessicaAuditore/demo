package com.soul.springboot;

import com.soul.springboot.entity.Customer;
import com.soul.springboot.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Autowired
    CustomerService customerService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void contextLoads() {
        Customer customerList=customerService.findById(1);
//        Cache cache=cacheManager.getCache("dept");
//        cache.put("cus:"+customerList.getCustomer_id(),customerList);
        System.out.println(customerList);
    }

    @Test
    public void a(){
        List<Customer> customerList=customerService.findAll();
        List<Customer> customerList1=customerService.findAll();
        if(customerList.equals(customerList1)){
            System.out.println("159151465435461515");
        }
    }

}
