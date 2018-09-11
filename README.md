# learn-avro

### General notes
.avsc files are schema files
.avro files are avro data files

### Avro tools

    wget http://repo1.maven.org/maven2/org/apache/avro/avro-tools/1.8.2/avro-tools-1.8.2.jar
    java -jar ./avro-tools-1.8.2.jar tojson --pretty customer-specific.avro
    java -jar ./avro-tools-1.8.2.jar getschema customer-specific.avro
    
