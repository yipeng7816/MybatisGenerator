package com.yp.springdatabase.service;

import com.yp.springdatabase.bean.BasicUser;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<BasicUser, Long> {
}