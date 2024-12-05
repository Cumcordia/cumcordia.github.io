package kz.goldenfish.goldenfish.repository;

import kz.goldenfish.goldenfish.model.Booking;
import kz.goldenfish.goldenfish.model.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStartDateBetweenOrEndDateBetween(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2);

    @Query("SELECT b FROM Booking b WHERE b.status IN ('PENDING')")
    List<Booking> findAllByStatusPending(Booking.Status status);

    List<Booking> findByCabinAndStatusIn(Cabin cabin, List<String> statuses);

    @Query("SELECT b.cabin FROM Booking b WHERE " +
            "(b.startDate < :endDate AND b.endDate > :startDate)")
    List<Cabin> findBookedCabins(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);


    @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    WHERE b.cabin.id = :cabinId
      AND b.status IN (:statuses)
      AND ((b.startDate <= :endDate AND b.endDate >= :startDate))
""")
    boolean existsByCabinAndStatusInAndDatesOverlap(
            @Param("cabinId") Long cabinId,
            @Param("statuses") List<Booking.Status> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
