package jku.ce.location;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_location")
public class Location {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;
    @ManyToOne
    @JoinColumn(name = "federal_state_id")
    private FederalState federalState;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

}
