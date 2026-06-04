package aep.SOSsego.models;

import aep.SOSsego.enums.CategoryEnum;
import aep.SOSsego.enums.PriorityEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    private String description;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private String address;
    private Boolean isAnonymous;

    @Enumerated(EnumType.STRING)
    private PriorityEnum priority;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    @JsonBackReference
    private CitizenModel citizen;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime dateSLA;

    @OneToMany(mappedBy = "solicitation")
    private List<StatusHistoryModel> statusHistory;
}
