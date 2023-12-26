package com.bnt.plan;

import com.bnt.plan.dataobject.ListTaskDO;
import com.bnt.plan.repository.ListTaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/12/19 11:27 bnt
 * @history
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlanListCoreApplication.class)
public class ListTaskRepositoryTest {
    @Autowired
    private ListTaskRepository listTaskRepository;

    @Test // 插入一条记录
    public void testInsert() {
        ListTaskDO listTaskDO = new ListTaskDO();
        listTaskDO.setId(3);
        listTaskDO.setTaskName("任务san");
        listTaskDO.setTaskDesc("任务描述测试");
        listTaskDO.setTaskDate(LocalDateTime.now());
        listTaskDO.setTaskPriority(0);
        listTaskDO.setTaskTag("");
        listTaskDO.setTaskStatus("0");
        listTaskDO.setTaskMember("");
        listTaskDO.setParentId(2);
        listTaskRepository.insert(listTaskDO);
    }

    @Test
    public void testUpdate() {
        Optional<ListTaskDO> byId = listTaskRepository.findById(1);
        Assert.assertTrue(byId.isPresent());
        ListTaskDO listTaskDO = byId.get();
        listTaskDO.setTaskName("任务一修改");
        // 如果使用 save 方法来更新的话，必须是全量字段，否则其它字段会被覆盖
        listTaskRepository.save(listTaskDO);
    }

    @Test
    public void testDelete() {
        listTaskRepository.deleteById(1);
    }

    @Test
    public void testFindById() {
        Optional<ListTaskDO> byId = listTaskRepository.findById(1);
        System.out.println(byId.isPresent());
    }

    @Test
    public void testSelectByIds() {
        Iterable<ListTaskDO> allById = listTaskRepository.findAllById(Arrays.asList(1, 2, 3));
        allById.forEach(System.out::println);
    }

    @Test
    public void testFindByName() {
        ListTaskDO byTaskName = listTaskRepository.findByTaskName("任务一");
        System.out.println(byTaskName);
    }

    @Test
    public void testFindByNameLike() {
        // 创建排序条件
        Sort sort = Sort.by(Sort.Direction.DESC, "id"); // ID 倒序
        PageRequest of = PageRequest.of(0, 10, sort);
        Page<ListTaskDO> page = listTaskRepository.findByTaskNameLike("任务", of);
        // 打印
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());

    }
}
