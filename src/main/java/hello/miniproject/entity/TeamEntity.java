package hello.miniproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "team")
@Getter
@Setter
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    @OneToOne
    @JsonIgnore
    private EmployeeEntity manager = null;
    @OneToMany(mappedBy = "team",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EmployeeEntity> employees =  new ArrayList<>();

    public TeamEntity() {
    }

    public TeamEntity(String teamName) {
        this.teamName = teamName;
    }
}
