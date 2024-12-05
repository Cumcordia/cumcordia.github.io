package kz.goldenfish.goldenfish.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BookingRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private int adults;
    private int children;
    private Long cabinId;

    // getters and setters
}
