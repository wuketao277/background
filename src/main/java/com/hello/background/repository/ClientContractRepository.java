package com.hello.background.repository;

import com.hello.background.domain.ClientContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户合同仓库
 *
 * @author wuketao
 * @date 2019/12/29
 * @Description
 */
@Repository
public interface ClientContractRepository extends JpaRepository<ClientContract, Integer> {
    List<ClientContract> findByClientIdOrderByExpireDate(Integer clientId);
}
