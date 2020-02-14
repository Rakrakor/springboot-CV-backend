package com.samrak.CV.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.samrak.CV.entities.File;
import com.samrak.CV.exceptions.FilePathException;
import com.samrak.CV.repository.FileRepository;
import org.springframework.util.StringUtils;

@Transactional
@Service
public class ServiceFile {

	@Autowired
    private FileRepository fileRepository;
	
	@Autowired
	private ServiceMe serveMe;

    public File storeFile(MultipartFile multipartFile) throws FilePathException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FilePathException("Invalid File path " + fileName);
            }

            File file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes());

            return fileRepository.save(file);
        } catch (IOException ex) {
            throw new FilePathException("File Not Stored:  " + fileName + "Please try again: "+ ex);
        }
    }

    public File getFile(Long fileId) throws FileNotFoundException {
        return fileRepository.findById(fileId).orElseThrow(() -> new FileNotFoundException("File not found with id: " + fileId));
    }
    
    
    public Boolean deleteFile(Long fileId) throws FileNotFoundException{
        
    	try {
    	fileRepository.deleteById(fileId);
    	return true;
    	}catch(Exception e){
    		 throw new FileNotFoundException("File Not Found ");
    		
    	}
		
    }

    
    
    //to make an HQL Query: https://javadeveloperzone.com/spring-boot/spring-data-jpa-delete-query/
    //https://www.baeldung.com/spring-data-jpa-delete
}
	

