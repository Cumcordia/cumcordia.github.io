package kz.goldenfish.goldenfish.controller;

import kz.goldenfish.goldenfish.model.Booking;
import kz.goldenfish.goldenfish.model.BookingRequest;
import kz.goldenfish.goldenfish.model.Cabin;
import kz.goldenfish.goldenfish.model.CabinDTO;
import kz.goldenfish.goldenfish.repository.CabinRepository;
import kz.goldenfish.goldenfish.service.BookingService;
import kz.goldenfish.goldenfish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private CabinRepository cabinRepository;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, CabinRepository cabinRepository) {
        this.cabinRepository = cabinRepository;
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user", authentication.getName());
        } else {
            model.addAttribute("user", null);
        }

        return "confirmation";
    }

    @GetMapping
    public String showBookingForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            model.addAttribute("user", authentication.getName());
        } else {
            model.addAttribute("user", null);
        }

        return "booking";
    }

    @GetMapping("/getAvailableCabins")
    @ResponseBody
    public List<CabinDTO> getAvailableCabins(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<Cabin> allCabins = cabinRepository.findAll();
        List<CabinDTO> cabinDTOs = allCabins.stream()
                .map(cabin -> new CabinDTO(cabin.getId(), cabin.getName()))
                .collect(Collectors.toList());
        return cabinDTOs;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
