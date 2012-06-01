package com.alibaba.stonelab.javalab.misc.hibernate.query;

import javax.annotation.Generated;

import com.alibaba.stonelab.javalab.misc.hibernate.model.Notice;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;

/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QNotice extends RelationalPathBase<Notice> {

    private static final long                 serialVersionUID = -1667498703;

    private static final PathInits            INITS            = PathInits.DIRECT;

    public static final QNotice               notice           = new QNotice("party", "notice", "n");

    public final StringPath                   content          = createString("content");

    public final DateTimePath<java.util.Date> gmtCreate        = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModify        = createDateTime("gmtModify", java.util.Date.class);

    public final NumberPath<Integer>          id               = createNumber("id", Integer.class);

    public final QPublisher                   publisher;

    public final StringPath                   title            = createString("title");

    public QNotice(String schema, String table, String variable){
        super(Notice.class, variable, schema, table);
        this.publisher = INITS.isInitialized("publisher") ? new QPublisher(forProperty("publisher")) : null;
    }

}
