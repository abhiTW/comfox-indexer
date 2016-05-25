package jigsaw;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.jigsaw.Assignment;
import model.jigsaw.Person;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

public class JigsawCrawler {
    private JigsawHttpClient jigsawHttpClient;
    private Gson gson;

    public JigsawCrawler(JigsawHttpClient jigsawHttpClient, Gson gson) {
        this.jigsawHttpClient = jigsawHttpClient;
        this.gson = gson;
    }

    public List<Person> getJigsawPeopleData() throws IOException, URISyntaxException {
        String peopleJson = jigsawHttpClient.getPeopleWithWorkingOffice("Chennai");
        return unmarshallJigsawPeopleData(peopleJson);
    }

    public List<Assignment> getJigsawAssignmentsData() throws IOException, URISyntaxException {
        String assignmentsJson = jigsawHttpClient.getAssignments();
        return unmarshallJigsawAssignmentsData(assignmentsJson);
    }

    private List<Person> unmarshallJigsawPeopleData(String peopleJson) {
        Type collectionType = new TypeToken<List<Person>>() {}.getType();
        List<Person> people = gson.fromJson(peopleJson, collectionType);
        return people;
    }

    private List<Assignment> unmarshallJigsawAssignmentsData(String assignmentsJson) {
        Type collectionType = new TypeToken<List<Assignment>>() {}.getType();
        List<Assignment> assignments = gson.fromJson(assignmentsJson, collectionType);
        return assignments;
    }
}
