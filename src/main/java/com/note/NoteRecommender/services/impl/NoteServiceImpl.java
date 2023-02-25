//package com.note.NoteRecommender.services.impl;
//
//import com.note.NoteRecommender.dto.NoteDto;
//import com.note.NoteRecommender.helper.QueryHelper;
//import com.note.NoteRecommender.entities.*;
//import com.note.NoteRecommender.repositories.NoteRepositories;
//import com.note.NoteRecommender.services.NoteService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import com.note.NoteRecommender.helper.NoteResponse;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//
//@Service
//public class NoteServiceImpl implements NoteService {
//
//    @Autowired
//    private NoteRepositories noteRepo;
//
//    @Autowired
//    private QueryHelper queryHelper;
//    @Autowired
//    private ModelMapper modelMapper;
//
//
//
//
//    @Override
//    public Note createNote(Long userId, Long departmentId, Long semesterId, Long courseId, Note note) throws Exception {
//        Note resultNote = new Note();
//        Course course = this.queryHelper.getCourseMethod(courseId);
//        User user = this.queryHelper.getUserMethod(userId);
//        Department department = this.queryHelper.getDepartmentMethod(departmentId);
//        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
//        Set <Department> userDepartments = user.getDepartmentList();
//        if (!userDepartments.isEmpty()) {
//            for (Department eachDepartment : userDepartments) {
//                if (eachDepartment.getDepartment().getDepartmentId().equals(department.getDepartmentId())) {
//                    Department department1 = eachDepartment.getDepartment();
//
//                    List<Semester> semesters = department1.getSemesterList();
//                    for (Semester eachSemester : semesters) {
//                        if (eachSemester.getSemesterId().equals(semester.getSemesterId())) {
//                            List<Course> courses = semester.getCourseList();
//                            for (Course eachCourse : courses) {
//                                if (eachCourse.getCourseId().equals(course.getCourseId())) {
//                                    note.setCourse(course);
//                                    note.setUser(user);
//                                    note.setDoNoteCreation(new Date());
//
//                                    resultNote = this.noteRepo.save(note);
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    throw new Exception("user department is null for the given user");
//                }
//
//            }
//        }
//        return resultNote;
//    }
//
//
//    @Override
//    public Note updateNote(Long userId, Long departmentId, Long semesterId, Long courseId, Long noteId, Note
//            note) {
//        User retrievedUser = this.queryHelper.getUserMethod(userId);
//        Department retrievedDepartment = this.queryHelper.getDepartmentMethod(departmentId);
//        Semester retrievedSemester = this.queryHelper.getSemesterMethod(semesterId);
//        Course retrievedCourse = this.queryHelper.getCourseMethod(courseId);
//        Note retrivedNote = this.queryHelper.getNoteMethod(noteId);
//        List<Department> userDepartments = retrievedUser.getDepartmentList();
//        for (Department eachDepartment : userDepartments) {
//            Department department = eachDepartment.getDepartment();
//            if (department.getDepartmentId().equals(retrievedDepartment.getDepartmentId())) {
//                List<Semester> semesters = retrievedDepartment.getSemesterList();
//                for (Semester eachSemester : semesters) {
//                    if (eachSemester.getSemesterId().equals(retrievedSemester.getSemesterId())) {
//                        List<Course> courses = eachSemester.getCourseList();
//                        for (Course eachCourse : courses) {
//                            if (eachCourse.getCourseId().equals(retrievedCourse.getCourseId())) {
//                                List<Note> notes = eachCourse.getNoteList();
//                                for (Note eachNote : notes) {
//                                    if (eachNote.getNoteId().equals(retrivedNote.getNoteId())) {
//                                        if (retrievedUser.getUserId().equals(retrivedNote.getUser().getUserId())) {
//                                            retrivedNote.setNoteTitle(note.getNoteTitle());
//                                            retrivedNote.setNoteDescription(note.getNoteDescription());
//                                            retrivedNote.setDoNoteCreation(new Date());
//                                            retrivedNote = this.noteRepo.save(retrivedNote);
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
////            } else {
////                throw new Exception("provided department does not match with the list of department created by user");
//            // }
//            //    }
//
//        }
//        return retrivedNote;
//    }
//
//    @Override
//    public Note getNoteByName(Long userId, Long departmentId, Long semesterId, Long courseId, String name) {
//        User user = this.queryHelper.getUserMethod(userId);
//        Department department = this.queryHelper.getDepartmentMethod(departmentId);
//        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
//        Course course = this.queryHelper.getCourseMethod(courseId);
//        Note note = this.noteRepo.findNoteByNoteTitle(name);
//        List<Department> userDepartments = user.getDepartmentList();
//
//        Note note1 = new Note();
//        for (Department eachDepartment : userDepartments) {
//            Department department1 = eachDepartment.getDepartment();
//            if (department1.getDepartmentId().equals(department.getDepartmentId())) {
//                List<Semester> semesters = eachDepartment.getDepartment().getSemesterList();
//                for (Semester eachSemester : semesters) {
//                    if (eachSemester.getSemesterName().equals(semester.getSemesterName())) {
//                        List<Course> courses = eachSemester.getCourseList();
//                        for (Course eachCourse : courses) {
//                            if (eachCourse.getCourseId().equals(course.getCourseId())) {
//                                List<Note> notes = eachCourse.getNoteList();
//                                for (Note eachNote : notes) {
//                                    if (eachNote.getNoteTitle().equals(note.getNoteTitle())) {
//                                        note1 = this.noteRepo.findNoteByNoteTitle(name);
//                                    }
//
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//
//        }
//        return note1;
//    }
//
//    @Override
//    public Note getNoteById(Long noteId) {
//        Note note=this.noteRepo.findNoteByNoteId(noteId);
//
//        return note;
//    }
//
//
//    @Override
//    public List<Note> getNoteByCourse(Long userId, Long departmentId, Long semesterId, Long courseId) {
//        User user = this.queryHelper.getUserMethod(userId);
//        Department department = this.queryHelper.getDepartmentMethod(departmentId);
//        Semester semester = this.queryHelper.getSemesterMethod(semesterId);
//        Course course = this.queryHelper.getCourseMethod(courseId);
//        Set<Department> userDepartments = user.getDepartmentList();
//
//        List<Note> notes = new ArrayList<>();
//        for (Department eachDepartment : userDepartments) {
//            Department department1 = eachDepartment.getDepartment();
//            if (department1.getDepartmentId().equals(department.getDepartmentId())) {
//                List<Semester> semesters = department.getSemesterList();
//                for (Semester eachSemester : semesters) {
//                    if (eachSemester.getSemesterId().equals(semester.getSemesterId())) {
//                        List<Course> courses = semester.getCourseList();
//                        for (Course eachCourse : courses) {
//                            if (eachCourse.getCourseId().equals(course.getCourseId())) {
//                                notes = this.noteRepo.findAllByCourse(course);
//                            }
//
//                        }
//
//                    }
//
//                }
//            }
//        }
//        return notes;
//    }
//
//    @Override
//    public void deleteNote(Long userId, Long departmentId, Long semesterId, Long courseId, Long noteId) {
//
//    }
//
//    @Override
//    public NoteResponse getAllNotes(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
//        Sort sort=null;
//        if(sortOrder.equalsIgnoreCase("asc")) {
//            sort  = Sort.by(sortBy).ascending();
//        }
//        else {
//            sort = Sort.by(sortBy).descending();
//        }
//
//
//      //  Pageable page=PageRequest.of(pageNumber,pageSize,sort);
//        Pageable page=PageRequest.of(pageNumber,pageSize,sort);
//        Page<Note> notes=this.noteRepo.findAll(page);
//        List<Note> notes1=notes.getContent();
//
//        List<NoteDto> noteDtos=notes.stream().map((post)->this.modelMapper.map(notes1, NoteDto.class)).collect(Collectors.toList());
//
//        NoteResponse noteResponse=new NoteResponse();
//        noteResponse.setPageNumber(notes.getNumber());
//        noteResponse.setContents(noteDtos);
//        noteResponse.setPageSize(notes.getSize());
//        noteResponse.setTotalElements(notes.getTotalElements());
//        noteResponse.setTotalPage(notes.getTotalPages());
//
//        return noteResponse;
//    }
//
//    @Override
//    public List<Note> getAllNoteByUser(Long userId) {
//        User user=this.queryHelper.getUserMethod(userId);
//        List<Note> notes=this.noteRepo.findAllByUser(user);
//        return notes;
//    }
//
//    @Override
//    public List<Note> searchNote(String keyword) {
//        List<Note> serachResultNotes=this.noteRepo.findByNoteTitleContaining(keyword);
//        return serachResultNotes;
//    }
//
//}
