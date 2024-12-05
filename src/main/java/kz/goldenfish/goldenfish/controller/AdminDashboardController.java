package kz.goldenfish.goldenfish.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.goldenfish.goldenfish.model.Booking;
import kz.goldenfish.goldenfish.service.BookingService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin-dashboard")
public class AdminDashboardController {

    private BookingService bookingService;

    public AdminDashboardController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String manageBooking(Model model, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }

        //List<Booking> pendingBookings = bookingService.findBookingByStatus("PENDING");
        //model.addAttribute("pendingBookings", pendingBookings);

        if (csrfToken != null) {
            System.out.println("CSRF Token: " + csrfToken.getToken());
            System.out.println("CSRF Header: " + csrfToken.getHeaderName());
        }

        return "admin/admin-dashboard"; // Название шаблона
    }
}
