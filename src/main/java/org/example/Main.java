package org.example;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int NUM_FOO = 700;
    private static final int NUM_RECORDS_PER_FOO = 10000;

    public static void main(final String[] args) throws IOException {
        final DataRoot root = new DataRoot();
        final EmbeddedStorageManager storageManager = EmbeddedStorage.start(root, Paths.get("data"));

        if ("init".equalsIgnoreCase(args[0])) {
            for (long fooId = 0; fooId < NUM_FOO; fooId++) {
                Foo foo = new Foo(fooId);
                List<Record> records = new ArrayList<>();
                for (int recordId = 0; recordId < NUM_RECORDS_PER_FOO; recordId++) {
                    records.add(new Record(Integer.toString(recordId)));
                }
                foo.addRecords(records);
                root.getContent().put(fooId, foo);
                storageManager.store(root.getContent());
                //foo.getRecords().clear();
            }
            System.err.println("Size after init: " + root.getContent().size());
        } else if ("size".equalsIgnoreCase(args[0])) {
            System.err.println("Size is: " + root.getContent().size());
        } else if ("clear".equalsIgnoreCase(args[0])) {
            root.getContent().clear();
            storageManager.store(root.getContent());
            System.err.println("Size after clear: " + root.getContent().size());
        } else {
            throw new IllegalArgumentException("Unknown command line option: " + args[0]);
        }
        dumpClassHistogramm();
        System.exit(0);
    }

    private static void dumpClassHistogramm() throws IOException {
        long myPid = ProcessHandle.current().pid();
        Process jmap = Runtime.getRuntime().exec("jmap -histo:live " + myPid);
        new String(jmap.getInputStream().readAllBytes())
                .lines()
                .limit(100)
                .forEach(System.err::println);
    }
}