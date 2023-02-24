package com.note.NoteRecommender.helper;


import com.note.NoteRecommender.entities.*;
import com.note.NoteRecommender.exceptions.ResourceNotFoundException;
import com.note.NoteRecommender.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryHelper {
    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private RoleRepositories roleRepo;
    @Autowired
    private UserRepositories userRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private  NoteRepositories noteRepo;

    @Autowired
    private  CommentRepo commentRepo;



    public Semester getSemesterMethod(Long semesterId){
        return this.semesterRepo.findById(semesterId).orElseThrow(()->new ResourceNotFoundException("semester","semesterId ",semesterId));
    }

    public Department getDepartmentMethod(Long departmentId){
        return this.departmentRepo.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("department","departmentId ",departmentId));
    }

    public User getUserMethod(Long userId){
        return this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId ",userId));
    }

    public Role getRoleMethod(Long roleId){
        return this.roleRepo.findById(roleId).orElseThrow(()->new ResourceNotFoundException("role","roleId ",roleId));
    }

    public Course getCourseMethod(Long courseId){
        return this.courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("course","courseId ",courseId));
    }
    public Note getNoteMethod(Long noteId){
        return this.noteRepo.findById(noteId).orElseThrow(()->new ResourceNotFoundException("note","noteId ",noteId));
    }

    public Comments getCommentsMethod(Long commentId){
        return this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","commmentId ",commentId));
    }

}
