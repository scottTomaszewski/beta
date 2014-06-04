package com.beta;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteDTO {
    public final int id;
    public final String name;
    public final Grade grade;
    public final Optional<String> setterId;
    public final Optional<String> tapeColor;

    @VisibleForTesting
    @JsonCreator
    public RouteDTO(@JsonProperty("id") int id,
                    @JsonProperty("name") String name,
                    @JsonProperty("grade") Grade grade,
                    @JsonProperty("setterId") Optional<String> setterId,
                    @JsonProperty("tapeColor") Optional<String> tapeColor) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.setterId = setterId;
        this.tapeColor = tapeColor;
    }

//    private final LocalDate createdAt;
}
