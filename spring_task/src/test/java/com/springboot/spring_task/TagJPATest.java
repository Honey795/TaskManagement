package com.springboot.spring_task;

import com.springboot.spring_task.entity.Kinds;
import com.springboot.spring_task.entity.Tags;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.repository.TagRepository;
import com.springboot.spring_task.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TagJPATest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void should_save_tag() {
        Tags tag = tagRepository.save(new Tags("desc1", "code1"));

        assertThat(tag).hasFieldOrPropertyWithValue("description", "desc1");
        assertThat(tag).hasFieldOrPropertyWithValue("code", "code1");
    }

    @Test
    public void should_findTag_byName() {
        Tags tag1 = new Tags("desc1", "code1");
        entityManager.persist(tag1);

        Tags tag2 = new Tags("desc2", "code2");
        entityManager.persist(tag2);

        Tags tag = tagRepository.findByName(tag2.getCode());

        assertThat(tag).isEqualTo(tag2);
    }

    @Test
    public void should_findAll_tags() {
        Tags tag1 = new Tags("desc1", "code1");
        entityManager.persist(tag1);

        Tags tag2 = new Tags("desc2", "code2");
        entityManager.persist(tag2);

        Tags tag3 = new Tags("desc3", "code3");
        entityManager.persist(tag3);

        Iterable iterable = tagRepository.findAll();

        assertThat(iterable).contains(tag2, tag2, tag3);
    }

    @Test
    public void should_findTags_isNotNullTask() {
        Tags tags = new Tags("desc1", "code1");
        entityManager.persist(tags);
        Kinds kind = new Kinds();
        kind.setId(3L);
        Tasks task = new Tasks(kind,"name1","desc11",new Date(),"code1");
        taskRepository.save(task);

        Iterable iterable = tagRepository.findAllByTasksIsNotNull();

        assertThat(iterable).contains(tags);
    }

    @Test
    public void should_deleteTag_withTasks() {
        Tags tags1 = new Tags("desc1", "code1");
        entityManager.persist(tags1);

        Kinds kind = new Kinds();
        kind.setId(3L);
        Tasks task = new Tasks(kind,"name1","desc11",new Date(),"code1");
        taskRepository.save(task);

        tagRepository.delete(tags1);
        Iterable iterable = tagRepository.findAllByTasksIsNotNull();

        assertThat(iterable).doesNotContain(tags1);
    }
}
