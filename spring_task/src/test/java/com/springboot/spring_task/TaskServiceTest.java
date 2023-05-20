package com.springboot.spring_task;

import com.springboot.spring_task.dto.TasksDTO;
import com.springboot.spring_task.entity.Kinds;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.repository.TaskRepository;
import com.springboot.spring_task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    private TaskService taskService;
    Tasks task;

    @BeforeEach
    public void init() throws ParseException {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository);
        Kinds kind = new Kinds();
        kind.setId(1L);
        kind.setDescription("kind1");

        Date datePlan = new SimpleDateFormat("yyyy-MM-dd").parse("2023-05-20");
        task = new Tasks();
        task.setId(1L);
        task.setKind(kind);
        task.setName("name");
        task.setPlanDate(datePlan);
    }
    @Test
    public void should_findTask_byId() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        TasksDTO result = taskService.find(1L);
        assertEquals(1L, result.getId());
        assertEquals("name", result.getName());
    }

    @Test
    public void should_findAll_tasks() throws ParseException {
        List<Tasks> mockList = Arrays.asList(task);
        Pageable paging = PageRequest.of(1,10);
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse("2023-04-13");
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-13");

        Page<Tasks> pagedResponse = new PageImpl(mockList);
        when(taskRepository.findTasksByPlanDate(dateStart,dateEnd,paging)).thenReturn(pagedResponse);

        List<TasksDTO> taskList = taskService.findAll(dateStart,dateEnd,1,10);

        assertEquals(1,taskList.size());
        assertEquals("name",taskList.get(0).getName());
        assertEquals("kind1",taskList.get(0).getKindsDescription());
    }
}
