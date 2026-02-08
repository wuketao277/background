package com.hello.background.repository;

import com.hello.background.domain.ClientContract;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
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
public interface ClientContractRepository extends PagingAndSortingRepository<ClientContract, Integer>, JpaSpecificationExecutor<ClientContract> {
    List<ClientContract> findByClientIdOrderByExpireDate(Integer clientId);
}
