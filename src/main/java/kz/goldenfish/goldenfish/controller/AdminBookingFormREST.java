package kz.goldenfish.goldenfish.controller;

import kz.goldenfish.goldenfish.model.Booking;
import kz.goldenfish.goldenfish.model.BookingDTO;
import kz.goldenfish.goldenfish.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/bookings")
public class AdminBookingFormREST {

    private final BookingService bookingService;

    public AdminBookingFormREST(BookingService bookingService) {
        this.bookingService = bookingService;
    }

/*    // Получение всех ожидающих бронирований
    @GetMapping("/pending")
    public List<Booking> getPendingBookings() {
        return bookingService.findBookingByStatus("PENDING");
    }*/

    // Возвращаем HTML-страницу
    @GetMapping("/pending")
    public String getPendingBookingsPage(Model model) {
        model.addAttribute("_csrf", null); // Если используете Spring Security, здесь добавьте CSRF-токен
        return "admin-dashboard";
    }

    // Возвращаем список броней со статусом PENDING
    @GetMapping("/pending-data")
    @ResponseBody
    public List<BookingDTO> getPendingBookingsData() {
        return bookingService.findPendingBookings();
    }


    // Подтверждение бронирования
    @PostMapping("/approve")
    public ResponseEntity<String> approveBooking(@RequestBody Map<String, Long> request) {
        Long bookingId = request.get("id");
        if (bookingId == null) {
            return ResponseEntity.badRequest().body("ID брони не указан.");
        }

        try {
            bookingService.approveBooking(bookingId);
            return ResponseEntity.ok("Бронь подтверждена.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка подтверждения брони.");
        }
    }

    // Отклонение бронирования
    @PostMapping("/reject")
    public ResponseEntity<String> rejectBooking(@RequestBody Map<String, Long> request) {
        Long bookingId = request.get("id");
        if (bookingId == null) {
            return ResponseEntity.badRequest().body("ID брони не указан.");
        }

        try {
            bookingService.rejectBooking(bookingId);
            return ResponseEntity.ok("Бронь отклонена.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка отклонения брони.");
        }
    }
}
