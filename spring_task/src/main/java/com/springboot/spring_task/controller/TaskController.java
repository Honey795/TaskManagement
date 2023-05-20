package com.springboot.spring_task.controller;

import com.springboot.spring_task.dto.TasksDTO;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.exception_handing.BadRequestException;
import com.springboot.spring_task.exception_handing.NotFoundException;
import com.springboot.spring_task.service.FileStorageService;
import com.springboot.spring_task.service.TaskService;
import com.springboot.spring_task.validator.TaskValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Tag(name="Task", description="Задачи")
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final FileStorageService fileStorageService;

    @Autowired
    public TaskController(TaskService taskService, FileStorageService fileStorageService) {
        this.taskService = taskService;
        this.fileStorageService = fileStorageService;
    }

    @Operation(
            summary = "Поиск тэга",
            description = "Позволяет получить тэг по заданному Id"
    )
    @GetMapping("/{task_id}")
    public ResponseEntity<TasksDTO> find(@PathVariable @Min(0) Long task_id){
        TasksDTO task = taskService.find(task_id);
        if(task == null){
            throw new NotFoundException("Не найдено задач с Id = " + task_id);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(
            summary = "Список задач",
            description = "Позволяет получить список задач за заданный период"
    )
    @GetMapping
    public List<TasksDTO> findAllTask(
            @RequestParam(value = "startDate", required=true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required=true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) int from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) int size){
        if(!TaskValidator.validateDates(startDate,endDate))
            throw new BadRequestException("Неверно задан период");
        return taskService.findAll(startDate,endDate,from,size);
    }

    @Operation(
            summary = "Создание задачи",
            description = "Позволяет создать новую задачу"
    )
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Tasks addTask(@Valid @RequestBody Tasks tasks){
        if(!TaskValidator.validateDate(tasks.getPlanDate()))
            throw new BadRequestException("Invalid param");
        taskService.saveOrUpdate(tasks);
        return tasks;
    }

    @Operation(
            summary = "Изменение задачи",
            description = "Позволяет внести изменения в задачу"
    )
    @PutMapping
    public ResponseEntity<String> updateTask(@Valid @RequestBody Tasks tasks){
        taskService.saveOrUpdate(tasks);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалить задачу по заданному Id"
    )
    @DeleteMapping("/{task_id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable @Min(0) Long task_id){
        taskService.delete(task_id);
    }

    @Operation(
            summary = "Получение файла",
            description = "Позволяет получить файл прикрепеленный к задаче"
    )
    @GetMapping("/{task_id}/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable @Min(0) Long task_id,
                             @PathVariable String fileName,
                             HttpServletRequest request) {
        Resource resource = fileStorageService.downloadFile(task_id, fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new BadRequestException("Не удалось получить файл");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(
            summary = "Получение файлов",
            description = "Позволяет получить несколько файлов прикрепеленных к задаче в формате zip"
    )
    @GetMapping(value ="/{task_id}/downloadFiles", produces="application/zip")
    public void downloadFiles(@PathVariable @Min(0) Long task_id,
                              @RequestParam List<String> names,
                              HttpServletResponse response) {
        fileStorageService.downloadFiles(task_id, names, response);
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=TaskFiles.zip");
    }

    @Operation(
            summary = "Загрузка файла",
            description = "Позволяет прикрепить к задаче файл-вложение по заданному Id"
    )
    @PostMapping("/{task_id}/uploadFile")
    public void uploadFile(@PathVariable @Min(0) Long task_id,
                           @RequestParam("file") MultipartFile file){
        fileStorageService.storeFile(task_id, file);
    }

    @Operation(
            summary = "Загрузка файлов",
            description = "Позволяет прикрепить к задаче несколько файлов по Id"
    )
    @PostMapping("/{task_id}/uploadFiles")
    public void uploadFiles(@PathVariable @Min(0) Long task_id,
                           @RequestParam("file") MultipartFile[] files){
        Arrays.asList(files)
                .stream().forEach(file -> fileStorageService.storeFile(task_id, file));
    }
}

