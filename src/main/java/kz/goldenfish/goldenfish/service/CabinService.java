package kz.goldenfish.goldenfish.service;
import kz.goldenfish.goldenfish.model.Cabin;
import kz.goldenfish.goldenfish.repository.BookingRepository;
import kz.goldenfish.goldenfish.repository.CabinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CabinService {

    private final CabinRepository cabinRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public CabinService(CabinRepository cabinRepository, BookingRepository bookingRepository) {
        this.cabinRepository = cabinRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Cabin> findAvailableCabins(LocalDate startDate, LocalDate endDate) {
        // Получаем список занятых домиков
        List<Cabin> bookedCabins = bookingRepository.findBookedCabins(startDate, endDate);

        // Возвращаем все домики, которых нет в списке занятых
        return cabinRepository.findAll().stream()
                .filter(cabin -> !bookedCabins.contains(cabin))
                .collect(Collectors.toList());
    }
}
