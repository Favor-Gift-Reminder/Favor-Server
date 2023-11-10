package com.favor.favor.anniversary;

import com.favor.favor.exception.CustomException;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendRepository;
import com.favor.favor.user.User;
import com.favor.favor.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnniversaryService {

    private final AnniversaryRepository anniversaryRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public Anniversary createAnniversary(AnniversaryRequestDto anniversaryRequestDto, Long userNo){
        User user = findUserByUserNo(userNo);
        Anniversary anniversary = anniversaryRepository.save(anniversaryRequestDto.toEntity(anniversaryRequestDto.getAnniversaryTitle(), user));
        return anniversaryRepository.save(anniversary);
    }

    @Transactional
    public void updateAnniversary(AnniversaryUpdateRequestDto dto, Anniversary anniversary){
        anniversary.setAnniversaryTitle(dto.getAnniversaryTitle());
        anniversary.setAnniversaryDate(LocalDate.parse(dto.getAnniversaryDate()));
        anniversary.setCategory(dto.getAnniversaryCategory());

        anniversaryRepository.save(anniversary);
    }

    @Transactional
    public void updateIsPinned(Anniversary anniversary){
        anniversary.setIsPinned(anniversary.getIsPinned() == true ? false : true);
        anniversaryRepository.save(anniversary);
    }

    @Transactional
    public void deleteAnniversary(Long anniversaryNo){
        List<Friend> friendList = findAnniversaryByAnniversaryNo(anniversaryNo).getUser().getFriendList();
        for(Friend friend : friendList){
            if(friend.getAnniversaryNoList().contains(anniversaryNo)){
                friend.getAnniversaryNoList().remove(anniversaryNo);
                friendRepository.save(friend);
            }
        }

        anniversaryRepository.deleteByAnniversaryNo(anniversaryNo);
    }

    public List<AnniversaryResponseDto> readAll(){
        List<AnniversaryResponseDto> a_List = new ArrayList<>();
        for(Anniversary a : anniversaryRepository.findAll()){
            AnniversaryResponseDto dto = new AnniversaryResponseDto(a);
            a_List.add(dto);
        }
        return a_List;
    }


    public User findUserByUserNo(Long userNo){
        User user = null;
        try{
            user = userRepository.findByUserNo(userNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }
    public Anniversary findAnniversaryByAnniversaryNo(Long anniversaryNo){
        Anniversary anniversary = null;
        try{
            anniversary = anniversaryRepository.findAnniversaryByAnniversaryNo(anniversaryNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, ANNIVERSARY_NOT_FOUND);
        }
        return anniversary;
    }


    public AnniversaryResponseDto returnDto(Anniversary anniversary){
        return new AnniversaryResponseDto(anniversary);
    }

    public void isExistingAnniversaryNo(Long anniversaryNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = anniversaryRepository.existsByAnniversaryNo(anniversaryNo);
        }catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, ANNIVERSARY_NOT_FOUND);
        }
    }

    public void isExistingUserNo (Long userNo){
        Boolean isExistingNo = null;
        try{
            isExistingNo = userRepository.existsByUserNo(userNo);
        } catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, USER_NOT_FOUND);
        }
    }
}
