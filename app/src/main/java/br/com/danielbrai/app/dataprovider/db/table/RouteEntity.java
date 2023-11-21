package br.com.danielbrai.app.dataprovider.db.table;

import br.com.danielbrai.core.domain.enums.Operations;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "route")
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORIGIN")
    private CoordinateEntity origin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DESTINATION")
    private CoordinateEntity destination;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private Operations operation;

    @ManyToOne
//    @JsonIgnore
//    @EqualsAndHashCode.Exclude
    private VoyageEntity voyage;
}