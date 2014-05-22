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
    @GetGeneratedKeys
    @SqlUpdate("insert into beta_user (email, hashedPassword) values (:email, :hashedPassword)")
    int insert(@Bind("email") String email, @Bind("hashedPassword") String hashedPassword);

    @SqlQuery("select * from beta_user where id = :id")
    BetaUser findById(@Bind("id") int id);

    @SqlQuery("select * from beta_user")
    List<BetaUser> all();

    @SqlUpdate("update beta_user set firstName = :firstName where id = :id")
    int updateFirstName(@Bind("id") int id, @Bind("firstName") String firstName);

    @SqlUpdate("update beta_user set lastName = :lastName where id = :id")
    int updateLastName(@Bind("id") int id, @Bind("lastName") String lastName);

    @SqlUpdate("update beta_user set profilePictureAbsolutePath = :path where id = :id")
    int updateProfilePictureLocation(@Bind("id") int id, @Bind("path") String path);
}
