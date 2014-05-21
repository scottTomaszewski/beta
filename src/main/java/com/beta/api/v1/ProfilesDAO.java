package com.beta.api.v1;

import com.beta.BetaUser;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(BetaUser.Mapper.class)
interface ProfilesDAO {
    @SqlUpdate("insert into beta_user " +
            "(email, passwordHash) " +
            "values " +
            "(:email, :passwordHash)")
    @GetGeneratedKeys
    int insert(@Bind("email") String email,
               @Bind("passwordHash") String passwordHash);

    @SqlQuery("select * from beta_user where id = :id")
    BetaUser findById(@Bind("id") int id);

    @SqlQuery("select * from beta_user")
    List<BetaUser> all();

    @SqlUpdate("update beta_user set firstName = :firstName where id = :id")
    @GetGeneratedKeys
    int updateFirstName(@Bind("id") int id, @Bind("firstName") String firstName);

    @SqlUpdate("update beta_user set lastName = :lastName where id = :id")
    @GetGeneratedKeys
    int updateLastName(@Bind("id") int id, @Bind("lastName") String lastName);
}
