package org.example;

import one.microstream.reference.Lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Foo {

    private long id;

    private Lazy<List<Record>> records;

    public Foo(long id) {
        this.id = id;
        this.records = Lazy.Reference(new ArrayList<>());
    }

    public void addRecords(Collection<Record> records) {
        this.records.get().addAll(records);
    }

    public Lazy<List<Record>> getRecords() {
        return records;
    }
}
