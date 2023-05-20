package com.springboot.spring_task.service;

import com.springboot.spring_task.configuration.FileStorage;
import com.springboot.spring_task.exception_handing.NotFoundException;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileStorageService {
    private final Path fileLocation;

    public FileStorageService(FileStorage fileStorage) {
        this.fileLocation = Paths.get(fileStorage.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileLocation); //создание директории для задачи
        }
        catch (Exception ex){
            throw new NotFoundException("Не удалось создать каталог");
        }
    }
    /**
     * Сохраняет в директорию файл по заданному Id
     */
    public void storeFile(Long idTask, MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.fileLocation.resolve(idTask.toString()); //директория задачи
            if(!Files.exists(targetLocation))
                Files.createDirectories(targetLocation);
            targetLocation = targetLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex){
            throw new NotFoundException("Не удалось сохранить файл");
        }
    }
    /**
     * Считывает с директории файл по заданному Id
     */
    public Resource downloadFile(Long idTask, String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(idTask.toString() + "/" +fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        } catch (MalformedURLException e) {
            throw new NotFoundException("Не удалось получить файл");
        }
    }
    /**
     * Считывает с директории zip-архив по заданному Id
     */
    public void downloadFiles(Long idTask, List<String> names, HttpServletResponse response) {
        Path targetLocation = this.fileLocation.resolve(idTask.toString()); //директория задачи
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())){
            for (String fileName : names) {
                FileSystemResource resource = new FileSystemResource(targetLocation.resolve(fileName));
                ZipEntry zipEntry = new ZipEntry(resource.getFilename());
                zipEntry.setSize(resource.contentLength());
                zipOut.putNextEntry(zipEntry);
                StreamUtils.copy(resource.getInputStream(), zipOut); //формирование zip
                zipOut.closeEntry();
            }
        }
        catch (IOException ex) {
            throw new NotFoundException("Не удалось считать файл");
        }
    }
}
