package com.favor.favor.anniversary;

import com.favor.favor.exception.CustomException;
import com.favor.favor.member.User;
import com.favor.favor.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static com.favor.favor.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class AnniversaryService {

    private final AnniversaryRepository anniversaryRepository;
    private final UserRepository userRepository;

    public Anniversary createAnniversary(AnniversaryRequestDto anniversaryRequestDto, Long userNo){
        User user = findUserByUserNo(userNo);
        LocalDate localDate = returnLocalDate(anniversaryRequestDto.getAnniversaryDate());
        return anniversaryRepository.save(anniversaryRequestDto.toEntity(user, localDate));
    }

    public void updateAnniversary(AnniversaryUpdateRequestDto dto, Long anniversaryNo){
        Anniversary anniversary = findAnniversaryByanniversaryNo(anniversaryNo);

        anniversary.setAnniversaryTitle(dto.getAnniversaryTitle());
        LocalDate localDate = returnLocalDate(dto.getAnniversaryDate());
        anniversary.setAnniversaryDate(localDate);
        anniversary.setIsPinned(dto.getIsPinned());

        anniversaryRepository.save(anniversary);
    }

    public void deleteAnniversary(Long anniversaryNo){
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
    public Anniversary findAnniversaryByanniversaryNo(Long userNo){
        Anniversary anniversary = null;
        try{
            anniversary = anniversaryRepository.findAnniversaryByAnniversaryNo(userNo).orElseThrow(
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
    public LocalDate returnLocalDate(String dateString){
        String patternDate = "yyyy-MM-dd";
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternDate);
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date;

        } catch(DateTimeParseException e){
            throw new CustomException(e, DATE_INVALID);
        }
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
