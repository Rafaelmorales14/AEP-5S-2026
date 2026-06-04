package aep.SOSsego.models;

import aep.SOSsego.enums.StatusSolicitationEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_statusHistory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHora;
    private StatusSolicitationEnum statusAnterior;
    private StatusSolicitationEnum statusNovo;
    private String comentarioObrigatorio;

    @ManyToOne
    @JoinColumn(name = "publicServant_id")
    private PublicServantModel publicServant;

    @ManyToOne
    @JoinColumn(name = "solicitation_id")
    private SolicitationModel solicitation;

}
