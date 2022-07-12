package challenge;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ReactiveExample {

    public static final int VALOR_PERMITIDO = 15;
    private  Flux<Estudiante> estudianteList;

    public ReactiveExample() {
        //TODO: convertir los estudiantes a un Flux

       estudianteList = Flux.fromIterable(List.of(
                new Estudiante("raul", 30, List.of(1, 2, 1, 4, 5)),
                new Estudiante("andres", 35, List.of(4, 2, 4, 3, 5)),
                new Estudiante("juan", 75, List.of(3, 2, 4, 5, 5)),
                new Estudiante("pedro", 80, List.of(5, 5, 4, 5, 5)),
                new Estudiante("santiago", 40, List.of(4, 5, 4, 5, 5))
        ));

    }

    //TODO: suma de puntajes
    public Mono<Integer> sumaDePuntajes() {
        return estudianteList
                .map(mapeoDeEstudianteAPuntaje())
                .reduce(Integer::sum);
    }

    private Function<Estudiante, Integer> mapeoDeEstudianteAPuntaje() {
        return Estudiante::getPuntaje;
    }

    //TODO: mayor puntaje de estudiante
    public Flux<Estudiante> mayorPuntajeDeEstudiante(int limit) {
        return estudianteList
                .sort(Comparator.comparing(Estudiante::getPuntaje).reversed())
                .take(limit);
    }

    //TODO: total de asisntencias de estudiantes con mayor puntaje basado en un  valor
    public Mono<Integer> totalDeAsisntenciasDeEstudiantesConMayorPuntajeDe(int valor) {
        return estudianteList
                .filter(estudiante -> estudiante.getPuntaje()>70)
                .map(estudiante -> estudiante.getAsistencias())
                .flatMap(integers -> Flux.fromIterable(integers))
                .reduce(Integer::sum);
    }

    //TODO: el estudiante tiene asistencias correctas
    public Mono<Boolean> elEstudianteTieneAsistenciasCorrectas(Estudiante estudiante) {
        return Mono.just(estudiante)
                .filter(estudiante1 -> estudiante1.getAsistencias()
                        .stream()
                        .reduce(0, Integer::sum) >= VALOR_PERMITIDO)
                .map(estudiante1 -> true)
                .switchIfEmpty(Mono.just(false));
    }

    //TODO: promedio de puntajes por estudiantes
    public Mono<Double> promedioDePuntajesPorEstudiantes() {
        return estudianteList
                .collect(Collectors.averagingDouble(Estudiante::getPuntaje));
    }


    //TODO: los nombres de estudiante con puntaje mayor a un valor
    public Flux<String> losNombresDeEstudianteConPuntajeMayorA(int valor) {
        return estudianteList
                .filter(estudiante -> estudiante.getPuntaje()>valor)
                .map(Estudiante::getNombre);
    }

    //TODO: estudiantes aprovados
    public Flux<String> estudiantesAprovados(){
        return estudianteList
                .map(this::aprobar)
                .filter(Estudiante::isAprobado)
                .map(Estudiante::getNombre);
    }

    public Estudiante aprobar(Estudiante estudiante){
        var est = new Estudiante(estudiante.getNombre(),estudiante.getPuntaje(),estudiante.getAsistencias());
        if(estudiante.getPuntaje()>=75){
            est.setAprobado(true);
        }
        return est;
    }

}
