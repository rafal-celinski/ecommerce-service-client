package pis24l.projekt.api_client.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.DecoderContext;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeCodec implements Codec<OffsetDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public void encode(BsonWriter writer, OffsetDateTime value, EncoderContext encoderContext) {
        writer.writeString(value.format(FORMATTER));
    }

    @Override
    public OffsetDateTime decode(BsonReader reader, DecoderContext decoderContext) {
        return OffsetDateTime.parse(reader.readString(), FORMATTER);
    }

    @Override
    public Class<OffsetDateTime> getEncoderClass() {
        return OffsetDateTime.class;
    }
}

