package nl.tomkemper.bep3.hellomongo.uuids;

import com.mongodb.MongoClientSettings;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.uuidRepresentation(UuidRepresentation.STANDARD);
    }

    @Override
    protected String getDatabaseName() {
        return "uuid-test";
    }
}
