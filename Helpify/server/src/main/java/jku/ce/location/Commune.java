package jku.ce.location;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_commune")
public class Commune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commune commune = (Commune) o;
        return Objects.equals(name, commune.name) && Objects.equals(postalCode, commune.postalCode) && Objects.equals(region, commune.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

