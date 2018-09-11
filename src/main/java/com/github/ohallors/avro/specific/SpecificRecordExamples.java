package com.github.ohallors.avro.specific;

import com.example.Customer;
import java.io.File;
import java.io.IOException;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class SpecificRecordExamples {
    public static void main(String[] args) throws IOException {
        // step 1: create specific record
        Customer.Builder customerBuilder = Customer.newBuilder();
        customerBuilder.setAge(25);
        customerBuilder.setFirstName("John");
        customerBuilder.setLastName("Doe");
        customerBuilder.setHeight(178.5f);
        customerBuilder.setWeight(80.5f);
        customerBuilder.setAutomatedEmail(false);
        Customer customer = customerBuilder.build();
        System.out.println(customer);

        // step 2: write to file
        DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);

        try (DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
            dataFileWriter.append(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 3: read from file
        final File file = new File("customer-specific.avro");
        final DatumReader<Customer> datumReader = new SpecificDatumReader<>(Customer.class);
        final DataFileReader<Customer> dataFileReader = new DataFileReader<>(file, datumReader);
        while (dataFileReader.hasNext()) {
            Customer readCustomer = dataFileReader.next();
            System.out.println(readCustomer.toString());
            System.out.println("First name: " + readCustomer.getFirstName());
        }

        // step 4: interpret

    }
}
