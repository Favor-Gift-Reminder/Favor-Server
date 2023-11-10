package com.favor.favor.gift;

import com.favor.favor.exception.CustomException;
import com.favor.favor.photo.GiftPhoto;
import com.favor.favor.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

import static com.favor.favor.exception.ExceptionCode.FILE_NOT_FOUND;
import static com.favor.favor.exception.ExceptionCode.SERVER_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftPhotoService {
    private final GiftRepository giftRepository;
    private final GiftService giftService;
    private final PhotoService photoService;

    //선물 사진 목록에 사진 추가
    @Transactional
    public Gift addGiftPhoto(Long giftNo, MultipartFile file){
        Gift gift = giftService.findGiftByGiftNo(giftNo);
        List<GiftPhoto> giftPhotoList = gift.getGiftPhotoList();

        String fileName = file.getOriginalFilename();
        String storedFileName = getGiftFileName(fileName);

        String giftPhotoUrl = photoService.uploadFileToS3(storedFileName, file);

        try{
            GiftPhoto giftPhoto = GiftPhoto.builder()
                    .photoUrl(giftPhotoUrl)
                    .build();

            giftPhotoList.add(giftPhoto);

        }catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }

        gift.setGiftPhotoList(giftPhotoList);
        return giftRepository.save(gift);
    }

    //선물 사진 목록 조회
    public List<GiftPhoto> getGiftPhotoList(Long giftNo){
        return giftService.findGiftByGiftNo(giftNo).getGiftPhotoList();
    }

    //선물 사진 삭제
    @Transactional
    public Gift deleteGiftPhoto(Long giftNo, String photoUrl){
        Gift gift = giftService.findGiftByGiftNo(giftNo);
        List<GiftPhoto> giftPhotoList = gift.getGiftPhotoList();

        GiftPhoto temp = null;

        for(GiftPhoto giftPhoto : giftPhotoList){
            if(photoUrl.equals(giftPhoto.getPhotoUrl())){
                temp = giftPhoto;
                String fileName = extractGiftPhotoFileName(photoUrl);
                photoService.deleteFileFromS3(fileName);
                break;
            }
        }
        giftPhotoList.remove(temp);

        return giftRepository.save(gift);
    }

    //선물 사진 이름 생성
    public String getGiftFileName(String fileName){
        return  "gift/"  + UUID.randomUUID()+ fileName.substring(fileName.lastIndexOf('.'));
    }

    //선물 사진 이름 추출
    public static String extractGiftPhotoFileName(String photoUrl) {
        String fileName;
        try {
            fileName = photoUrl.substring(photoUrl.indexOf("gift/"));
        } catch (Exception e) {
            throw new CustomException(e, FILE_NOT_FOUND);
        }

        return fileName;
    }
}
