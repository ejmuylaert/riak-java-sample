package nl.spotdog.riak;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import nl.spotdog.riak.model.Person;


public class Siblings {
    public static void main(String[] args) {
        System.out.println("Creating Objects In Riak...");
        String address = "192.168.56.101";
        try {
            IRiakClient client = RiakFactory.pbcClient(address, 8087);

            Bucket siblingBucket = client.createBucket("siblings2").allowSiblings(true).lastWriteWins(false).execute();

            Person person = new Person();
            person.uuid = "uuid";
            person.firstName = "Second";
            person.lastName = "Last";
            person.age = 22;

            siblingBucket.store(person).execute();


            System.out.println("Fetching siblings");
            Person siblingFetch = siblingBucket.fetch("uuid", Person.class).withResolver(new PersonResolver()).execute();
            System.out.println("Done fetching siblings");
            System.out.println("Fetched: " + siblingFetch);

            client.shutdown();

        } catch (RiakException e) {
            e.printStackTrace();
        }

    }
}
