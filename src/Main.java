import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Item> items = new ArrayList<>();

    public static void main(String[] args) {
        addItem();
        addItem();
        addItem();
        items.forEach(System.out::println);
    }

    private static void addItem() {
        Item item = getItemInput();
        if (item == null) return;
        items.add(item);
    }

    private static boolean itemExists(int itemId) {
        return items.stream().anyMatch(item -> item.getId() == itemId);
    }

    private static Item getItemInput() {
        int id = getInputId();
        if (id == -1) {
            sc.nextLine();
            return null;
        }

        String description = getInputDescription();
        if (description.isEmpty()) {
            return null;
        }

        float price = getInputPrice();
        if (price == -1) {
            sc.nextLine();
            return null;
        }

        int amount = getInputAmount();
        if (amount < 1) {
            sc.nextLine();
            return null;
        }

        return new Item(id, description, price, amount);
    }

    private static int getInputId() {
        System.out.print("Enter item ID: ");
        int id = 0;
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            if (itemExists(id)) {
                System.out.println("Item ID already exists");
                return -1;
            }
        }
        if (id < 1) {
            System.out.println("Invalid ID");
            return -1;
        }

        sc.nextLine();
        return id;
    }

    private static String getInputDescription() {
        System.out.print("Enter item description: ");
        String description = sc.nextLine();
        if (description.isEmpty()) {
            System.out.println("Items should have description");
            return "";
        }
        return description;
    }

    private static float getInputPrice() {
        System.out.print("Enter item price: ");
        float price = 0;
        if (sc.hasNextFloat()) price = sc.nextFloat();
        if (price <= 0) {
            System.out.println("Invalid price");
            return -1;
        }
        sc.nextLine();
        return price;
    }

    private static int getInputAmount() {
        System.out.print("Enter item amount: ");
        int amount = 0;
        if (sc.hasNextInt()) amount = sc.nextInt();
        if (amount < 1) {
            System.out.println("Invalid amount");
            return -1;
        }
        sc.nextLine();
        return amount;
    }
}
