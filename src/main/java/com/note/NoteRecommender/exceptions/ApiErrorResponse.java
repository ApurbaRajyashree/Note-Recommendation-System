package com.note.NoteRecommender.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiErrorResponse {
    private String errorId;
    private Integer status;
    private String message;
    private List<String> errors;
    private String path;

}
