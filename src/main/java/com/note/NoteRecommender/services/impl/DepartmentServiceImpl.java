package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.DepartmentDto;
import com.note.NoteRecommender.entities.Department;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.repositories.DepartmentRepo;
import com.note.NoteRecommender.services.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DepartmentDto createDepartment(Long userId, DepartmentDto departmentDto) throws Exception {

        User retrievedUser = queryHelper.getUserMethod(userId);

        Department retrievedDepartment = this.departmentRepo.findByDepartmentName(departmentDto.getDepartmentName());
        if (retrievedDepartment != null) {
            throw new Exception("Department with the given department name " + departmentDto.getDepartmentName() + "already exists!!");
        } else {
            retrievedDepartment = this.modelMapper.map(departmentDto, Department.class);

            retrievedUser.getDepartments().add(retrievedDepartment);
            retrievedDepartment.setUsers(Set.of(retrievedUser));


            retrievedDepartment = this.departmentRepo.save(retrievedDepartment);

        }
        return this.modelMapper.map(retrievedDepartment, DepartmentDto.class);

    }

    @Override
    public DepartmentDto updateDepartment(Long userId, Long departmentId, DepartmentDto departmentDto) throws Exception {
        DepartmentDto departmentDto1 = new DepartmentDto();
        User retrievedUser = this.queryHelper.getUserMethod(userId);
        Department retrievedDepartment = this.queryHelper.getDepartmentMethod(departmentId);
        Set<Department> departments = retrievedUser.getDepartments();
        if (!departments.isEmpty()) {
            for (Department eachDepartment : departments
            ) {
                if (eachDepartment.getDepartmentId().equals(retrievedDepartment.getDepartmentId())) {
                    if (departmentDto.getDepartmentName() != null) {
                        retrievedDepartment.setDepartmentName(departmentDto.getDepartmentName());
                    }
                    if (departmentDto.getDepartmentDescription() != null) {
                        retrievedDepartment.setDepartmentDescription(departmentDto.getDepartmentDescription());
                    }
                    Department department = this.departmentRepo.save(retrievedDepartment);
                    departmentDto1.setDepartmentId(department.getDepartmentId());
                    departmentDto1.setDepartmentName(department.getDepartmentName());
                    departmentDto1.setDepartmentDescription(department.getDepartmentDescription());

                }
            }

        } else {
            throw new Exception("there is no departments created by the given user!!!");
        }

        return departmentDto1;
    }

    @Override
    public List<DepartmentDto> getAllDepartments(Long userId) {
        User retrievedUser = this.queryHelper.getUserMethod(userId);
        Set<Department> departments = retrievedUser.getDepartments();
        if (!departments.isEmpty()) {
            return departments.stream().map(department -> this.modelMapper.map(department, DepartmentDto.class)).collect(Collectors.toList());
        }


        return null;
    }

    @Override
    public DepartmentDto getDepartmentByName(Long userId, String name) {
        DepartmentDto resultDto = new DepartmentDto();

        User retrievedUser = this.queryHelper.getUserMethod(userId);
        Department resultDepartment = this.departmentRepo.findByDepartmentName(name);
        Set<Department> departments = retrievedUser.getDepartments();
        if (!departments.isEmpty()) {
            for (Department eachDepartment : departments) {
                if (eachDepartment.getDepartmentId().equals(resultDepartment.getDepartmentId())) {
                    if (resultDepartment != null) {
                        resultDto.setDepartmentId(resultDepartment.getDepartmentId());
                        resultDto.setDepartmentName(resultDepartment.getDepartmentName());
                        resultDto.setDepartmentDescription(resultDepartment.getDepartmentDescription());
                    } else {
                        return null;
                    }
                }
            }
        }

        return resultDto;
    }

    @Override
    public String deleteDepartment(Long userId, String departmentName) {
        User retrievedUser = this.queryHelper.getUserMethod(userId);

        Department department = this.departmentRepo.findByDepartmentName(departmentName);
        if (department == null) {
            return "there is no department exist with the given name";
        } else {
            Set<Department> departments = retrievedUser.getDepartments();
            if (!departments.isEmpty()) {
                for (Department eachDepartment : departments
                ) {
                    if (eachDepartment.getDepartmentId().equals(department.getDepartmentId())) {
                        //eachDepartment.getUsers().remove(retrievedUser);
                        this.departmentRepo.deleteById(department.getDepartmentId());
                    }
                }
            } else {
                return "there is no department created by the given user with id " + retrievedUser.getUserId();
            }

        }
        return "department deleted successfully";
    }
}
