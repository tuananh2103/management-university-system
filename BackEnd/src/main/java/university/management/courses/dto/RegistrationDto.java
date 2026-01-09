package university.management.courses.dto;


import java.util.List;

public record RegistrationDto(String regNumber,int semester,
    List<Integer> courseIds, String createdAt) {

}