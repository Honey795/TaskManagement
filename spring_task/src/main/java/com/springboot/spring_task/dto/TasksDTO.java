package com.springboot.spring_task.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TasksDTO {
    private Long id;

    private String kindsDescription;
    //наименование
    private String name;
    //описание
    private String description;

    private Date planDate;
    //тег
    private String tag;
}
