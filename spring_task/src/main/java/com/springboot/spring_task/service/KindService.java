package com.springboot.spring_task.service;

import com.springboot.spring_task.repository.KindRepository;
import com.springboot.spring_task.entity.Kinds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
public class KindService {
    private final KindRepository kindRepository;

    @Autowired
    public KindService(KindRepository kindRepository) {
        this.kindRepository = kindRepository;
    }

    @Cacheable("kindCashe")
    public List<Kinds> findAll(){
        return kindRepository.findAll();
    }
}
