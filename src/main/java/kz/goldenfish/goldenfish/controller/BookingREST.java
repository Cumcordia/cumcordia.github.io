package kz.goldenfish.goldenfish.controller;

import kz.goldenfish.goldenfish.model.*;
import kz.goldenfish.goldenfish.repository.BookingRepository;
import kz.goldenfish.goldenfish.repository.CabinRepository;
import kz.goldenfish.goldenfish.service.BookingService;
import kz.goldenfish.goldenfish.service.CabinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/bookings")
public class BookingREST {

    @Autowired
    private CabinRepository cabinRepository;

    @Autowired
    private BookingService bookingService;
    private CabinService cabinService;
    private BookingRepository bookingRepository;

    public BookingREST(CabinService cabinService, BookingRepository bookingRepository) {
        this.cabinService = cabinService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookCabin(@RequestBody BookingRequest bookingRequest) {
        try {
            // Получаем даты и проверяем их корректность
            LocalDate startDate = bookingRequest.getStartDate();
            LocalDate endDate = bookingRequest.getEndDate();

            if (startDate.isAfter(endDate)) {
                return ResponseEntity.badRequest().body(new ResponseMessage("error", "Дата заезда не может быть позже даты выезда."));
            }

            // Проверяем доступность домиков
            List<Cabin> availableCabins = cabinService.findAvailableCabins(startDate, endDate);

            if (availableCabins.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseMessage("error", "Нет свободных домиков на выбранные даты."));
            }

            // Проверяем брони на выбранные даты
            for (Cabin cabin : availableCabins) {
                boolean hasPendingOrConfirmedBooking = bookingRepository.existsByCabinAndStatusInAndDatesOverlap(
                        cabin.getId(),
                        List.of(Booking.Status.PENDING, Booking.Status.CONFIRMED),
                        startDate,
                        endDate
                );

                if (!hasPendingOrConfirmedBooking) {
                    // Если домик свободен, создаем бронирование
                    Booking booking = new Booking();
                    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                    booking.setStartDate(startDate);
                    booking.setEndDate(endDate);
                    booking.setTotalPeople(bookingRequest.getAdults() + bookingRequest.getChildren());
                    booking.setStatus(Booking.Status.PENDING);
                    booking.setCreatedAt(LocalDateTime.now());
                    booking.setUser(user);
                    booking.setCabin(cabin);

                    bookingService.saveBooking(booking);

                    return ResponseEntity.ok(new ResponseMessage("success", "Бронирование успешно зарегистрировано!"));
                }
            }

            // Если нет свободных домиков
            return ResponseEntity.badRequest().body(new ResponseMessage("error", "Нет доступных домиков на выбранные даты."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage("error", "Ошибка при бронировании: " + e.getMessage()));
        }
    }


}
