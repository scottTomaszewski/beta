package com.beta.api.v1;

import com.beta.BetaPreviewEmail;
import com.beta.BetaPreviewEmailDTO;
import com.beta.BetaPreviewEmailTable;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.time.LocalDate;

@RegisterMapper(BetaPreviewEmail.Mapper.class)
interface EmailDAO {

    @GetGeneratedKeys
    @SqlUpdate("insert into " + BetaPreviewEmailTable.TABLE_NAME
            + " (email, date) values (:e, :d)")
    int insert(@Bind("e") String email, @Bind("d") String date);

    @SqlQuery("select * from " + BetaPreviewEmailTable.TABLE_NAME + " where id = :id")
    BetaPreviewEmail findById(@Bind("id") Integer id);
}
