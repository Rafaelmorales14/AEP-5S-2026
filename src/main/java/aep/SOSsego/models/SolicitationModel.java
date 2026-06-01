package aep.SOSsego.models;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.PropertyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_solicitation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String protocol;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private String address;
    private Boolean isAnonymous;

    @Enumerated(EnumType.STRING)
    private PropertyEnum property;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private CitizenModel citizen;
    private LocalDateTime createdAt;
    private LocalDateTime dateSLA;

    @OneToMany(mappedBy = "solicitation")
    private List<StatusHistoryModel> statusHistory;
}
