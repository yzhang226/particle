package org.lightning.particle.jdbc.common.meta;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class Column extends BaseColumn {

    /**
     * DATA_TYPE SQL type from java.sql.Types *
     */
    private int dataType;

    /**
     * TYPE_NAME Data source dependent type name, * for a UDT the type name is fully qualified *
     */
    private String typeName;

    /**
     * The COLUMN_SIZE column specifies the column size for the given column.
     * For numeric data, this is the maximum precision. For character data, this is the length in characters.
     * For datetime datatypes, this is the length in characters of the String representation (assuming the * maximum allowed precision of the fractional seconds component).
     * For binary data, this is the length in bytes.
     * For the ROWID datatype, * this is the length in bytes. Null is returned for data types where the * column size is not applicable.
     */
    private int columnSize;

    /**
     * DECIMAL_DIGITS the number of fractional digits. Null is returned for data types where * DECIMAL_DIGITS is not applicable. *
     */
    private int decimalDigits;

    /**
     * NUM_PREC_RADIX int {@code =>} Radix (typically either 10 or 2) *
     */
    private int numPrecRadix;

    /**
     * NULLABLE
     */
    private int nullable;

    /**
     * REMARKS 列注释,
     */
    private String remarks;

    /**
     * COLUMN_DEF
     */
    private String columnDefault;

    /**
     * CHAR_OCTET_LENGTH int {@code =>} for char types the * maximum number of bytes in the column *
     */
    private int charOctetLength;

    /**
     * ORDINAL_POSITION int {@code =>} index of column in table * (starting at 1) *
     */
    private int ordinalPosition;

    /**
     * IS_NULLABLE
     * YES --- if the column can include NULLs *
     * NO --- if the column cannot include NULLs *
     * empty string --- if the nullability for the * column is unknown *
     */
    private String isNullable;

    /**
     * SCOPE_CATALOG
     *  catalog of table that is the scope * of a reference attribute (null if DATA_TYPE isn't REF) *
     */
    private String scopeCatalog;

    /**
     * SCOPE_SCHEMA
     *  schema of table that is the scope * of a reference attribute (null if the DATA_TYPE isn't REF) *
     */
    private String scopeSchema;

    /**
     * SCOPE_TABLE
     *  table name that this the scope * of a reference attribute (null if the DATA_TYPE isn't REF) *
     */
    private String scopeTable;

    /**
     * SOURCE_DATA_TYPE
     * source type of a distinct type or user-generated * Ref type, SQL type from java.sql.Types (null if DATA_TYPE * isn't DISTINCT or user-generated REF) *
     */
    private String sourceDataType;

    /**
     * IS_AUTOINCREMENT
     * YES --- if the column is auto incremented *
     * NO --- if the column is not auto incremented *
     * empty string --- if it cannot be determined whether the column is auto incremented *
     */
    private String isAutoIncrement;

    /**
     * IS_GENERATEDCOLUMN
     * YES --- if this a generated column *
     * NO --- if this not a generated column *
     * empty string --- if it cannot be determined whether this is a generated column *
     */
    private String isGeneratedColumn;
}
