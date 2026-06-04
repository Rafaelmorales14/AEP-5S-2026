package aep.SOSsego.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "tb_citizen")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CitizenModel extends UserModel{
    @OneToMany(mappedBy = "citizen")
    @JsonManagedReference
    private List<SolicitationModel> solicitation;
}
