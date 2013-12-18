package nl.spotdog.riak;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.query.indexes.BinIndex;
import com.basho.riak.client.query.indexes.FetchIndex;
import com.basho.riak.client.query.indexes.IntIndex;
import nl.spotdog.riak.model.Person;

import java.util.List;
import java.util.UUID;

public class SecondaryIndex {
    public static void main(String[] args) {
        try {
            IRiakClient client = RiakFactory.pbcClient("192.168.56.101", 8087);
            Bucket myBucket = client.fetchBucket("second2").execute();

            Person person = new Person();
            person.uuid = UUID.randomUUID().toString();
            person.firstName = "Test";
            person.lastName = "First";
            person.age = 21;

            Person person2 = new Person();
            person2.uuid = UUID.randomUUID().toString();
            person2.firstName = "Test";
            person2.lastName = "Second";
            person2.age = 22;


            System.out.println("Storing Object in Riak...");
            myBucket.store(person).execute();
            myBucket.store(person2).execute();

            System.out.println("Reading Objects From Riak... (by age)");


            List<String> ageKeys = myBucket.fetchIndex(IntIndex.named("age")).from(21).to(23).execute();
//            List<String> ageKeys = myBucket.fetchIndex(IntIndex.named("age")).withValue(22).execute();
            System.out.println("Number of results: " + ageKeys.size());
            for (String key: ageKeys) {
                System.out.println("Key: " + key);
                Person p = myBucket.fetch(key, Person.class).execute();
                System.out.println(p);
            }

            System.out.println("Reading Objects From Riak... (by name");
            List<String> nameKeys = myBucket.fetchIndex(BinIndex.named("name")).withValue("First").execute();
            System.out.println("Number of results: " + nameKeys.size());
            for (String key: nameKeys) {
                System.out.println("Key: " + key);
                Person p = myBucket.fetch(key, Person.class).execute();
                System.out.println(p);
            }


            client.shutdown();
        } catch (RiakException e) {
            e.printStackTrace();
        }
    }
}
