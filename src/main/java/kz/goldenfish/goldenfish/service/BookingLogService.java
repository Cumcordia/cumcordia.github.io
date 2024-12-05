package kz.goldenfish.goldenfish.service;
import kz.goldenfish.goldenfish.model.BookingLog;
import kz.goldenfish.goldenfish.repository.BookingLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingLogService {

    private final BookingLogRepository bookingLogRepository;

    @Autowired
    public BookingLogService(BookingLogRepository bookingLogRepository) {
        this.bookingLogRepository = bookingLogRepository;
    }

    public BookingLog createBookingLog(BookingLog bookingLog) {
        bookingLog.setTimestamp(LocalDateTime.now());
        return bookingLogRepository.save(bookingLog);
    }

}
