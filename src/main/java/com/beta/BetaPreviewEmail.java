package com.beta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BetaPreviewEmail extends BetaPreviewEmailDTO {

    BetaPreviewEmail(@JsonProperty("id") int id,
                     @JsonProperty("email") String email,
                     @JsonProperty("date") LocalDate date) {
        super(id, email, date);
    }

    public static class Mapper implements ResultSetMapper<BetaPreviewEmail> {
        public BetaPreviewEmail map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaPreviewEmail(r.getInt(BetaPreviewEmailTable.ID.columnName),
                    r.getString(BetaPreviewEmailTable.EMAIL.columnName),
                    r.getDate(BetaPreviewEmailTable.DATE.columnName).toLocalDate());
        }
    }
}
