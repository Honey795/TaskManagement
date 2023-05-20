package com.springboot.spring_task.service;

import com.springboot.spring_task.dto.TasksDTO;
import com.springboot.spring_task.repository.TaskRepository;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService{
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Transactional
    public TasksDTO find(Long id){
        TasksDTO taskDto = null;
        Optional<Tasks> optional = taskRepository.findById(id);

        if(optional.isPresent()){
            taskDto = MapperUtils.map( optional.get(), TasksDTO.class);
        }
        return taskDto;
    }

    @Transactional
    public void saveOrUpdate(Tasks model) {
        taskRepository.save(model);
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Получает список задач
     */
    public List<TasksDTO> findAll(Date startDate, Date endDate, int from, int size) {
        Pageable paging = PageRequest.of(from,size);
        Page<Tasks> page =  taskRepository.findTasksByPlanDate(startDate,endDate,paging);
        List<Tasks> tasks = new ArrayList<>(page.getContent());
        tasks = tasks.stream()
                .sorted((o1,o2)->o1.getKind().getPriority().compareTo(o2.getKind().getPriority()))
                .collect(Collectors.toList());
        //cоответствие полей
        List<TasksDTO> taskDto = MapperUtils.mapAll(tasks, TasksDTO.class);
        return taskDto;
    }
}
