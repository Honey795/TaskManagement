package com.springboot.spring_task;

import com.springboot.spring_task.entity.Kinds;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.repository.TaskRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TaskJPATest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TaskRepository taskRepository;
    Kinds kind;
    Date date;
    @BeforeAll
    public void createKinds() {
        kind = new Kinds();
        kind.setId(3L);
        try {
            date = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "13.05.2023" );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void should_save_task() {
        Tasks task = taskRepository.save(new Tasks(kind,"name1","desc1",date,"code1"));

        assertThat(task).hasFieldOrPropertyWithValue("name", "name1");
        assertThat(task).hasFieldOrPropertyWithValue("tag", "code1");
        assertThat(task).hasFieldOrPropertyWithValue("description", "desc1");
        assertThat(task).hasFieldOrPropertyWithValue("plan_date", date);
    }

    @Test
    public void should_findTask_byId() {
        Tasks task1 = new Tasks(kind,"name1","desc1",date,"code1");
        entityManager.persist(task1);

        Tasks task2 = new Tasks(kind,"name2","desc2",date,"code2");
        entityManager.persist(task2);

        Tasks task = taskRepository.findById(task2.getId()).get();

        assertThat(task).isEqualTo(task2);
    }

    @Test
    public void should_findAll_tasks() {
        Tasks task1 = new Tasks(kind,"name1","desc1",date,"code1");
        entityManager.persist(task1);

        Tasks task2 = new Tasks(kind,"name2","desc2",date,"code2");
        entityManager.persist(task2);

        Tasks task3 = new Tasks(kind,"name3","desc3",date,"code3");
        entityManager.persist(task3);

        Iterable iterable = taskRepository.findAll();

        assertThat(iterable).contains(task2, task2, task3);
    }

    @Test
    public void should_delete_task() {
        Tasks task = new Tasks(kind,"name1","desc11",date,"code1");
        entityManager.persist(task);

        taskRepository.delete(task);
        Iterable iterable = taskRepository.findAll();

        assertThat(iterable).doesNotContain(task);
    }
}
