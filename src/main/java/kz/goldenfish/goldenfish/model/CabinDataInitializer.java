package kz.goldenfish.goldenfish.model;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CabinDataInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initCabins(){
        String check = "SELECT count(*) FROM cabins";
        Integer cunt = jdbcTemplate.queryForObject(check, Integer.class);

        if (cunt == 0){
            String insertQuery = """
                    INSERT INTO cabins (id, name, capacity) VALUES
                    (1, 'Cabin Alpha', 10),
                    (2, 'Cabin Beta', 10),
                    (3, 'Cabin Gamma', 10),
                    (4, 'Cabin Delta', 10),
                    (5, 'Cabin Epsilon', 10),
                    (6, 'Cabin Zeta', 10),
                    (7, 'Cabin Eta', 10),
                    (8, 'Cabin Theta', 10),
                    (9, 'Cabin Iota', 10),
                    (10, 'Cabin Kappa', 10);
                    """;
            jdbcTemplate.execute(insertQuery);
        }
    }
}
