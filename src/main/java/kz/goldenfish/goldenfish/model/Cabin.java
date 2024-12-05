package kz.goldenfish.goldenfish.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "cabins")
public class Cabin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, insertable = false, updatable = false)
    private Integer capacity = 10;

    @OneToMany(mappedBy = "cabin")
    private List<Booking> bookings;
}
