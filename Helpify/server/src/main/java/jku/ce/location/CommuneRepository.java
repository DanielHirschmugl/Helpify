package jku.ce.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    Optional<Commune> findById(Long id);
    Optional<Commune> findByName(String name);
    Optional<Commune> findByPostalCode(int postalCode);
}
