package com.atlasCopco.ToolTest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atlasCopco.ToolTest.model.StudentEntity;
import com.atlasCopco.ToolTest.repository.StudentRepository;
import com.atlasCopco.ToolTest.serviceImpl.ToolTestServiceImpl;

@RestController
@RequestMapping("/toolTest/")
public class TooTestController {

    private static final Logger logger = LoggerFactory.getLogger(TooTestController.class);

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ToolTestServiceImpl toolTestServiceImpl;

    @PostMapping("studentRecords/")
    public ResponseEntity< StudentEntity> saveAllStudents(@RequestBody StudentEntity students) {
        logger.info("Received a request to save multiple students.");

       StudentEntity savedStudents = studentRepository.save(students);

        logger.info("Save student activity successful");
        return new ResponseEntity<>(savedStudents, HttpStatus.ACCEPTED);
    }
    
    @GetMapping("getAllRecords/")
    public List<StudentEntity> getAllStudents(){
    	
    	return studentRepository.findAll();
    		 
    }
    
    @PostMapping("encrypt")
    public String encryptFiles(@RequestParam String username,@RequestParam String password) {
    	
    	try {
    		toolTestServiceImpl.encryptExcelFiles(username, password);
    		
    		
    	}catch(Exception e) {
    		logger.info("Error while encrypting files",e.getMessage(),e);
    		
    	}
		return "Input files from the mentioned directory got encrypted successfully";
    }
    
    
}
