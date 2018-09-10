package com.github.ohallors.avro.generic;

import java.io.File;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class GenericRecordExamples {
    public static void main(String[] args) {
        // step 0: define schema
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "  \"type\": \"record\",\n" +
                "  \"namespace\": \"com.example\",\n" +
                "  \"name\": \"Customer\",\n" +
                "  \"doc\": \"Avro schema for our Customer\",\n" +
                "  \"fields\": [\n" +
                "    {\"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First name of customer\"},\n" +
                "    {\"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last name of customer\"},\n" +
                "    {\"name\": \"age\", \"type\": \"int\", \"doc\": \"age at time of registration\"},\n" +
                "    {\"name\": \"height\", \"type\": \"float\", \"doc\": \"height in centimetres\"},\n" +
                "    {\"name\": \"weight\", \"type\": \"float\", \"doc\": \"weight in kilos\"},\n" +
                "    {\"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"true if user wants marketing emails\"}\n" +
                "  ]\n" +
                "}");
        // step 1: create a generic record
        GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
        customerBuilder.set("first_name", "John");
        customerBuilder.set("last_name", "Doe");
        customerBuilder.set("age", 25);
        customerBuilder.set("height", 170f);
        customerBuilder.set("weight", 80.5f);
        customerBuilder.set("automated_email", false);
        GenericData.Record customer = customerBuilder.build();
        System.out.println(customer);

        GenericRecordBuilder customerBuilderWithDefault = new GenericRecordBuilder(schema);
        customerBuilderWithDefault.set("first_name", "John");
        customerBuilderWithDefault.set("last_name", "Doe");
        customerBuilderWithDefault.set("age", 25);
        customerBuilderWithDefault.set("height", 170f);
        customerBuilderWithDefault.set("weight", 80.5f);
        GenericData.Record customerWithDefault = customerBuilderWithDefault.build();
        System.out.println(customerWithDefault);

        // step 2: write generic record to file
        final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-generic.avro"));
            dataFileWriter.append(customer);
            dataFileWriter.append(customerWithDefault);
            System.out.println("written customer-generic.avro");
        } catch (IOException ex) {
            System.out.println("Couldn't write file");
            ex.printStackTrace();
        }

        // step 3: read generic record from a file
        final File file = new File("customer-generic.avro");
        final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        GenericRecord customerRead;
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)) {
            // step 4: interpret as a generic record
            customerRead = dataFileReader.next();
            System.out.println("successfully read avro file");
            System.out.println(customerRead.toString());

            System.out.println("First name: " + customerRead.get("first_name"));
            System.out.println("Non existent field: " + customerRead.get("not_here"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean someLibraryMethod() {
        return true;
    }
}