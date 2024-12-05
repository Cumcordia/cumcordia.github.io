package kz.goldenfish.goldenfish.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CabinDTO {
    private Long id;
    private String name;

    public CabinDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
