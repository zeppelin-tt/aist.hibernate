package aist.demo.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGInterval;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class IntervalType implements UserType {
    private static final int[] SQL_TYPES = { Types.OTHER };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return Integer.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String interval = resultSet.getString(names[0]);
        if (resultSet.wasNull() || interval == null) {
            return null;
        }
        PGInterval pgInterval = new PGInterval(interval);
        Date epoch = new Date(0l);
        pgInterval.add(epoch);
        return Integer.valueOf((int)epoch.getTime() / 1000);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.VARCHAR);
        } else {
            preparedStatement.setObject(i, getInterval(((Integer) o).intValue()), Types.OTHER);
        }
    }

    public static String getInterval(int value){
        return new PGInterval(0, 0, 0, 0, 0, value).getValue();
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }

}