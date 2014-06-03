package com.beta;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BetaUser extends BetaUserDTO {
    @VisibleForTesting
    final String password;
    @VisibleForTesting
    final String salt;

    @VisibleForTesting
    BetaUser(int id,
             String email,
             Optional<String> firstName,
             Optional<String> lastName,
             Optional<String> pictureAbsolutePath,
             String password,
             String salt) {
        super(id, email, firstName, lastName, pictureAbsolutePath);
        this.password = password;
        this.salt = salt;
    }

    public boolean checkPassword(char[] plainTextPassword) {
        boolean valid = new PasswordSecurity(plainTextPassword, salt).checkAgainst(password);
        Arrays.fill(plainTextPassword, 'a');
        return valid;
    }

    public static class Mapper implements ResultSetMapper<BetaUser> {
        public BetaUser map(int idx, ResultSet r, StatementContext c) throws SQLException {
            return new BetaUser(r.getInt(BetaUserTable.ID.columnName),
                    r.getString(BetaUserTable.EMAIL.columnName),
                    Optional.fromNullable(r.getString(BetaUserTable.FIRST_NAME.columnName)),
                    Optional.fromNullable(r.getString(BetaUserTable.LAST_NAME.columnName)),
                    Optional.fromNullable(r.getString(BetaUserTable.PICTURE_ABSOLUTE_PATH.columnName)),
                    r.getString(BetaUserTable.PASSWORD.columnName),
                    r.getString(BetaUserTable.SALT.columnName));
        }
    }
}
