package com.beta.api.v1;

import com.beta.BetaPreviewEmail;
import com.beta.BetaPreviewEmailDTO;
import com.beta.BetaPreviewEmailTable;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.time.LocalDate;

interface EmailDAO {

    @GetGeneratedKeys
    @SqlUpdate("insert into " + BetaPreviewEmailTable.TABLE_NAME
            + " (email, date) values (:e, :d)")
    int insert(@Bind("e") String email, @Bind("d") LocalDate password);

    @SqlQuery("select * from " + BetaPreviewEmailTable.TABLE_NAME + " where id = :id")
    BetaPreviewEmailDTO findById(@Bind("id") Integer id);
}
