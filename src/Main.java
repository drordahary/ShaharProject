import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Item> items = new ArrayList<>();
    private static final ArrayList<UnitBudget> unitsBudget = new ArrayList<>();
    private static final ArrayList<Order> orders = new ArrayList<>();

    public static void main(String[] args) {

    }

    private static void addItem() {
        Item item = getItemInput();
        if (item == null) return;
        if (itemExists(item.getId())) {
            System.out.println("Item ID already exists");
            return;
        }
        items.add(item);
    }

    private static void removeItem() {
        int id = getInputId();
        if (!itemExists(id) && id != -1) {
            System.out.println("Item does not exists");
            return;
        }
        if (id == -1) {
            sc.nextLine();
            return;
        }
        items.removeIf(item -> item.getId() == id);
        for (Order order : orders) {
            order.removeItemOrder(id);
        }
    }

    private static void editItem() {
        Item item = getItemInput();
        if (item == null) return;
        if (!itemExists(item.getId())) {
            System.out.println("Item does not exists");
            return;
        }
        int itemIdx = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                itemIdx = i;
                break;
            }
        }
        items.get(itemIdx).setDescription(item.getDescription());
        items.get(itemIdx).setPrice(item.getPrice());
        items.get(itemIdx).setAmount(item.getAmount());
    }

    private static boolean itemExists(int itemId) {
        return items.stream().anyMatch(item -> item.getId() == itemId);
    }

    private static void addUnitBudget() {
        UnitBudget unitBudget = getUnitBudgetInput();
        if (unitBudget == null) return;
        if (unitBudgetExists(unitBudget.getId())) {
            System.out.println("Unit ID already exists");
            return;
        }
        unitsBudget.add(unitBudget);
    }

    private static void removeUnitBudget() {
        int id = getInputId();
        if (!unitBudgetExists(id) && id != -1) {
            System.out.println("Unit does not exists");
            return;
        }
        if (id == -1) {
            sc.nextLine();
            return;
        }
        unitsBudget.removeIf(unit -> unit.getId() == id);
    }

    private static void editUnitBudget() {
        UnitBudget unitBudget = getUnitBudgetInput();
        if (unitBudget == null) return;
        if (!unitBudgetExists(unitBudget.getId())) {
            System.out.println("Unit does not exists");
            return;
        }
        unitsBudget.removeIf(unit -> unit.getId() == unitBudget.getId());
        unitsBudget.add(unitBudget);
    }

    private static boolean unitBudgetExists(int id) {
        return unitsBudget.stream().anyMatch(unit -> unit.getId() == id);
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
        System.out.print("Enter ID: ");
        int id = 0;
        if (sc.hasNextInt()) {
            id = sc.nextInt();
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

    private static UnitBudget getUnitBudgetInput() {
        int id = getInputId();
        if (id == -1) {
            sc.nextLine();
            return null;
        }

        String unitName = getInputUnitName();
        if (unitName.isEmpty()) {
            return null;
        }

        float budget = getInputBudget();
        if (budget == -1) {
            sc.nextLine();
            return null;
        }

        return new UnitBudget(id, unitName, budget);
    }

    private static String getInputUnitName() {
        System.out.print("Enter unit name: ");
        String unitName = sc.nextLine();
        if (unitName.isEmpty()) {
            System.out.println("Unit name input empty");
            return "";
        }
        return unitName;
    }

    private static float getInputBudget() {
        System.out.print("Enter unit budget: ");
        float price = 0;
        if (sc.hasNextFloat()) price = sc.nextFloat();
        if (price <= 0) {
            System.out.println("Invalid budget");
            return -1;
        }
        sc.nextLine();
        return price;
    }
}
