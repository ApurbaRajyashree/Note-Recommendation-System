package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.CourseDto;
import com.note.NoteRecommender.dto.DepartmentDto;
import com.note.NoteRecommender.dto.SemesterDto;
import com.note.NoteRecommender.entities.Course;
import com.note.NoteRecommender.entities.Department;
import com.note.NoteRecommender.entities.Semester;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.repositories.SemesterRepo;
import com.note.NoteRecommender.services.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public SemesterDto createSemester(Long userId, Long departmentId, SemesterDto semesterDto) throws Exception {

        SemesterDto semesterDto1=new SemesterDto();

        User user=this.queryHelper.getUserMethod(userId);
        Department department=this.queryHelper.getDepartmentMethod(departmentId);
        Semester resultSemester=new Semester();

        Set<Department> Departments = user.getDepartments();
        for (Department eachDepartment : Departments
        ) {
            Department department1 = eachDepartment;
            if (department1.getDepartmentId().equals(department.getDepartmentId())) {
                List<Semester> semesters = department1.getSemesterList();
                for (Semester eachSemester : semesters
                ) {
                    if(eachSemester.getSemesterName().equals(semesterDto.getSemesterName())){
                        throw new Exception("semester with the given name already exist in the given department with id "+departmentId);
                    }
                }
                Semester semester = new Semester();
                semester.setSemesterName(semesterDto.getSemesterName());


                semester.setDepartment(department);
                semester.setUsers(Set.of(user));
                user.getSemesters().add(semester);

                resultSemester = this.semesterRepo.save(semester);
                semesterDto1.setSemesterId(resultSemester.getSemesterId());
                semesterDto1.setSemesterName(resultSemester.getSemesterName());


            }
        }
        return semesterDto1;
    }

    @Override
    public String deleteSemester(Long userId, Long departmentId, Long semesterId) throws Exception {
        String message="";
        User user=this.queryHelper.getUserMethod(userId);
        Department department=this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester=this.queryHelper.getSemesterMethod(semesterId);
        if(semester!=null){

            Set<Department> userDepartments=user.getDepartments();
            if(userDepartments.isEmpty()){
                throw new Exception("there is no department created by the given user!!!");
            }else{
                for (Department eachUserDepartment:userDepartments   ) {
                    Department department1=eachUserDepartment;
                    if(department1.getDepartmentId().equals(department.getDepartmentId())){
                        List<Semester> semesters=department1.getSemesterList();
                        for (Semester eachCategory:semesters
                        ) {
                            if(eachCategory.getSemesterId().equals(semester.getSemesterId())){
                                List<Course> courses=semester.getCourseList();
                                if(!courses.isEmpty()){
                                    for (Course eachCourse:courses
                                    ) {
                                        eachCourse.setSemester(null);
                                    }

                                }
                                semester.setUsers(null);
                                user.getSemesters().remove(semester);

                                semester.setDepartment(null);

                                this.semesterRepo.deleteById(semesterId);
                                message="semester deleted successfully";
                            }
                        }
                    }

                }
            }
        }else {
            return message="there is no semester with the given id "+semesterId;
        }
        return message;
    }



    @Override
    public List<SemesterDto> readSemesterByDepartment(Long userId, Long departmentId) throws Exception {
        List<Semester> semesters=new ArrayList<>();
        User user=queryHelper.getUserMethod(userId);
        Department faculty=queryHelper.getDepartmentMethod(departmentId);
        semesters=faculty.getSemesterList();
        Set<Department> departments=user.getDepartments();
        List<SemesterDto> semesterDtos=new ArrayList<>(departments.size());
        if(!departments.isEmpty()) {

            for (Department eachDepartment:departments ) {
                if (eachDepartment.getDepartmentId().equals(departmentId)) {
                    if (!semesters.isEmpty()) {

                        for (Semester eachCat:semesters ) {
                            SemesterDto semesterDto=new SemesterDto();

                            semesterDto.setSemesterId(eachCat.getSemesterId());
                            semesterDto.setSemesterName(eachCat.getSemesterName());

                            semesterDto.setDepartmentDto(this.modelMapper.map(eachCat.getDepartment(),DepartmentDto.class));
                            List<Course> courses=eachCat.getCourseList();
                            List<CourseDto> courseDtos=courses.stream().map(course -> this.modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
                            semesterDto.setCourseDtoList(courseDtos);

                            semesterDtos.add(semesterDto);

                        }
                    } else {
                        return null;
                    }
                }
            }
        }else {
            throw new Exception("there is no departments for the given user with id "+userId);
        }
        return semesterDtos;
    }



    @Override
    public SemesterDto readSemesterById(Long userId, Long departmentId, Long semesterId) throws Exception {
        SemesterDto semesterDto=new SemesterDto();
        User user=this.queryHelper.getUserMethod(userId);
        Department department=this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester=this.queryHelper.getSemesterMethod(semesterId);
        List<Semester> semesters=department.getSemesterList();

        Set<Department> departments=user.getDepartments();
        if(!departments.isEmpty()) {
            for (Department eachDepartment:departments ) {

                if (eachDepartment.getDepartmentId().equals(departmentId)) {
                    for (Semester eachCategory : semesters ) {
                        if (eachCategory.getSemesterId().equals(semester.getSemesterId())) {
                            semesterDto.setSemesterId(semester.getSemesterId());
                            semesterDto.setSemesterName(semester.getSemesterName());
                            semesterDto.setDepartmentDto(this.modelMapper.map(semester.getDepartment(),DepartmentDto.class));
                            List<Course> courses=semester.getCourseList();
                            if(!courses.isEmpty()){
                                semesterDto.setCourseDtoList(courses.stream().map(course -> this.modelMapper.map(course, CourseDto.class)).collect(Collectors.toList()));
                            }
                            return semesterDto;
                       }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public SemesterDto updateSemester(Long userId, Long departmentId, Long semesterId, SemesterDto semesterDto) throws Exception {
        SemesterDto  semesterDto1=new SemesterDto();
        User retrievedUser=this.queryHelper.getUserMethod(userId);
        Department retrievedDepartment=this.queryHelper.getDepartmentMethod(departmentId);

        Semester retrievedSemester=this.queryHelper.getSemesterMethod(semesterId);
        Set<Department> departments= retrievedUser.getDepartments();
        if(!departments.isEmpty()){
            for (Department eachDepartment:departments) {
                if(eachDepartment.getDepartmentId().equals(retrievedDepartment.getDepartmentId())){
                    List<Semester> semesters=eachDepartment.getSemesterList();
                    if(!semesters.isEmpty()){
                        for (Semester eachSemester:semesters) {
                            if(eachSemester.getSemesterId().equals(semesterId)){
                                if(semesterDto.getSemesterName()!=null){
                                    retrievedSemester.setSemesterName(semesterDto.getSemesterName());
                                }
                                if(semesterDto.getDepartmentDto()!=null){
                                    DepartmentDto departmentDto =semesterDto.getDepartmentDto();
                                    Department resultDepartment=this.modelMapper.map(departmentDto,Department.class);
                                    for (Department eachDepartment1:departments) {
                                        if(eachDepartment1.getDepartmentName().equals(departmentDto.getDepartmentName())){
                                            retrievedSemester.setDepartment(resultDepartment);
                                        }
                                    }
                                }
                                Semester resultSemester=this.semesterRepo.save(retrievedSemester);
                                Department department=resultSemester.getDepartment();

                                DepartmentDto facultyDto=this.modelMapper.map(department,DepartmentDto.class);

                                semesterDto1.setSemesterName(resultSemester.getSemesterName());
                                semesterDto1.setSemesterId(resultSemester.getSemesterId());
                                semesterDto1.setDepartmentDto(facultyDto);
                                return semesterDto1;
                            }
                        }
                    }else {
                        throw new Exception("there is no semester for the given faculty with id "+eachDepartment.getDepartmentId());
                    }
                }
            }
        }else {
            throw new Exception("there is no departments for the given user!!!");
        }
        return null;
    }

}




