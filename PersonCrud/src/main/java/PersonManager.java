import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonManager {
    private static final String DATA_FILE = "people.json";
    private List<Person> people;
    private ObjectMapper objectMapper;

    public PersonManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try {
                people = objectMapper.readValue(file, new TypeReference<List<Person>>() {});
            } catch (IOException e) {
                System.err.println("Error loading  " + e.getMessage());
                people = new ArrayList<>();
            }
        } else {
            people = new ArrayList<>();
        }
    }

    private void saveToFile() {
        try {
            objectMapper.writeValue(new File(DATA_FILE), people);
        } catch (IOException e) {
            System.err.println("Error saving  " + e.getMessage());
        }
    }

    public void createPerson(String name, String email) {
        int newId = people.stream().mapToInt(Person::getId).max().orElse(0) + 1;
        Person person = new Person(newId, name, email);
        people.add(person);
        saveToFile();
        System.out.println("Person created: " + person);
    }

    public void retrieveAll() {
        if (people.isEmpty()) {
            System.out.println("No persons found.");
            return;
        }
        System.out.println("\n All Persons:");
        people.forEach(System.out::println);
    }

    public void retrieveById(int id) {
        Optional<Person> person = people.stream().filter(p -> p.getId() == id).findFirst();
        if (person.isPresent()) {
            System.out.println(" Found: " + person.get());
        } else {
            System.out.println(" Person with ID " + id + " not found.");
        }
    }

    public void updatePerson(int id, String name, String email) {
        Optional<Person> existing = people.stream().filter(p -> p.getId() == id).findFirst();
        if (existing.isPresent()) {
            Person p = existing.get();
            if (name != null && !name.trim().isEmpty()) p.setName(name);
            if (email != null && !email.trim().isEmpty()) p.setEmail(email);
            saveToFile();
            System.out.println("Updated: " + p);
        } else {
            System.out.println("Person with ID " + id + " not found.");
        }
    }

    public void deletePerson(int id) {
        boolean removed = people.removeIf(p -> p.getId() == id);
        if (removed) {
            saveToFile();
            System.out.println("Person with ID " + id + " deleted.");
        } else {
            System.out.println("Person with ID " + id + " not found.");
        }
    }
}