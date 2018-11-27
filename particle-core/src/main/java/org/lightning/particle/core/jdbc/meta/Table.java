package org.lightning.particle.core.jdbc.meta;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Getter
    private List<Column> pkColumns = Lists.newArrayList();

    /**
     * 构建主键信息
     */
    public void buildColumnOfPrimaryKey() {
//        PrimaryKey pk = primaryKeys.get(0);
        Set<String> pkColumnNames = primaryKeys.stream()
                .map(BaseColumn::getColumnName)
                .collect(Collectors.toSet());
        for (Column column : columns) {
            if (pkColumnNames.contains(column.getColumnName())) {
                pkColumns.add(column);
                column.setPrimaryKey(true);
            }
        }
    }

}
