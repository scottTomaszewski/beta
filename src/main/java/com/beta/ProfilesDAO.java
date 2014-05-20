package com.beta;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(BetaUser.Mapper.class)
interface ProfilesDAO {
    @SqlUpdate("insert into beta_user " +
            "(firstName, lastName, email, passwordHash) " +
            "values " +
            "(:firstName, :lastName, :email, :passwordHash)")
    @GetGeneratedKeys
    int insert(@Bind("firstName") String firstName,
               @Bind("lastName") String lastName,
               @Bind("email") String email,
               @Bind("passwordHash") String passwordHash);

    @SqlQuery("select * from beta_user where id = :id")
    BetaUser findById(@Bind("id") int id);

    @SqlQuery("select * from beta_user")
    List<BetaUser> all();
}
