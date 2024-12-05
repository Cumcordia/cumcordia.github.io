package kz.goldenfish.goldenfish.service;
import jakarta.transaction.Transactional;
import kz.goldenfish.goldenfish.model.Booking;
import kz.goldenfish.goldenfish.model.BookingDTO;
import kz.goldenfish.goldenfish.model.Cabin;
import kz.goldenfish.goldenfish.repository.BookingRepository;
import kz.goldenfish.goldenfish.repository.CabinRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CabinRepository cabinRepository;

    public BookingService(BookingRepository bookingRepository, CabinRepository cabinRepository) {
        this.bookingRepository = bookingRepository;
        this.cabinRepository = cabinRepository;
    }

    @Transactional
    public List<Cabin> getAvailableCabins(LocalDate startDate, LocalDate endDate) {
        List<Cabin> allCabins = cabinRepository.findAll();

        // Получаем все бронирования на указанный период
        List<Booking> conflictingBookings = bookingRepository.findByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate);

        // Составляем список доступных домиков
        Set<Cabin> unavailableCabins = conflictingBookings.stream()
                .map(Booking::getCabin)
                .collect(Collectors.toSet());

        return allCabins.stream()
                .filter(cabin -> !unavailableCabins.contains(cabin))
                .collect(Collectors.toList());
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void approveBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Бронь не найдена."));
        booking.setStatus(Booking.Status.CONFIRMED); // Установите нужный статус
        bookingRepository.save(booking);
    }

    public void rejectBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Бронь не найдена"));
        booking.setStatus(Booking.Status.REJECTED);
        bookingRepository.save(booking);
    }

    public List<Booking> findBookingByStatus(String pending) {
        return bookingRepository.findAllByStatusPending(Booking.Status.PENDING);
    }

    public List<BookingDTO> findPendingBookings() {
        List<Booking> bookings = bookingRepository.findAllByStatusPending(Booking.Status.PENDING);
        return bookings.stream().map(booking -> {
            BookingDTO dto = new BookingDTO();
            dto.setId(booking.getId());
            dto.setUsername(booking.getUser().getUsername());
            dto.setCabin(booking.getCabin().getName());
            dto.setStartDate(booking.getStartDate().toString());
            dto.setEndDate(booking.getEndDate().toString());
            dto.setStatus(String.valueOf(booking.getStatus()));
            dto.setTotalPeople(booking.getTotalPeople());
            return dto;
        }).collect(Collectors.toList());
    }
}
