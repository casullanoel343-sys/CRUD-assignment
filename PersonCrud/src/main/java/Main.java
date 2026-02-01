import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PersonManager manager = new PersonManager();
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println(" -----Welcome to the Person CRUD Application!------");
        while (true) {
            System.out.println("\n---- MENU ----");
            System.out.println("1. Create Person");
            System.out.println("2. Retrieve All Persons");
            System.out.println("3. Retrieve Person by ID");
            System.out.println("4. Update Person");
            System.out.println("5. Delete Person");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    manager.createPerson(name, email);
                    break;

                case "2":
                    manager.retrieveAll();
                    break;

                case "3":
                    System.out.print("Enter ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    manager.retrieveById(id);
                    break;

                case "4":
                    System.out.print("Enter ID to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter new name (or press Enter to skip): ");
                    String newName = scanner.nextLine();
                    if (newName.trim().isEmpty()) newName = null;
                    System.out.print("Enter new email (or press Enter to skip): ");
                    String newEmail = scanner.nextLine();
                    if (newEmail.trim().isEmpty()) newEmail = null;
                    manager.updatePerson(updateId, newName, newEmail);
                    break;

                case "5":
                    System.out.print("Enter ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    manager.deletePerson(deleteId);
                    break;

                case "6":
                    System.out.println("bye bye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}