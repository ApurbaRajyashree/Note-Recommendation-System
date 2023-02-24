package com.note.NoteRecommender.helper;

import com.note.NoteRecommender.dto.NoteDto;
import com.note.NoteRecommender.entities.Note;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class NoteResponse {
    private List<NoteDto> contents;
    private int pageSize;
    private int pageNumber;
    private Long totalElements;
    private int totalPage;

}
