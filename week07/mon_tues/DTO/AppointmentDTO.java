package week07.mon_tues.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentDTO {
    @Setter(AccessLevel.NONE)
    private Integer id;
    private String name;
    private String doctor;
    private LocalDateTime time;
    @Setter(AccessLevel.NONE)
    private List<String> allergies;
    @Setter(AccessLevel.NONE)
    private List<String> medications;

    public AppointmentDTO addAllergy(String str){
        synchronized (allergies){
            if(!allergies.contains(str))
                allergies.add(str);
        }
        return this;
    }
    public AppointmentDTO removeAllergy(String str){
        synchronized (allergies){
            if(allergies.contains(str))
                allergies.remove(str);
        }
        return this;
    }
    public AppointmentDTO addMedication(String str){
        synchronized (medications){
            if(!medications.contains(str))
                medications.add(str);
        }
        return this;
    }
    public AppointmentDTO removeMedication(String str){
        synchronized (medications){
            if(medications.contains(str))
                medications.remove(str);
        }
        return this;
    }
}
