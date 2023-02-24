package com.note.NoteRecommender.helper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FileResponse {

    private String fileName;
    private String url;
    private String fileType;
    private long fileSize;
}
