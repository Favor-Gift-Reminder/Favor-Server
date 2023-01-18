package com.favor.favor.Domain;

import com.favor.favor.Common.FavorType;
import com.favor.favor.Common.Role;
import com.favor.favor.Common.TimeStamped;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "이름을 입럭해주세요")
    private String name;

    @NotNull
    private String role;



    @Nullable
    private LocalDate birth;



    //취향 목록
    @ElementCollection
    private List<FavorType> favorList;

    //기념일 목록
    @ElementCollection
    private Map<String, LocalDate> eventList = new HashMap<>();

}
