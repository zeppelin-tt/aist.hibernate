package aist.demo.type;

import aist.demo.type.internal.ArraySqlTypeDescriptor;
import aist.demo.type.internal.IntArrayTypeDescriptor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

public class IntArrayType extends AbstractSingleColumnStandardBasicType<int[]> implements DynamicParameterizedType {

    public IntArrayType() {
        super(ArraySqlTypeDescriptor.INSTANCE, IntArrayTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "int-array";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((IntArrayTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
