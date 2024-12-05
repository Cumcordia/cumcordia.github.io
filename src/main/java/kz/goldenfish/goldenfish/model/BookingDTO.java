package kz.goldenfish.goldenfish.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingDTO {
    private Long id;
    private String username;
    private String cabin;
    private String startDate;
    private String endDate;
    private String status;
    private int totalPeople;
}