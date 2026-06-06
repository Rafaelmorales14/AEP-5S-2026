package aep.SOSsego.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "tb_publicServant")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PublicServantModel extends UserModel{
    private String registration;
    private String position;
    @OneToMany(mappedBy = "publicServant")
    @JsonManagedReference("public-servant")
    private List<StatusHistoryModel> statusHistory;
}
