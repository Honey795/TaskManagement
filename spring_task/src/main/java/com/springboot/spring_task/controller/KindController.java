package com.springboot.spring_task.controller;

import com.springboot.spring_task.entity.Kinds;
import com.springboot.spring_task.service.KindService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Kinds", description="Тип задачи")
@RestController
@RequestMapping("/kinds")
public class KindController {
    private final KindService kindService;

    @Autowired
    public KindController(KindService kindService) {
        this.kindService = kindService;
    }

    @Operation(
            summary = "Получение списка типов задач",
            description = "Позволяет получить список типов задач"
    )
    @GetMapping
    public List<Kinds> findAllKinds(){
        return kindService.findAll();
    }
}
