package com.beta.api.v1;

import com.beta.BetaUser;
import com.beta.BetaUserTable;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(BetaUser.Mapper.class)
interface ProfilesDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into " + BetaUserTable.TABLE_NAME
            + " (email, password, salt) values (:e, :p, :s)")
    int insert(@Bind("e") String email, @Bind("p") String password, @Bind("s") String salt);

    @SqlQuery("select * from " + BetaUserTable.TABLE_NAME + " where id = :id")
    BetaUser findById(@Bind("id") int id);

    @SqlQuery("select * from " + BetaUserTable.TABLE_NAME)
    List<BetaUser> all();

    @SqlUpdate("update " + BetaUserTable.TABLE_NAME + " set firstName = :firstName where id = :id")
    int updateFirstName(@Bind("id") int id, @Bind("firstName") String firstName);

    @SqlUpdate("update " + BetaUserTable.TABLE_NAME + " set lastName = :lastName where id = :id")
    int updateLastName(@Bind("id") int id, @Bind("lastName") String lastName);

    @SqlUpdate("update " + BetaUserTable.TABLE_NAME + " set email = :email where id = :id")
    void updateEmail(@Bind("id") int id, @Bind("email") String s);

    @SqlUpdate("update " + BetaUserTable.TABLE_NAME
            + " set pictureAbsolutePath = :path where id = :id")
    int updatePictureLocation(@Bind("id") int id, @Bind("path") String path);

}
