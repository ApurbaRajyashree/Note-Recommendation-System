package com.note.NoteRecommender.services;


import com.note.NoteRecommender.dto.SemesterDto;
import org.springframework.stereotype.Service;
import com.note.NoteRecommender.entities.Semester;

import java.util.List;

@Service
public interface SemesterService {
    SemesterDto createSemester(Long userId, Long departmentId, SemesterDto semesterDto) throws Exception;

   String  deleteSemester(Long userId,Long departmentId,Long semesterId) throws Exception;

    List<SemesterDto> readSemesterByDepartment(Long userId, Long departmentId) throws Exception;

    SemesterDto readSemesterById(Long userId,Long departmentId,Long semesterId) throws Exception;

    SemesterDto updateSemester(Long userId,Long departmentId,Long semesterId,SemesterDto semesterDto) throws Exception;
}
