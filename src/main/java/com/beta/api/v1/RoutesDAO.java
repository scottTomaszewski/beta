package com.beta.api.v1;

import com.beta.Route;
import com.beta.RouteTable;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(Route.Mapper.class)
public interface RoutesDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into " + RouteTable.TABLE_NAME + " (name, grade) values (:name, :grade)")
    int insert(@Bind("name") String name, @Bind("grade") String grade);

    @SqlQuery("select * from " + RouteTable.TABLE_NAME + " where id = :id")
    Route findById(@Bind("id") int id);

    @SqlQuery("select * from " + RouteTable.TABLE_NAME)
    List<Route> all();

//    @SqlUpdate("update routes set firstName = :firstName where id = :id")
//    int updateFirstName(@Bind("id") int id, @Bind("firstName") String firstName);
//
//    @SqlUpdate("update beta_user set lastName = :lastName where id = :id")
//    int updateLastName(@Bind("id") int id, @Bind("lastName") String lastName);
//
//    @SqlUpdate("update beta_user set profilePictureAbsolutePath = :path where id = :id")
//    int updateProfilePictureLocation(@Bind("id") int id, @Bind("path") String path);
}
