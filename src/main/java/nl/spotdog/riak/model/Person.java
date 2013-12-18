package nl.spotdog.riak.model;

import com.basho.riak.client.convert.RiakIndex;
import com.basho.riak.client.convert.RiakKey;

public class Person {
    @RiakKey
    public String uuid;
    public String firstName;
    @RiakIndex(name="name")
    public String lastName;
    @RiakIndex(name="age")
    public int age;

    @Override
    public String toString() {
        return "uuid: " + uuid + "\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "age: " + age;
    }
}
