import model.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionTable implements Table<Position> {
    private Connection connect = null;

    public PositionTable(Connection connection) throws SQLException {
        this.connect = Database.connection;
        String SQL = " CREATE TABLE IF NOT EXISTS public.position\n" +
                "(\n" +
                "    id integer NOT NULL,\n" +
                "    position character varying(20) COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    CONSTRAINT position_pkey PRIMARY KEY (id)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE public.position\n" +
                "    OWNER to postgres;";
        PreparedStatement preparedStatement = connect.prepareStatement(SQL);
        preparedStatement.executeUpdate();
        System.out.println("Table successfully created..");

    }

    public int insert(Position position) throws SQLException {

        String sql = "INSERT INTO public.position(\n" +
                "\tid, position)\n" +
                "\tVALUES (?, ?);";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, position.getId());
        preparedStatement.setString(2, position.getPosition());
        return preparedStatement.executeUpdate();
    }


    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM public.workers\n" +
                "\tWHERE positionId=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
       sql = "DELETE FROM public.position\n" +
                "\tWHERE id=?;";
         preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return preparedStatement.executeUpdate();

    }


    public int update(int id, String nameColumn, String newInstance) throws SQLException {

        String sql = "UPDATE public.position\n" +
                "\tSET " + nameColumn + "=?\n" +
                "\tWHERE id=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, newInstance);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate();
    }

    public int update(int id, String nameColumn, int newInstance) throws SQLException {

        String sql = "UPDATE public.position\n" +
                "\tSET " + nameColumn + "=?\n" +
                "\tWHERE id=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, newInstance);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeUpdate();
    }

    public Position select(int id) throws SQLException {
        String sql = "SELECT id, position\n" +
                "\tFROM public.position\n" +
                "\tWHERE id=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        Position selectPosition = null;
        if (result.next())
            selectPosition = new Position(result.getInt("id"), result.getString("position"));

        return selectPosition;
    }

    public ResultSet selectAll() throws SQLException {
        String sql = "SELECT id, position\n" +
                "\tFROM public.position\n";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);

        ResultSet result = preparedStatement.executeQuery();
        return result;

    }

    @Override
    public void truncate() throws SQLException {
        String sql = "TRUNCATE TABLE  public.position CASCADE ;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.execute();

    }
}
