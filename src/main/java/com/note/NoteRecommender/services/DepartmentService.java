package com.note.NoteRecommender.services;

import com.note.NoteRecommender.dto.DepartmentDto;
import com.note.NoteRecommender.entities.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {

    DepartmentDto createDepartment(Long userId, DepartmentDto departmentDto) throws Exception;


    DepartmentDto updateDepartment(Long userId,Long departmentId,DepartmentDto departmentDto) throws Exception;

    List<DepartmentDto> getAllDepartments(Long userId);

    DepartmentDto getDepartmentByName(String name);


    String deleteDepartment(String departmentName);
}
