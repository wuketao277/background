package com.hello.background.repository;

import com.hello.background.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuketao
 * @date 2019/11/30
 * @Description
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
