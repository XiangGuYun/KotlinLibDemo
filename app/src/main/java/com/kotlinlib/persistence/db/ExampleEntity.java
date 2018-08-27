package com.kotlinlib.persistence.db;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ExampleEntity {
    @Id public  long id;
}
