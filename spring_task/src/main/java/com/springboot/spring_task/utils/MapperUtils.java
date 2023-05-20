package com.springboot.spring_task.utils;

import com.springboot.spring_task.dto.TasksDTO;
import com.springboot.spring_task.entity.Tasks;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {
    private static final ModelMapper modelMapper;
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //сопоставление Task
        TypeMap<Tasks, TasksDTO> propertyMapper = modelMapper.createTypeMap(Tasks.class, TasksDTO.class);
        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getKind().getDescription(), TasksDTO::setKindsDescription)
        );
    }
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> dtoCLass) {
        return entityList.stream()
                .map(entity -> map(entity, dtoCLass))
                .collect(Collectors.toList());
    }
}
