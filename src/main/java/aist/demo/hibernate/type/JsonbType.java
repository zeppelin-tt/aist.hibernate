package aist.demo.hibernate.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JsonbType implements UserType {

    private final Gson GSON = new GsonBuilder().serializeNulls().create();

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue == null) {
            return null;
        }
        if (!(originalValue instanceof JsonElement)) {
            return null;
        }
        return GSON.fromJson(GSON.toJson(originalValue, JsonElement.class), JsonElement.class);
    }


    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        PGobject object = (PGobject) resultSet.getObject(names[0]);
        if (object.getValue() == null) {
            return null;
        }
        return GSON.toJsonTree(object.getValue());
    }


    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.OTHER);
        } else {
            if (!(o instanceof JsonElement))
                throw new SQLException("Object not instanceof JsonElement");
            preparedStatement.setObject(i, ((JsonElement) o).getAsString(), Types.OTHER);
        }
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);
        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }
        throw new SerializationException(String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null) {
            return 0;
        }
        return x.hashCode();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public Class<?> returnedClass() {
        return JsonElement.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

}
