package com.bnt.plan.repository;

import com.bnt.plan.dataobject.ListTaskDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * ListTaskRepository
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/12/19 11:21 bnt
 * @history
 */
public interface ListTaskRepository extends MongoRepository<ListTaskDO, Integer> {
    ListTaskDO findByTaskName(String taskName);

    Page<ListTaskDO> findByTaskNameLike(String taskName, Pageable pageable);
}
