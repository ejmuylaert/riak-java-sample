package nl.spotdog.riak;

import nl.spotdog.riak.model.Person;

import java.util.Collection;

public class PersonResolver implements com.basho.riak.client.cap.ConflictResolver<Person> {
    @Override
    public Person resolve(Collection<Person> siblings) {
        if (siblings.size() == 0) {
            System.out.println("No siblings founds");
            return null;
        }

        System.out.println("Resolving, nr of siblings: " + siblings.size());
        Person resolved = new Person();
        for (Person person : siblings) {
            System.out.println(person.firstName + ", " + person.lastName);
            resolved = person;
        }
        return resolved;
    }
}
