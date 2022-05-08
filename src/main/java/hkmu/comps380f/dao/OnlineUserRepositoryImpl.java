package hkmu.comps380f.dao;

import hkmu.comps380f.model.OnlineUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OnlineUserRepositoryImpl implements OnlineUserRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public OnlineUserRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class UserExtractor implements ResultSetExtractor<List<OnlineUser>> {

        @Override
        public List<OnlineUser> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, OnlineUser> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                OnlineUser user = map.get(username);
                if (user == null) {
                    user = new OnlineUser();
                    user.setUsername(username);
                    user.setPassword(rs.getString("password"));
                    map.put(username, user);
                }
                user.getRoles().add(rs.getString("role"));
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public void save(OnlineUser user) {
        final String SQL_INSERT_USER
                = "insert into users (username, password) values (?, ?)";
        final String SQL_INSERT_ROLE
                = "insert into user_roles (username, role) values (?, ?)";
        final String SQL_INSERT_FULLNAME
                = "insert into users_info (username, fullname) values (?, ?)";
        final String SQL_INSERT_PHONE
                = "insert into users_info (username, phone) values (?, ?)";
        final String SQL_INSERT_ADDRESS
                = "insert into users_info (username, address) values (?, ?)";

        jdbcOp.update(SQL_INSERT_USER, user.getUsername(), user.getPassword());
        System.out.println("User " + user.getUsername() + " inserted");
        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE, user.getUsername(), role);
            System.out.println("User_role " + role + " of user "
                    + user.getUsername() + " inserted");
        }
        jdbcOp.update(SQL_INSERT_FULLNAME, user.getUsername(), user.getFullname());
        System.out.println("User_fullname" + user.getUsername() + " inserted");
        jdbcOp.update(SQL_INSERT_PHONE, user.getUsername(), user.getPhone());
        System.out.println("User_phone" + user.getUsername() + " inserted");
        jdbcOp.update(SQL_INSERT_ADDRESS, user.getUsername(), user.getAddress());
        System.out.println("User_address" + user.getAddress() + " inserted");
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnlineUser> findAll() {
        final String SQL_SELECT_USERS
                = "select users.*, user_roles.role from users, user_roles"
                + " where users.username = user_roles.username";
        return jdbcOp.query(SQL_SELECT_USERS, new UserExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnlineUser> findById(String username) {
        final String SQL_SELECT_USER
                = "select users.*, user_roles.role from users, user_roles"
                + " where users.username = user_roles.username"
                + " and users.username = ?";
        return jdbcOp.query(SQL_SELECT_USER, new UserExtractor(), username);
    }

    @Override
    @Transactional
    public void delete(String username) {
        final String SQL_DELETE_USER = "delete from users where username=?";
        final String SQL_DELETE_ROLES = "delete from user_roles where username=?";
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);
    }
}
