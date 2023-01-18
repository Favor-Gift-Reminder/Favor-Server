package com.favor.favor.Dto.UserDto;

import com.favor.favor.Common.FavorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name;
    private String userid;
    private List<FavorType> favorList = new ArrayList<>();
    private Map<String, LocalDate> eventList = new HashMap<>();
}
