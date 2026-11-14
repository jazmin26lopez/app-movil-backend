package com.bebedero.msinfraestructura.config;

import com.bebedero.msinfraestructura.model.Galpon;
import com.bebedero.msinfraestructura.model.Granja;
import com.bebedero.msinfraestructura.model.LineaBebedero;
import com.bebedero.msinfraestructura.repository.GalponRepository;
import com.bebedero.msinfraestructura.repository.GranjaRepository;
import com.bebedero.msinfraestructura.repository.LineaBebederoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Siembra datos de ejemplo al arrancar, igual que hacia ApiSimulada en la app,
// para que el microservicio nunca empiece vacio (comodo para probar y para la defensa).
@Component
public class DatosIniciales implements CommandLineRunner {

    private final GranjaRepository granjaRepository;
    private final GalponRepository galponRepository;
    private final LineaBebederoRepository lineaRepository;

    public DatosIniciales(
            GranjaRepository granjaRepository,
            GalponRepository galponRepository,
            LineaBebederoRepository lineaRepository
    ) {
        this.granjaRepository = granjaRepository;
        this.galponRepository = galponRepository;
        this.lineaRepository = lineaRepository;
    }

    @Override
    public void run(String... args) {
        if (granjaRepository.count() > 0) {
            return;
        }

        String ahora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        // Granja 1: 3 galpones.
        Granja granja1 = granjaRepository.save(new Granja("Granja 1"));
        crearGalponConLineas(granja1, "Galpón 1", ahora, 18.0, 25.0);
        crearGalponConLineas(granja1, "Galpón 2", ahora, 19.5, 13.0);
        crearGalponConLineas(granja1, "Galpón 3", ahora, 32.0, 20.0);

        // Granja 2: 2 galpones.
        Granja granja2 = granjaRepository.save(new Granja("Granja 2"));
        crearGalponConLineas(granja2, "Galpón 1", ahora, 17.0, 22.5);
        crearGalponConLineas(granja2, "Galpón 2", ahora, 29.0, 16.0);

        // Granja 3: 4 galpones.
        Granja granja3 = granjaRepository.save(new Granja("Granja 3"));
        crearGalponConLineas(granja3, "Galpón 1", ahora, 20.0, 14.5);
        crearGalponConLineas(granja3, "Galpón 2", ahora, 21.0, 24.0);
        crearGalponConLineas(granja3, "Galpón 3", ahora, 33.0, 19.0);
        crearGalponConLineas(granja3, "Galpón 4", ahora, 18.5, 26.0);
    }

    private void crearGalponConLineas(Granja granja, String nombreGalpon, String fecha, double temp1, double temp2) {
        Galpon galpon = galponRepository.save(new Galpon(nombreGalpon, granja));
        lineaRepository.save(new LineaBebedero("Línea 1", galpon, temp1, fecha));
        lineaRepository.save(new LineaBebedero("Línea 2", galpon, temp2, fecha));
    }
}
