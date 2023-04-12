package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.dto.NoteDto;
import com.note.NoteRecommender.helper.QueryHelper;
import com.note.NoteRecommender.entities.*;
import com.note.NoteRecommender.repositories.NoteRepositories;
import com.note.NoteRecommender.services.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.note.NoteRecommender.helper.NoteResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepositories noteRepo;

    @Autowired
    private QueryHelper queryHelper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Note createNote(Long userId, Long departmentId, Long semesterId, Long courseId, Note note) throws Exception {
        Note resultNote = new Note();
        Course course = this.queryHelper.getCourseMethod(courseId);
        User user = this.queryHelper.getUserMethod(userId);
        Department department = this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
        Set <Department> userDepartments = user.getDepartments();
        if (!userDepartments.isEmpty()) {
            for (Department eachDepartment : userDepartments) {
                if (eachDepartment.getDepartmentId().equals(department.getDepartmentId())) {
                    List<Semester> semesters = eachDepartment.getSemesterList();
                    for (Semester eachSemester : semesters) {
                        if (eachSemester.getSemesterId().equals(semester.getSemesterId())) {
                            List<Course> courses = semester.getCourseList();
                            for (Course eachCourse : courses) {
                                if (eachCourse.getCourseId().equals(course.getCourseId())) {
                                    note.setCourse(course);
                                    note.setUser(user);
                                    note.setNoteStatus(NoteStatus.pending);
                                    note.setAverageRating(0);
                                    note.setTotalRatingCount(0);
                                    resultNote = this.noteRepo.save(note);
                                }
                            }
                        }
                    }
                } else {
                    throw new Exception("Department is null for the given user");
                }
            }
        }
        return resultNote;
    }


    @Override
    public Note updateNote(Long userId, Long departmentId, Long semesterId, Long courseId, Long noteId, Note
            note) {
        User retrievedUser = this.queryHelper.getUserMethod(userId);
        Department retrievedDepartment = this.queryHelper.getDepartmentMethod(departmentId);
        Semester retrievedSemester = this.queryHelper.getSemesterMethod(semesterId);
        Course retrievedCourse = this.queryHelper.getCourseMethod(courseId);
        Note retrivedNote = this.queryHelper.getNoteMethod(noteId);
        Set<Department> userDepartments = retrievedUser.getDepartments();
        for (Department eachDepartment : userDepartments) {
            if (eachDepartment.getDepartmentId().equals(retrievedDepartment.getDepartmentId())) {
                List<Semester> semesters = retrievedDepartment.getSemesterList();
                for (Semester eachSemester : semesters) {
                    if (eachSemester.getSemesterId().equals(retrievedSemester.getSemesterId())) {
                        List<Course> courses = eachSemester.getCourseList();
                        for (Course eachCourse : courses) {
                            if (eachCourse.getCourseId().equals(retrievedCourse.getCourseId())) {
                                List<Note> notes = eachCourse.getNoteList();
                                for (Note eachNote : notes) {
                                    if (eachNote.getNoteId().equals(retrivedNote.getNoteId())) {
                                        if (retrievedUser.getUserId().equals(retrivedNote.getUser().getUserId())) {
                                            if(retrivedNote.getNoteStatus().equals(NoteStatus.approved)) {
                                                retrivedNote.setNoteTitle(note.getNoteTitle());
                                                retrivedNote.setNoteDescription(note.getNoteDescription());
                                                retrivedNote.setNoteStatus(NoteStatus.pending);
                                                retrivedNote.setAverageRating(note.getAverageRating());
                                                retrivedNote = this.noteRepo.save(retrivedNote);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return retrivedNote;
    }

    @Override
    public Note getNoteByName(Long userId, Long departmentId, Long semesterId, Long courseId, String name) {
        User user = this.queryHelper.getUserMethod(userId);
        Department department = this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
        Course course = this.queryHelper.getCourseMethod(courseId);
        Note note = this.noteRepo.findNoteByNoteTitle(name);
        Set<Department> userDepartments = user.getDepartments();

        Note note1 = new Note();
        for (Department eachDepartment : userDepartments) {
            if (eachDepartment.getDepartmentId().equals(department.getDepartmentId())) {
                List<Semester> semesters = eachDepartment.getSemesterList();
                for (Semester eachSemester : semesters) {
                    if (eachSemester.getSemesterName().equals(semester.getSemesterName())) {
                        List<Course> courses = eachSemester.getCourseList();
                        for (Course eachCourse : courses) {
                            if (eachCourse.getCourseId().equals(course.getCourseId())) {
                                List<Note> notes = eachCourse.getNoteList();
                                for (Note eachNote : notes) {
                                    if (eachNote.getNoteTitle().equals(note.getNoteTitle())) {
                                        note1 = this.noteRepo.findNoteByNoteTitle(name);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return note1;
    }

    @Override
    public Note getNoteById(Long noteId) {
        Note note=this.noteRepo.findNoteByNoteId(noteId);
        return note;
    }


    @Override
    public List<Note> getNoteByCourse(Long userId, Long departmentId, Long semesterId, Long courseId) {
        User user = this.queryHelper.getUserMethod(userId);
        Department department = this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
        Course course = this.queryHelper.getCourseMethod(courseId);
        Set<Department> userDepartments = user.getDepartments();

        List<Note> notes = new ArrayList<>();
        for (Department eachDepartment : userDepartments) {
            if (eachDepartment.getDepartmentId().equals(department.getDepartmentId())) {
                List<Semester> semesters = department.getSemesterList();
                for (Semester eachSemester : semesters) {
                    if (eachSemester.getSemesterId().equals(semester.getSemesterId())) {
                        List<Course> courses = semester.getCourseList();
                        for (Course eachCourse : courses) {
                            if (eachCourse.getCourseId().equals(course.getCourseId())) {
                                notes = this.noteRepo.findAllByCourse(course);
                            }
                        }
                    }
                }
            }
        }
        return notes;
    }

    @Override
    public String deleteNote(Long userId, Long departmentId, Long semesterId, Long courseId, Long noteId) throws Exception {
        String message = "";
        User user = this.queryHelper.getUserMethod(userId);
        Department department = this.queryHelper.getDepartmentMethod(departmentId);
        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
        Course course = this.queryHelper.getCourseMethod(courseId);
        Note note=this.queryHelper.getNoteMethod(noteId);
        List<Note> notes = course.getNoteList();
        if (notes.isEmpty()) {
            throw new Exception("There is no note for the given course id " + courseId);
        } else {
            for (Note eachNote : notes
            ) {
                if (eachNote.getNoteId().equals(noteId)) {
                    Set<Department> departments = user.getDepartments();
                    if (!departments.isEmpty()) {
                        for (Department eachDepartment : departments
                        ) {
                            if (eachDepartment.getDepartmentId().equals(department.getDepartmentId())) {
                                List<Semester> semesters = department.getSemesterList();
                                if (!semesters.isEmpty()) {
                                    for (Semester eachSemester : semesters
                                    ) {
                                        if (eachSemester.getSemesterId().equals(semester.getSemesterId())) {
                                            List<Course> courses=semester.getCourseList();
                                            for (Course eachCourse: courses){
                                                if(eachCourse.getCourseId().equals(courseId)){
                                                    note.setCourse(null);
                                                    note.setUser(null);
                                                    user.getNotes().remove(note);
                                                }
                                            }
                                            this.noteRepo.deleteById(noteId);
                                            message = "note with the id " + noteId + " deleted successfully";
                                        }
                                    }
                                } else {
                                    throw new Exception("no semesters for the given department with id " + departmentId);
                                }
                            }
                        }
                    } else {
                        throw new Exception("no department for the given user with id " + user.getUserId());
                    }
                }
            }
        }
        return message;
    }

    @Override
    public NoteResponse getAllNotes(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sort=null;
        if(sortOrder.equalsIgnoreCase("asc")) {
            sort  = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }

      //  Pageable page=PageRequest.of(pageNumber,pageSize,sort);
        Pageable page=PageRequest.of(pageNumber,pageSize,sort);
        Page<Note> notes=this.noteRepo.findAll(page);
        List<Note> notes1=notes.getContent();

        List<NoteDto> noteDtos=notes.stream().map((post)->this.modelMapper.map(notes1, NoteDto.class)).collect(Collectors.toList());

        NoteResponse noteResponse=new NoteResponse();
        noteResponse.setPageNumber(notes.getNumber());
        noteResponse.setContents(noteDtos);
        noteResponse.setPageSize(notes.getSize());
        noteResponse.setTotalElements(notes.getTotalElements());
        noteResponse.setTotalPage(notes.getTotalPages());

        return noteResponse;
    }

    @Override
    public List<Note> getAllNoteByUser(Long userId) {
        User user=this.queryHelper.getUserMethod(userId);
        List<Note> notes=this.noteRepo.findAllByUser(user);
        return notes;
    }

    @Override
    public List<Note> searchNote(String keyword) {
        List<Note> serachResultNotes=this.noteRepo.findByNoteTitleContaining(keyword);
        return serachResultNotes;
    }

    @Override
    public String approveNote(Long noteId) {
        Note note = this.noteRepo.findById(noteId).get();
        note.setNoteStatus(NoteStatus.approved);
        note.setDateOfNoteCreation(new Date());
        this.noteRepo.save(note);
        return "Note approved!!!";
    }

    @Override
    public String rejectNote(Long noteId) {
        Note note = this.noteRepo.findById(noteId).get();
        note.setNoteStatus(NoteStatus.rejected);
        noteRepo.save(note);
        return "Note Rejected!!!";
    }

}
