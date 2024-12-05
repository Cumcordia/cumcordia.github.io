package kz.goldenfish.goldenfish.repository;
import kz.goldenfish.goldenfish.model.BookingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingLogRepository extends JpaRepository<BookingLog, Long> {
}
