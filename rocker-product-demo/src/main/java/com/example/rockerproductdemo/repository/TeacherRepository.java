package com.example.rockerproductdemo.repository;


import com.example.rockerproductdemo.emtry.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author AGbetrayal
 * @date 2020/5/4 15:02
 */
public interface TeacherRepository extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {
}
