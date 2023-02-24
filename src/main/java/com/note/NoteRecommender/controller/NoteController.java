package com.note.NoteRecommender.controller;

import com.note.NoteRecommender.config.AppConstants;
import com.note.NoteRecommender.helper.ApiResponse;
import com.note.NoteRecommender.helper.NoteResponse;
import com.note.NoteRecommender.entities.Note;
import com.note.NoteRecommender.repositories.NoteRepositories;
import com.note.NoteRecommender.services.FileStorageService;
import com.note.NoteRecommender.services.NoteService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepositories noteRepositories;

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${project.file}")
    private String path;
    


    @PostMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/note/create")
    ResponseEntity<?> createNoteController(@Valid @PathVariable Long uId, @PathVariable Long depId, @PathVariable Long semId, @PathVariable Long couId, @RequestBody Note note) throws Exception {
        Note note1=this.noteService.createNote(uId,depId,semId,couId,note);
        if(note1==null){
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!",false), HttpStatusCode.valueOf(500));
        }
        return new ResponseEntity<>(note1, HttpStatusCode.valueOf(200));
    }

    @PutMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/note/{noteId}/update")
    public ResponseEntity<?> updateNoteController(@Valid @PathVariable("uId")Long uId, @PathVariable("depId")Long depId,@PathVariable("semId")Long semId,@PathVariable("couId")Long couId,@PathVariable("noteId")Long noteId, @RequestBody Note note) throws Exception {
        Note updatedNote=this.noteService.updateNote(uId,depId,semId,couId,noteId,note);

        if (updatedNote != null) {
            return new ResponseEntity<>(updatedNote, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));

        }
    }

    @GetMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/notes")
    public ResponseEntity<?> getNotesByCourse(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId,@PathVariable("semId") Long semId,@PathVariable("couId") Long couId) throws Exception {
        List<Note> notes = this.noteService.getNoteByCourse(uId, depId,semId,couId);
        if (notes != null) {
            return new ResponseEntity<>(notes, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("Something went wrong!!!", false), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/note/{noteName}")
    public ResponseEntity<?>  getNoteByName(@PathVariable("uId") Long uId, @PathVariable("depId") Long depId,@PathVariable("semId") Long semId,@PathVariable("couId") Long couId,@PathVariable("noteName") String noteName) throws Exception {
        Note note = this.noteService.getNoteByName(uId, depId,semId,couId,noteName);
        if (note != null) {
            return new ResponseEntity<>(note, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("There is no such note", false), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/user/{uId}/notes")
    public ResponseEntity<?>  getAllNoteByUser(@PathVariable("uId") Long uId ) throws Exception {
       List<Note> notes = this.noteService.getAllNoteByUser(uId);
        if (notes != null) {
            return new ResponseEntity<>(notes, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("User has no notes!!", false), HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping("/notes/all")
    public ResponseEntity<?>  getAllNotes(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                          @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                          @RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
                                          @RequestParam(value = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false)String sortOrder) throws Exception {
        NoteResponse notes = this.noteService.getAllNotes( pageNumber, pageSize, sortBy, sortOrder);
        if (notes != null) {
            return new ResponseEntity<>(notes, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>(new ApiResponse("There is no notes", false), HttpStatusCode.valueOf(500));
        }
    }

    //search
    @GetMapping("/note/search/{keywords}")
    public ResponseEntity<?> searchNoteByTitle(
            @PathVariable("keywords") String keywords
    ){
        List<Note> result=this.noteService.searchNote(keywords);
        if (result != null) {
            return new ResponseEntity<>(result,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse("There is no notes", false), HttpStatusCode.valueOf(500));
        }
    }


    @PostMapping("/user/{uId}/dep/{depId}/sem/{semId}/cou/{couId}/note/{noteId}/file/upload")
    public ResponseEntity<?> uploadNoteFile(@PathVariable("noteId") Long noteId,
                                                       @PathVariable("uId")Long uId,
                                                       @PathVariable("depId")Long depId,
                                                       @PathVariable("semId")Long semId,
                                                       @PathVariable("couId")Long couId,
                                                       @RequestParam("file") MultipartFile file) throws IOException {

        Note note=this.noteService.getNoteById(noteId);
        String uploadFileName= this.fileStorageService.uploadFile(path,file);

        note.setFileName(uploadFileName);
        Note note1=this.noteService.updateNote(uId,depId,semId,couId,noteId,note);
        return new ResponseEntity<>(note1,HttpStatus.OK);
    }

    @GetMapping(value="/note/{noteId}/file/{fileName}",produces= MediaType.IMAGE_JPEG_VALUE)
    public void serveImageController(
            @PathVariable("noteId")Long noteId,
            @PathVariable("fileName")String fileName,
            HttpServletResponse httpServletResponse
    ) throws IOException {

        Optional<Note> findById = this.noteRepositories.findById(noteId);
        Note note=findById.get();

        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        InputStream serveFile = this.fileStorageService.getResource(path,fileName);
        StreamUtils.copy(serveFile,httpServletResponse.getOutputStream());
    }







}
