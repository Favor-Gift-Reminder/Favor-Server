package com.favor.favor.Photo;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {
    public List<Photo> parseFileInfo(
            Long photoNo,
            List<MultipartFile> multipartFiles
    ) throws Exception{

        //반환할 파일 리스트
        List<Photo> fileList = new ArrayList<>();


    }
}
