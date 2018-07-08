package org.lightning.particle.jdbc.common.meta;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class PrimaryKey extends BaseColumn {

    /**
     * KEY_SEQ sequence number within primary key( a value of 1 represents the first column of the primary key, a value of 2 would represent the second column within the primary key).
     */
    private int keySequence;

    /**
     * PK_NAME primary key name (may be null)
     */
    private String pkName;


}
