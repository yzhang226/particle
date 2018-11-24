package org.lightning.particle.core.jdbc.meta;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public abstract class BaseColumn {

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
     * COLUMN_NAME
     */
    private String columnName;

}
