package org.lightning.particle.core.jdbc.meta;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class Table {

    /**
     * TABLE_CAT
     */
    private String tableCategory;

    /**
     * TABLE_SCHEM
     */
    private String tableSchema;

    /**
     * TABLE_NAME
     */
    private String tableName;

    /**
     * TABLE_TYPE
     */
    private String tableType;

    /**
     * REMARKS 表注释
     */
    private String remarks;

    /**
     * TYPE_CAT
     */
    private String typeCategory;

    /**
     * TYPE_SCHEM
     */
    private String typeSchema;

    /**
     * TYPE_NAME
     */
    private String typeName;

    /**
     * SELF_REFERENCING_COL_NAME
     */
    private String selfReferencingColName;

    /**
     * REF_GENERATION
     */
    private String refGeneration;


    // ------
    /**
     * 主键(s)
     */
    private List<PrimaryKey> primaryKeys;

    /**
     * 所有列
     */
    private List<Column> columns;

    /**
     * 主键列
     */
    private Column pkColumn;

    /**
     * 构建主键信息
     */
    public void buildColumnOfPrimaryKey() {
        PrimaryKey pk = primaryKeys.get(0);
        for (Column column : columns) {
            if (column.getColumnName().equals(pk.getColumnName())) {
                if (pkColumn == null) {
                    pkColumn = column;
                    column.setPrimaryKey(true);
                }
            }
        }
    }

    public Column getPkColumn() {
        return pkColumn;
    }

}
