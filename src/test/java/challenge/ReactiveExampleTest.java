package challenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class ReactiveExampleTest {

    @InjectMocks
    ReactiveExample reactiveExample;


    @Test
    void sumaDePuntajes(){
        StepVerifier
                .create(reactiveExample.sumaDePuntajes())
                .expectNext(260)
                .expectComplete()
                .verify();
    }

    @Test
    void mayorPuntajeDeEstudiante(){
        StepVerifier
                .create(reactiveExample.mayorPuntajeDeEstudiante(1))
                .expectNextMatches(estudiante -> estudiante.getNombre().equals("pedro"))
                .expectComplete()
                .verify();
    }

    @Test
    void totalDeAsisntenciasDeEstudiantesComMayorPuntajeDe(){
        StepVerifier
                .create(reactiveExample.totalDeAsisntenciasDeEstudiantesConMayorPuntajeDe(75))
                .expectNext(43)
                .expectComplete()
                .verify();
    }

    @Test
    void elEstudianteTieneAsistenciasCorrectas(){
        var est = new Estudiante("raul", 30, List.of(5,2,1,4,5));
        StepVerifier
                .create(reactiveExample.elEstudianteTieneAsistenciasCorrectas(est))
                .expectNext(true)
                .expectComplete()
                .verify();
    }

    @Test
    void promedioDePuntajesPorEstudiantes(){
        StepVerifier
                .create(reactiveExample.promedioDePuntajesPorEstudiantes())
                .expectNext(52.0)
                .expectComplete()
                .verify();
    }

    @Test
    void estudiantesAprovados(){
        StepVerifier
                .create(reactiveExample.estudiantesAprovados())
                .expectNext("juan","pedro")
                .expectComplete()
                .verify();
    }
}