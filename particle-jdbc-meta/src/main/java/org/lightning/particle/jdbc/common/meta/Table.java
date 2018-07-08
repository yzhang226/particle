package org.lightning.particle.jdbc.common.meta;

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

}
