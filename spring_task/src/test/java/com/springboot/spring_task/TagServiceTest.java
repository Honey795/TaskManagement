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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

        tag = new Tags("tag1","desc1");
    }

    @Test
    public void should_findByNameTag_withTaskOrderByPriority() throws ParseException {
        Kinds kind1 = new Kinds(1L,"kind1",2L);
        Kinds kind2 = new Kinds(2L,"kind2",1L);

        Date datePlan = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-20");
        Tasks task1 = new Tasks(kind1,"name1","desc1",datePlan,"tag1");
        task1.setId(1L);

        Tasks task2 = new Tasks(kind2,"name2","desc2",datePlan,"tag1");
        task2.setId(2L);

        List<Tasks> taskList = Arrays.asList(task1,task2);
        tag.setTask(taskList);
        when(tagRepository.findByName("tag1")).thenReturn(tag);
        Tags result = tagService.find("tag1");
        assertEquals("name2", result.getTask().get(0).getName());
    }
}
