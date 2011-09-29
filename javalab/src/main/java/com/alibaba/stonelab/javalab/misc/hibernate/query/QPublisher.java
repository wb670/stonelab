package com.alibaba.stonelab.javalab.misc.hibernate.query;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.alibaba.stonelab.javalab.misc.hibernate.model.Publisher;
import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QPublisher is a Querydsl query type for Publisher
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QPublisher extends BeanPath<Publisher> {

    private static final long serialVersionUID = -1313345277;

    public static final QPublisher publisher1 = new QPublisher("publisher1");

    public final StringPath publisher = createString("publisher");

    public final StringPath publisherId = createString("publisherId");

    public QPublisher(String variable) {
        super(Publisher.class, forVariable(variable));
    }

    public QPublisher(BeanPath<? extends Publisher> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QPublisher(PathMetadata<?> metadata) {
        super(Publisher.class, metadata);
    }

}

