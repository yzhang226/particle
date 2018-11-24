package org.lightning.particle.plugin.javabean.common;

import org.apache.commons.lang3.ClassUtils;
import org.lightning.particle.core.jdbc.meta.Column;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cook on 2018/11/24
 */
public abstract class JavaJdbcTypeMappings {

    /**
     *
     * @param column
     * @return
     */
    public static String guessJdbcTypeName(Column column) {
        return jdbcTypeMap.get(column.getDataType()).toString();
    }

    /**
     *
     * @param column
     * @param enablePrimitiveType
     * @return
     */
    public static Class<?> guessJavaType(Column column, boolean enablePrimitiveType) {
        int jdbcType = column.getDataType();
        String typeName = column.getTypeName();
        boolean allowNull = column.getNullable() == 1;

        Class<?> javaType = processLocalDateType(typeName);
        if (javaType == null) {
            javaType = javaTypeMap.get(jdbcType);
            if (!allowNull && ClassUtils.isPrimitiveWrapper(javaType) && enablePrimitiveType ) {
                javaType = ClassUtils.wrapperToPrimitive(javaType);
            }
        }

        return javaType;
    }


    private static Class<?> processLocalDateType(String typeName) {
        if ("datetime".equalsIgnoreCase(typeName)) {
            return LocalDateTime.class;
        } else if ("date".equalsIgnoreCase(typeName)) {
            return LocalDate.class;
        } else if ("time".equalsIgnoreCase(typeName)) {
            return LocalTime.class;
        } else if ("timestamp".equalsIgnoreCase(typeName)) {
            return LocalDateTime.class;
        }
        return null;
    }

    static Map<Integer, Class<?>> javaTypeMap = new HashMap<>();
    static Map<Integer, MybatisJdbcType> jdbcTypeMap = new HashMap<>();

    static {
        javaTypeMap.put(Types.DATE, Date.class);
        javaTypeMap.put(Types.TIME, Date.class);
        javaTypeMap.put(Types.TIMESTAMP, Date.class);

        javaTypeMap.put(Types.ARRAY, List.class);
        javaTypeMap.put(Types.BIGINT, Long.class);
        javaTypeMap.put(Types.BINARY, byte[].class); //$NON-NLS-1$
        javaTypeMap.put(Types.BIT, Boolean.class);
        javaTypeMap.put(Types.BLOB, byte[].class); //$NON-NLS-1$
        javaTypeMap.put(Types.BOOLEAN, Boolean.class);
        javaTypeMap.put(Types.CHAR, String.class);
        javaTypeMap.put(Types.CLOB, String.class);
        javaTypeMap.put(Types.DATALINK, Object.class);

        javaTypeMap.put(Types.DECIMAL, BigDecimal.class);
        javaTypeMap.put(Types.DISTINCT, Object.class);
        javaTypeMap.put(Types.DOUBLE, Double.class);
        javaTypeMap.put(Types.FLOAT, Float.class);
        javaTypeMap.put(Types.INTEGER, Integer.class);
        javaTypeMap.put(Types.JAVA_OBJECT, Object.class);
        javaTypeMap.put(Types.LONGNVARCHAR, String.class);
        javaTypeMap.put(Types.LONGVARBINARY, byte[].class);
        javaTypeMap.put(Types.LONGVARCHAR, String.class);
        javaTypeMap.put(Types.NCHAR, String.class);
        javaTypeMap.put(Types.NCLOB, String.class);
        javaTypeMap.put(Types.NVARCHAR, String.class);
        javaTypeMap.put(Types.NULL, Object.class);
        javaTypeMap.put(Types.NUMERIC, BigDecimal.class);
        javaTypeMap.put(Types.OTHER, Object.class);
        javaTypeMap.put(Types.REAL, Double.class);
        javaTypeMap.put(Types.REF, Object.class);
        javaTypeMap.put(Types.SMALLINT, Integer.class);
        javaTypeMap.put(Types.STRUCT, Object.class);

        javaTypeMap.put(Types.TINYINT, Integer.class);
        javaTypeMap.put(Types.VARBINARY, byte[].class);
        javaTypeMap.put(Types.VARCHAR, String.class);

        //
        jdbcTypeMap.put(Types.DATE, MybatisJdbcType.DATE);
        jdbcTypeMap.put(Types.TIME, MybatisJdbcType.TIME);
        jdbcTypeMap.put(Types.TIMESTAMP, MybatisJdbcType.TIMESTAMP);

        jdbcTypeMap.put(Types.ARRAY, MybatisJdbcType.ARRAY);
        jdbcTypeMap.put(Types.BIGINT, MybatisJdbcType.BIGINT);
        jdbcTypeMap.put(Types.BINARY, MybatisJdbcType.BINARY); //$NON-NLS-1$
        jdbcTypeMap.put(Types.BIT, MybatisJdbcType.BIT);
        jdbcTypeMap.put(Types.BLOB, MybatisJdbcType.BLOB); //$NON-NLS-1$
        jdbcTypeMap.put(Types.BOOLEAN, MybatisJdbcType.BOOLEAN);
        jdbcTypeMap.put(Types.CHAR, MybatisJdbcType.CHAR);
        jdbcTypeMap.put(Types.CLOB, MybatisJdbcType.CLOB);
        jdbcTypeMap.put(Types.DATALINK, MybatisJdbcType.DATALINK);

        jdbcTypeMap.put(Types.DECIMAL, MybatisJdbcType.DECIMAL);
        jdbcTypeMap.put(Types.DISTINCT, MybatisJdbcType.DISTINCT);
        jdbcTypeMap.put(Types.DOUBLE, MybatisJdbcType.DOUBLE);
        jdbcTypeMap.put(Types.FLOAT, MybatisJdbcType.FLOAT);
        jdbcTypeMap.put(Types.INTEGER, MybatisJdbcType.INTEGER);
        jdbcTypeMap.put(Types.JAVA_OBJECT, MybatisJdbcType.JAVA_OBJECT);
        jdbcTypeMap.put(Types.LONGNVARCHAR, MybatisJdbcType.LONGNVARCHAR);
        jdbcTypeMap.put(Types.LONGVARBINARY, MybatisJdbcType.LONGVARBINARY);
        jdbcTypeMap.put(Types.LONGVARCHAR, MybatisJdbcType.LONGVARCHAR);
        jdbcTypeMap.put(Types.NCHAR, MybatisJdbcType.NCHAR);
        jdbcTypeMap.put(Types.NCLOB, MybatisJdbcType.NCLOB);
        jdbcTypeMap.put(Types.NVARCHAR, MybatisJdbcType.NVARCHAR);
        jdbcTypeMap.put(Types.NULL, MybatisJdbcType.NULL);
        jdbcTypeMap.put(Types.NUMERIC, MybatisJdbcType.NUMERIC);
        jdbcTypeMap.put(Types.OTHER, MybatisJdbcType.OTHER);
        jdbcTypeMap.put(Types.REAL, MybatisJdbcType.REAL);
        jdbcTypeMap.put(Types.REF, MybatisJdbcType.REF);
        jdbcTypeMap.put(Types.SMALLINT, MybatisJdbcType.SMALLINT);
        jdbcTypeMap.put(Types.STRUCT, MybatisJdbcType.STRUCT);

        jdbcTypeMap.put(Types.TINYINT, MybatisJdbcType.TINYINT);
        jdbcTypeMap.put(Types.VARBINARY, MybatisJdbcType.VARBINARY);
        jdbcTypeMap.put(Types.VARCHAR, MybatisJdbcType.VARCHAR);
    }

}
