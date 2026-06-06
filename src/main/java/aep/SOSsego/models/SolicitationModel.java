package aep.SOSsego.models;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.PriorityEnum;
import aep.SOSsego.enums.StatusSolicitationEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(unique = true)
    private String protocol;

    @Size(min = 10, message = "A descrição deve ter no mínimo 10 caracteres")
    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Enumerated(EnumType.STRING)
    private StatusSolicitationEnum currentlyStatus;

    @Size(min = 10, message = "Endereço muito curto (mínimo 10 caracteres)")
    private String address;
    private Boolean isAnonymous;

    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    @JsonBackReference("citizen-solicitation")
    private CitizenModel citizen;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime dateSLA;

    @OneToMany(mappedBy = "solicitation")
    @JsonManagedReference("solicitation-history")
    private List<StatusHistoryModel> statusHistory;
}
