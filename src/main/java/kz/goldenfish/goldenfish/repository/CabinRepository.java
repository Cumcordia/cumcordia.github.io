package kz.goldenfish.goldenfish.repository;

import kz.goldenfish.goldenfish.model.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CabinRepository extends JpaRepository<Cabin, Long> {

    List<Cabin> findAll();
}
