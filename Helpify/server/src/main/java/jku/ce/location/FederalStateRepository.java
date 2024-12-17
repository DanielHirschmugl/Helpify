package jku.ce.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FederalStateRepository extends JpaRepository<FederalState, Long> {
    Optional<FederalState> findById(Long id);
    Optional<FederalState> findByName(String name);
}
