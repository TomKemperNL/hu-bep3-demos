package nl.tomkemper.bep3.hellomongo.nestedids;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.bson.Document;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "nestedIds"; //bizar dat deze verplicht is om te overschrijven!
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter converterConfigurationAdapter) {
        converterConfigurationAdapter.registerConverter(new IdReader());
        converterConfigurationAdapter.registerConverter(new IdWriter());
        System.out.println("Ze worden toegevoegd");
    }

    public static class IdReader implements Converter<ObjectId, PersoonId> {
        public PersoonId convert(ObjectId document) {
            return new PersoonId(document.toHexString());
        }
    }

    public static class IdWriter implements Converter<PersoonId, ObjectId> {
        public ObjectId convert(PersoonId id) {
            return new ObjectId(id.getId());
        }
    }


}
