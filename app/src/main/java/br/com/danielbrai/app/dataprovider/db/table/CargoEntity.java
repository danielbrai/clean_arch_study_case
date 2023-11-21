package br.com.danielbrai.app.dataprovider.db.table;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "cargo")
@AllArgsConstructor
@NoArgsConstructor
public class CargoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal capacity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_LOAD_LOCATION")
    private CoordinateEntity loadLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_UNLOAD_LOCATION")
    private CoordinateEntity unloadLocation;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private VoyageEntity voyage;
}