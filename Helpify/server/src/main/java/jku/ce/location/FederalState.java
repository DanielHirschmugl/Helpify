package jku.ce.location;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_federal_state")
public class FederalState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @OneToMany(mappedBy = "federalState", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Region> regions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FederalState that = (FederalState) o;
        return Objects.equals(name, that.name) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

