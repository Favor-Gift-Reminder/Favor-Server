package com.favor.favor.Photo;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {
    public List<Photo> parseFileInfo(
            Long photoIdx,
            List<MultipartFile> multipartFiles
    ) throws Exception {

        //반환할 파일 리스트
        List<Photo> fileList = new ArrayList<>();

        //파일이 빈 것이 들어올 경우 빈 것 반환
        if (multipartFiles.isEmpty()) {
            return fileList;
        }
        //파일 이름을 날짜로 바꾸어서 저장
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        //파일이 빈 것이 들어오면 빈 것 반환
        if (multipartFiles.isEmpty()) {
            return fileList;
        }

        //프로젝트 폴더 저장 위해 절대경로 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "\\";

        //경로 지정하고 그곳에다가 저장
        String path = "images/" + current_date;
        File file = new File(path);

        //저장할 디렉토리가 존재하지 않을 경우
        if (!file.exists()) {
            file.mkdirs(); //상위 디렉토리까지 생성
        }

        //파일 핸들링 시작
        for (MultipartFile multipartFile : multipartFiles) {
            //파일이 비어있지 않을 때 작업 시작해야 오류 없음
            if (!multipartFile.isEmpty()) {
                //jpeg, png, gif만 처리할 예정
                String contentType = multipartFile.getContentType();
                String originalFileExtension;

                //확장자가 없다면 잘못된 파일
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = "png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = "gif";
                    }

                    //다른 파일명이면 아무것도 하지 않음
                    else {
                        break;
                    }
                }

                //각 이름 겹치면 안되므로 나노 초까지 동원해서 지정
                String new_file_name = System.nanoTime() + originalFileExtension;

                //생성 후 리스트에 추가
                Photo photo = Photo.builder()
                        .photoIdx(photoIdx)
                        .originalFileName((multipartFile.getOriginalFilename()))
                        .storedFileName(path + "/" + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();
                fileList.add(photo);

                //저장된 파일로 변경해 이를 보여줌
                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);
            }
        }
        return fileList;
    }
}
