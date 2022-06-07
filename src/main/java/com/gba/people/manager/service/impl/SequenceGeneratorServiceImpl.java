package com.gba.people.manager.service.impl;

import com.gba.people.manager.model.DatabaseSequence;
import com.gba.people.manager.service.SequenceGeneratorService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import static java.util.Objects.isNull;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public record SequenceGeneratorServiceImpl(MongoOperations mongoOperations) implements SequenceGeneratorService {
    @Override
    public Integer generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !isNull(counter) ? counter.getSeq() : 1;
    }
}
