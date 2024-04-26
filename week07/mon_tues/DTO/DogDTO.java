package week07.mon_tues.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DogDTO {
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String name;
    private String breed;
    private int gender;
    private int age;
}
