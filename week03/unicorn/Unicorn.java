package week03.unicorn;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
public class Unicorn {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int id;
    @Size(min=1, max=30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(name = "age")
    private int age;
    //// limits Doesn't actually work.
    @Min(1)
    @Max(100)
    @Column(name = "power_strength")
    private float powerStrength;

    public Unicorn(String name, int age, float powerStrength) {
        this.name = name;
        this.age = age;
        this.powerStrength = powerStrength;
    }

    public Unicorn() {
        this.name = "unnamed";
        this.age = -1;
        this.powerStrength = -1;
    }

    @Override
    public String toString() {
        return "Unicorn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", powerStrength=" + powerStrength +
                '}';
    }
}
