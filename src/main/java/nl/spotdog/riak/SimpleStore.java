package nl.spotdog.riak;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import nl.spotdog.riak.model.Person;

import java.util.UUID;

public class SimpleStore {
    public static void main(String[] args) {
        try {
            IRiakClient client = RiakFactory.pbcClient("192.168.56.101", 8087);
            Bucket myBucket = client.fetchBucket("storing4").execute();

            Person person = new Person();
            person.uuid = UUID.randomUUID().toString();
            person.firstName = "Test";
            person.lastName = "Last";


            System.out.println("Storing Object in Riak...");
            myBucket.store(person).execute();

            System.out.println("Reading Objects From Riak...");
            Person fetched = myBucket.fetch(person.uuid, Person.class).execute();
            System.out.println(fetched);

            client.shutdown();

        } catch (RiakException e) {
            e.printStackTrace();
        }
    }
}
