package spacetrader.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Philip on 10/16/2014.
 *
 * Hackish but simplifies things later on.
 */
public class DbResponse {
    public Connection c = null;
    public Statement s = null;
    public ResultSet r = null;

    public DbResponse(Connection c, Statement s, ResultSet r) {
        this.c = c;
        this.s = s;
        this.r = r;
    }
}
