package com.note.NoteRecommender.dto;
import com.note.NoteRecommender.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Component
public class UserDto implements Serializable {
    private Long userId;
    private String userName;
    private String email;
    private String phoneNumber;
    private String batch;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private Boolean isEnabled=Boolean.FALSE;
    private Set<RoleDto> roleDtoSet;
    private DepartmentDto departmentDto;
    private SemesterDto semesterDto;
    private CourseDto courseDto;





}
