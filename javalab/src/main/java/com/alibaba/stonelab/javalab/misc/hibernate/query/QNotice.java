package com.alibaba.stonelab.javalab.misc.hibernate.query;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.alibaba.stonelab.javalab.misc.hibernate.model.Notice;
import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = -1667498703;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNotice notice = new QNotice("notice");

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModify = createDateTime("gmtModify", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QPublisher publisher;

    public final StringPath title = createString("title");

    public QNotice(String variable) {
        this(Notice.class, forVariable(variable), INITS);
    }

    public QNotice(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNotice(PathMetadata<?> metadata, PathInits inits) {
        this(Notice.class, metadata, inits);
    }

    public QNotice(Class<? extends Notice> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.publisher = inits.isInitialized("publisher") ? new QPublisher(forProperty("publisher")) : null;
    }

}

