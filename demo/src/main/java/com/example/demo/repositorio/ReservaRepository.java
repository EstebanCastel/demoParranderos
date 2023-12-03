package com.example.demo.repositorio;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Subtract;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.modelo.Reserva;
import com.example.demo.modelo.PorcentajeOcupacion;
import java.util.List;
import java.util.Date;

import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public interface ReservaRepository extends MongoRepository<Reserva, String> {

    default List<PorcentajeOcupacion> calcularPorcentajeOcupacion(MongoTemplate mongoTemplate, Date fechaInicio, Date fechaFin) {
        MatchOperation matchOperation = match(Criteria.where("fechaLlegada").gte(fechaInicio).lt(fechaFin));

        ProjectionOperation projectionOperation = project()
            .and("habitacion._id").as("habitacionId")
            .and(Cond.newBuilder()
                 .when(Criteria.where("fechaLlegada").gte(fechaInicio))
                 .then("$fechaLlegada")
                 .otherwise(fechaInicio)).as("ajusteFechaLlegada")
            .and(Cond.newBuilder()
                 .when(Criteria.where("fechaSalida").lt(fechaFin))
                 .then("$fechaSalida")
                 .otherwise(fechaFin)).as("ajusteFechaSalida");

        ProjectionOperation diasOcupadosProjection = project()
        .andInclude("habitacionId")
        .and(Subtract.valueOf("ajusteFechaSalida").subtract("ajusteFechaLlegada"))
        .divide(1000 * 60 * 60 * 24)
        .as("diasOcupados");

        GroupOperation groupOperation = group("habitacionId").sum("diasOcupados").as("totalDiasOcupados");

        ProjectionOperation finalProjectionOperation = project()
        .and("_id").as("habitacionId")
        .andExpression("(totalDiasOcupados / 365) * 100").as("porcentajeOcupacion");

        Aggregation aggregation = newAggregation(matchOperation, projectionOperation, diasOcupadosProjection, groupOperation, finalProjectionOperation);
        AggregationResults<PorcentajeOcupacion> results = mongoTemplate.aggregate(aggregation, Reserva.class, PorcentajeOcupacion.class);
        return results.getMappedResults();
    }
}
