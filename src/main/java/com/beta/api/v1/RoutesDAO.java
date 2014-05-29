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

    @SqlUpdate("update " + RouteTable.TABLE_NAME + " set setterId = :setterId where id = :id")
    int updateSetterId(@Bind("id") int id, @Bind("setterId") String setterId);

    @SqlUpdate("update " + RouteTable.TABLE_NAME + " set tapeColor = :tapeColor where id = :id")
    int updateTapeColor(@Bind("id") int id, @Bind("tapeColor") String tapeColor);
}
