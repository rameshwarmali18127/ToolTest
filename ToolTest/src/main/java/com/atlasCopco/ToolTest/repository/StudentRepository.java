package com.atlasCopco.ToolTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atlasCopco.ToolTest.model.StudentEntity;

public interface StudentRepository   extends JpaRepository<StudentEntity,String>{
	

}
