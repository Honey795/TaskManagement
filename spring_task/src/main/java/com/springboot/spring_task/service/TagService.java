package com.springboot.spring_task.service;

import com.springboot.spring_task.dto.TagsDTO;
import com.springboot.spring_task.repository.TagRepository;
import com.springboot.spring_task.entity.Tags;
import com.springboot.spring_task.entity.Tasks;
import com.springboot.spring_task.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Получает тэг по заданному Id
     */
    @Transactional
    public Tags find(String name) {
        Tags tags = null;
        tags = tagRepository.findByName(name);

        if (tags != null) {
            Collections.sort(tags.getTask(), new Comparator<Tasks>() {
                @Override
                public int compare(Tasks t1, Tasks t2) {
                    return t1.getKind().getPriority().compareTo(t2.getKind().getPriority());
                }
            });
        }
        return tags;
    }

    @Transactional
    public void saveOrUpdate(Tags model) {
        tagRepository.save(model);
    }

    @Transactional
    public void delete(String code) {
        Tags tags = tagRepository.findByName(code);
        tagRepository.delete(tags);
    }

    /**
     * Получает список тэгов
     */
    public List<TagsDTO> findAll() {
        List<Tags> tags = tagRepository.findAllByTasksIsNotNull().stream().distinct().collect(Collectors.toList());
        return MapperUtils.mapAll(tags, TagsDTO.class);
    }
}
