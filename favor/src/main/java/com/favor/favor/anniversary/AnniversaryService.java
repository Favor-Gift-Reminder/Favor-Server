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
import java.util.stream.Collectors;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnniversaryService {

    private final AnniversaryRepository anniversaryRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public AnniversaryResponseDto createAnniversary(Long userNo, AnniversaryRequestDto anniversaryRequestDto){
        isExistingUserNo(userNo);
        User user = findUserByUserNo(userNo);

        Anniversary anniversary = anniversaryRequestDto.toEntity(anniversaryRequestDto.getAnniversaryTitle(), user);
        anniversaryRepository.save(anniversary);

        return AnniversaryResponseDto.from(anniversary);
    }

    public AnniversaryResponseDto readAnniversary(Long anniversaryNo) {
        isExistingAnniversaryNo(anniversaryNo);
        Anniversary anniversary = findAnniversaryByAnniversaryNo(anniversaryNo);

        return AnniversaryResponseDto.from(anniversary);
    }

    @Transactional
    public AnniversaryResponseDto updateAnniversary(Long anniversaryNo, AnniversaryUpdateRequestDto anniversaryUpdateRequestDto){
        isExistingAnniversaryNo(anniversaryNo);
        Anniversary anniversary = findAnniversaryByAnniversaryNo(anniversaryNo);

        anniversary.updateAnniversaryTitle(anniversaryUpdateRequestDto.getAnniversaryTitle());
        anniversary.updateAnniversaryDate(LocalDate.parse(anniversaryUpdateRequestDto.getAnniversaryDate()));
        anniversary.updateCategory(anniversaryUpdateRequestDto.getAnniversaryCategory());

        anniversaryRepository.save(anniversary);

        return AnniversaryResponseDto.from(anniversary);
    }

    @Transactional
    public AnniversaryResponseDto updateIsPinned(Long anniversaryNo){
        isExistingAnniversaryNo(anniversaryNo);
        Anniversary anniversary = findAnniversaryByAnniversaryNo(anniversaryNo);

        anniversary.updateIsPinned(anniversary.getIsPinned() == true ? false : true);
        anniversaryRepository.save(anniversary);

        return AnniversaryResponseDto.from(anniversary);
    }

//    @Transactional
//    public void deleteAnniversary(Long anniversaryNo){
//        List<Friend> friendList = findAnniversaryByAnniversaryNo(anniversaryNo).getUser().getFriendList();
//        for(Friend friend : friendList){
//            if(friend.getAnniversaryNoList().contains(anniversaryNo)){
//                friend.getAnniversaryNoList().remove(anniversaryNo);
//                friendRepository.save(friend);
//            }
//        }
//
//        anniversaryRepository.deleteByAnniversaryNo(anniversaryNo);
//    }

    @Transactional
    public void deleteAnniversary(Long anniversaryNo){
        List<Friend> updateRequiredFriends = findAnniversaryByAnniversaryNo(anniversaryNo).getUser().getFriendList().stream()
                .filter(friend -> friend.getAnniversaryNoList().contains(anniversaryNo))
                .peek(friend -> friend.getAnniversaryNoList().remove(anniversaryNo))
                .collect(Collectors.toList());

        friendRepository.saveAll(updateRequiredFriends);
        anniversaryRepository.deleteByAnniversaryNo(anniversaryNo);
    }

    public List<AnniversaryResponseDto> readAll(){
        return anniversaryRepository.findAll().stream()
                .map(AnniversaryResponseDto::from)
                .collect(Collectors.toList());
    }

    private User findUserByUserNo(Long userNo){
        User user;
        try{
            user = userRepository.findByUserNo(userNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, USER_NOT_FOUND);
        }
        return user;
    }

    private Anniversary findAnniversaryByAnniversaryNo(Long anniversaryNo){
        Anniversary anniversary;
        try{
            anniversary = anniversaryRepository.findAnniversaryByAnniversaryNo(anniversaryNo).orElseThrow(
                    () -> new RuntimeException()
            );
        } catch (RuntimeException e){
            throw new CustomException(e, ANNIVERSARY_NOT_FOUND);
        }
        return anniversary;
    }


    private void isExistingAnniversaryNo(Long anniversaryNo){
        Boolean isExistingNo;
        try{
            isExistingNo = anniversaryRepository.existsByAnniversaryNo(anniversaryNo);
        }catch(RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
        if(!isExistingNo){
            throw new CustomException(null, ANNIVERSARY_NOT_FOUND);
        }
    }

    private void isExistingUserNo (Long userNo){
        Boolean isExistingNo;
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
