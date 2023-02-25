package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.CourseDto;
import com.note.NoteRecommender.dto.SemesterDto;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.entities.*;
import com.note.NoteRecommender.repositories.CourseRepo;
import com.note.NoteRecommender.repositories.RoleRepositories;
import com.note.NoteRecommender.services.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseDto createCourse(Long userId, Long departmentId, Long semesterId, CourseDto courseDto) throws Exception {


        Course resultCourse=new Course();
        User user=this.queryHelper.getUserMethod(userId);
        Department department =this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester =this.queryHelper.getSemesterMethod(semesterId);
        Set<Department> departments=user.getDepartments();
        if(!departments.isEmpty()) {
            for (Department eachDepartment:departments
            ) {
                if(eachDepartment.getDepartmentId().equals(departmentId)){
                    List<Semester> semesters=eachDepartment.getSemesterList();
                    if(!semesters.isEmpty()){
                        for (Semester eachSemester:semesters
                        ) {
                            if(eachSemester.getSemesterId().equals(semesterId)){
                                List<Course> courses = semester.getCourseList();
                                if (!courses.isEmpty()) {
                                    for (Course eachCourse : courses
                                    ) {
                                        Course retrievedCourse = this.courseRepo.findByCourseTitle(eachCourse.getCourseTitle());
                                        if (retrievedCourse.getCourseTitle().equals(courseDto.getCourseTitle())) {
                                            throw new Exception("Course with the given title already exist in the given semester");
                                        }

                                    }
                                }
                                Course course=new Course();
                                course.setCourseTitle(courseDto.getCourseTitle());
                                course.setCourseDesc(courseDto.getCourseDesc());

                                CourseDto courseDto1=new CourseDto();
                                course.setUsers(Set.of(user));
                                user.getCourses().add(course);
                                course.setSemester(semester);
                                resultCourse=this.courseRepo.save(course);
                                courseDto1.setCourseId(resultCourse.getCourseId());
                                courseDto1.setCourseTitle(resultCourse.getCourseTitle());
                                courseDto1.setCourseDesc(resultCourse.getCourseDesc());
                                courseDto1.setSemesterDto(this.modelMapper.map(resultCourse.getSemester(),SemesterDto.class));
                                return courseDto1;


                            }
                        }

                    }else {
                        throw new Exception("there is no semesters for given department with id "+departmentId);
                    }
                }
            }
        }else {
            throw new Exception("there is no department for the given user with id "+userId);
        }

        return null;
    }

    @Override
    public String deleteCourse(Long userId, Long departmentId, Long semesterId, Long courseId) throws Exception {
        String message="";
        User user=this.queryHelper.getUserMethod(userId);
        Department department=this.queryHelper.getDepartmentMethod(departmentId);
        Semester  semester=this.queryHelper.getSemesterMethod(semesterId);
        Course course=this.queryHelper.getCourseMethod(courseId);


        List<Course> courses=semester.getCourseList();
        if(courses.isEmpty()){
            throw new Exception("There is no course for the given semester id "+semesterId);
        }else {
            for (Course eachCourse:courses
            ) {
                if(eachCourse.getCourseId().equals(courseId)){

                    Set<Department> departments=user.getDepartments();
                    if(!departments.isEmpty()) {
                        for (Department eachUserFaculties : departments
                        ) {
                            if (eachUserFaculties.getDepartmentId().equals(department.getDepartmentId())) {
                                Department department1 = eachUserFaculties;
                                List<Semester> semesters = department1.getSemesterList();
                                if (!semesters.isEmpty()) {
                                    for (Semester eachSemester:semesters
                                    ) {
                                        if(eachSemester.getSemesterId().equals(semester.getSemesterId())){
                                            course.setSemester(null);
                                            course.setUsers(null);
                                            user.getCourses().remove(course);
                                            List<Note> notes=course.getNoteList();
                                            if(!notes.isEmpty()){
                                                for (Note eachExam:notes
                                                ) {
                                                    eachExam.setCourse(null);

                                                }
                                            }

                                            this.courseRepo.deleteById(course.getCourseId());
                                            message="course with the id "+courseId+" deleted successfully";

                                        }
                                    }

                                }else {
                                    throw new Exception("no semesters for the given department with id "+department1.getDepartmentId());
                                }

                            }

                        }
                    }else {
                        throw new Exception("no userdepartments for the given user with id "+user.getUserId());
                    }

                }



            }
        }

        return message;
    }

    @Override
    public CourseDto updateCourse(Long userId, Long departmentId, Long semesterId, Long courseId,CourseDto courseDto) throws Exception {
        User retrievedUser=this.queryHelper.getUserMethod(userId);
        Department retrievedDepartment=this.queryHelper.getDepartmentMethod(departmentId);
        Semester retrievedSemester=this.queryHelper.getSemesterMethod(semesterId);
        Course retrievedCourse=this.queryHelper.getCourseMethod(courseId);



        Set<Department> userDepartments = retrievedUser.getDepartments();
        for (Department eachUserDepartment : userDepartments
        ) {
            Department department = eachUserDepartment;
            if (department.getDepartmentId().equals(retrievedDepartment.getDepartmentId())) {
                List<Semester> semesters = department.getSemesterList();
                for (Semester eachSemester : semesters
                ) {
                    if (eachSemester.getSemesterId().equals(retrievedSemester.getSemesterId())) {
                        List<Course> courses = eachSemester.getCourseList();
                        for (Course eachCourse : courses
                        ) {
                            if (eachCourse.getCourseId().equals(retrievedCourse.getCourseId())) {
                                if(!courseDto.getCourseTitle().isEmpty()) {
                                    retrievedCourse.setCourseTitle(courseDto.getCourseTitle());
                                }
                                if(!courseDto.getCourseTitle().isEmpty()) {
                                    retrievedCourse.setCourseDesc(courseDto.getCourseDesc());
                                }
                                SemesterDto updateCat= courseDto.getSemesterDto();
                                if(updateCat!=null) {
                                    List<Semester> semesters1=department.getSemesterList();
                                    for (Semester eachCat:semesters1
                                    ) {
                                        if(eachCat.getSemesterId().equals(updateCat.getSemesterId())){
                                            Semester semester=this.queryHelper.getSemesterMethod(courseDto.getSemesterDto().getSemesterId());
                                            retrievedCourse.setSemester(semester);
                                        }




                                    }
                                }
                                retrievedCourse=this.courseRepo.save(retrievedCourse);


                            }
                        }
                    }

                }
            }
            else {
                throw new Exception("provided department does not match with the list of department created by user");
            }

        }
        return this.modelMapper.map(retrievedCourse,CourseDto.class);
    }



    @Override
    public List<CourseDto> getCoursesBySemester(Long userId, Long departmentId, Long semesterId) throws Exception {
        List<Course> retrievedCourse=new ArrayList<>();
        List<CourseDto> courseDtos=new ArrayList<>();
        User user=this.queryHelper.getUserMethod(userId);
        Department department=this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester=this.queryHelper.getSemesterMethod(semesterId);
        List<Course> courses=semester.getCourseList();
        if(!courses.isEmpty()){

            Set<Department> userDepartments=user.getDepartments();
            if(!userDepartments.isEmpty()){
                for (Department eachUserDepartment:userDepartments
                ) {
                    if(eachUserDepartment.getDepartmentId().equals(department.getDepartmentId())){
                        Department department1=eachUserDepartment;
                        List<Semester> semesters=department1.getSemesterList();

                        for (Semester eachSemester:semesters
                        ) {
                            if(eachSemester.getSemesterId().equals(semesterId)){
                                retrievedCourse=this.courseRepo.findBySemester(semester);
                                courseDtos=retrievedCourse.stream().map(course -> this.modelMapper.map(course,CourseDto.class)).collect(Collectors.toList());
                            }
                        }
                    }

                }

            }else{
                throw new Exception("there is empty userdepartments for the given user!!!");
            }



        }else {
            return null;
        }
        return courseDtos;
    }

    @Override
    public CourseDto getCourseById(Long userId, Long departmentId, Long semesterId, Long courseId) throws Exception {
        User retrievedUser=this.queryHelper.getUserMethod(userId);
        Department retrievedDepartment=this.queryHelper.getDepartmentMethod(departmentId);
        Semester retrievedSemester=this.queryHelper.getSemesterMethod(semesterId);
        Course retrievedCourse=this.queryHelper.getCourseMethod(courseId);
        CourseDto resultCourseDto=new CourseDto();

        Set<Department> departments=retrievedUser.getDepartments();
        if(!departments.isEmpty()){
            for (Department eachDepartment:departments
            ) {
                if(eachDepartment.getDepartmentId().equals(departmentId)){
                    List<Semester> semesters=eachDepartment.getSemesterList();
                    if(!semesters.isEmpty()){
                        for (Semester eachSemester:semesters
                        ) {
                            if(eachSemester.getSemesterId().equals(semesterId)){
                                List<Course> courses=eachSemester.getCourseList();
                                if(!courses.isEmpty()){
                                    for (Course eachCourse:courses
                                    ) {
                                        if(eachCourse.getCourseId().equals(retrievedCourse.getCourseId())){
                                            resultCourseDto.setCourseId(eachCourse.getCourseId());
                                            resultCourseDto.setCourseTitle(eachCourse.getCourseTitle());
                                            resultCourseDto.setSemesterDto(this.modelMapper.map(eachCourse.getSemester(),SemesterDto.class));
                                            resultCourseDto.setNotes(eachCourse.getNoteList());
                                            return resultCourseDto;
                                        }
                                    }


                                }else {
                                    throw new Exception("there is no courses for the given semester with id "+semesterId);
                                }
                            }


                        }


                    }else {
                        throw new Exception("there is no semester for the given department with id "+departmentId);
                    }
                }
            }

        }else {
            throw new Exception("there is no department for the given user with id "+userId);
        }

        return null;
    }

    @Override
    public List<CourseDto> getAllCourse(Long userId) {
        User retrievedUser=this.queryHelper.getUserMethod(userId);
        Set<Course> courses=retrievedUser.getCourses();
        List<CourseDto> courseDtos=new ArrayList<>();
        if(!courses.isEmpty()){
            for (Course eachCourse:courses
            ) {
                CourseDto courseDto=new CourseDto();
                courseDto.setCourseId(eachCourse.getCourseId());
                courseDto.setCourseTitle(eachCourse.getCourseTitle());
                courseDto.setCourseDesc(eachCourse.getCourseDesc());
                courseDto.setSemesterDto(this.modelMapper.map(eachCourse.getSemester(),SemesterDto.class));
                courseDto.setNotes(eachCourse.getNoteList());
                courseDtos.add(courseDto);
            }
            return courseDtos;

        }
        return null;
    }

}
