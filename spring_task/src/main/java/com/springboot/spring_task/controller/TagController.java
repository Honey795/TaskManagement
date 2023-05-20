package com.springboot.spring_task.controller;

import com.springboot.spring_task.dto.TagsDTO;
import com.springboot.spring_task.entity.Tags;
import com.springboot.spring_task.exception_handing.NotFoundException;
import com.springboot.spring_task.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(name="Tags", description="Тэги")
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @Operation(
            summary = "Поиск тэга",
            description = "Позволяет получить тэг по заданному Id"
    )
    @GetMapping("/{tag_name}")
    public ResponseEntity<Tags> find(@PathVariable String tag_name){
        Tags tags = tagService.find(tag_name);
        if(tags == null){
            throw new NotFoundException("Не найдено тэгов с Code = " + tag_name);
        }
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @Operation(
            summary = "Список тэгов",
            description = "Позволяет получить список тэгов"
    )
    @GetMapping
    public List<TagsDTO> findAllTag(){
        return tagService.findAll();
    }

    @Operation(
            summary = "Создание тэга",
            description = "Позволяет создать новый тэг"
    )
    @PostMapping
    public ResponseEntity<String> addTag(@Valid @RequestBody Tags tags){
        Tags checker = tagService.find(tags.getCode());
        if(checker != null){
            throw new NotFoundException("Такая запись уже существует");
        }
        tagService.saveOrUpdate(tags);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Изменение тэга",
            description = "Позволяет изменить тэг"
    )
    @PutMapping
    public ResponseEntity<String> updateTag(@Valid @RequestBody Tags tags){
        tagService.saveOrUpdate(tags);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Удаление тэга",
            description = "Позволяет удалить тэг по заданному Id"
    )
    @DeleteMapping("/{tag_code}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable @Min(0) String tag_code){
        tagService.delete(tag_code);
    }
}
