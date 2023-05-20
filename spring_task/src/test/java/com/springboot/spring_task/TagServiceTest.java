package com.springboot.spring_task;

import com.springboot.spring_task.entity.Kinds;
import com.springboot.spring_task.entity.Tags;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.repository.TagRepository;
import com.springboot.spring_task.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    private TagService tagService;
    Tags tag;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagService(tagRepository);

        tag = new Tags();
        tag.setCode("tag1");
        tag.setDescription("desc1");
    }

    @Test
    public void should_findByNameTag_withTaskOrderByPriority() {
        Kinds kind1 = new Kinds();
        kind1.setId(1L);
        kind1.setPriority(2L);
        kind1.setDescription("kind1");
        Tasks task1 = new Tasks();
        task1.setId(1L);
        task1.setKind(kind1);
        task1.setName("name1");

        Kinds kind2 = new Kinds();
        kind2.setId(2L);
        kind2.setPriority(1L);
        kind2.setDescription("kind2");
        Tasks task2 = new Tasks();
        task2.setId(2L);
        task2.setKind(kind2);
        task2.setName("name2");

        List<Tasks> taskList = Arrays.asList(task1,task2);
        tag.setTask(taskList);
        when(tagRepository.findByName("tag1")).thenReturn(tag);
        Tags result = tagService.find("tag1");
        assertEquals("name2", result.getTask().get(0).getName());
    }
}
