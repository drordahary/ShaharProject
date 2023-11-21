import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Item> items = new ArrayList<>();
    private static final ArrayList<UnitBudget> unitsBudget = new ArrayList<>();
    private static final ArrayList<Order> orders = new ArrayList<>();
    private static final HashMap<Integer, Integer> unitsOrders = new HashMap<>(); // Order ID -> Unit ID

    private enum Choice {
        ADD_ITEM,
        REMOVE_ITEM,
        EDIT_ITEM,
        SHOW_ITEMS,
        ADD_UNIT,
        REMOVE_UNIT,
        EDIT_UNIT,
        SHOW_UNITS,
        ADD_ORDER,
        REMOVE_ORDER,
        EDIT_ORDER,
        SHOW_ORDERS,
        SEARCH_ORDER,
        FILTER_ORDERS,
        EXECUTE_ORDER,
        EXIT
    }

    private enum EditOrderChoice {
        ADD_ITEM_TO_ORDER,
        REMOVE_ITEM_FROM_ORDER,
        EDIT_ITEM_IN_ORDER,
        SHOW_ITEMS_IN_ORDER,
        FINISH_EDITING
    }

    public static void main(String[] args) {
        Choice choice = null;
        int input = -1;

        while (input != Choice.EXIT.ordinal()) {
            if (sc.hasNextInt()) {
                input = sc.nextInt() - 1;
                if (Choice.SHOW_ORDERS.ordinal() >= input && input >= 0)
                    choice = Choice.values()[input];
            }
            if (input <= -1 || choice == null) {
                System.out.println("Invalid input");
                continue;
            }

            switch (choice) {
                case ADD_ITEM -> addItem();
                case REMOVE_ITEM -> removeItem();
                case EDIT_ITEM -> editItem();
                case SHOW_ITEMS -> showItems();
                case ADD_UNIT -> addUnitBudget();
                case REMOVE_UNIT -> removeUnitBudget();
                case EDIT_UNIT -> editUnitBudget();
                case SHOW_UNITS -> showUnits();
                case ADD_ORDER -> {
                    int unitId = getUnitIdIfExists();
                    if (unitId != -1) addOrder(unitId);
                }
                case REMOVE_ORDER -> {
                    int orderId = getOrderIdIfExists();
                    if (orderId != -1) removeOrder(orderId);
                }
                case EDIT_ORDER -> System.out.println("Not implemented");
                case SHOW_ORDERS -> showOrders();
                case SEARCH_ORDER -> {
                    int orderId = getOrderIdIfExists();
                    if (orderId != -1) searchOrder(orderId);
                }
                case FILTER_ORDERS -> {
                    String status = sc.nextLine();
                    filterOrdersByStatus(status);
                }
                case EXECUTE_ORDER -> System.out.println("Not implemented");
                case EXIT -> sc.close();
            }
        }
    }

    private static void showMenu() {
        System.out.println("1. Add item");
        System.out.println("2. Remove item");
        System.out.println("3. Edit item");
        System.out.println("4. Show items");
        System.out.println("5. Add unit");
        System.out.println("6. Remove unit");
        System.out.println("7. Edit unit");
        System.out.println("8. Show units");
        System.out.println("9. Add order");
        System.out.println("10. Remove order");
        System.out.println("11. Edit order");
        System.out.println("12. Show orders");
        System.out.println("13. Search order");
        System.out.println("14. Filter orders by status");
        System.out.println("15. Execute order");
        System.out.println("16. Exit");
    }

    private static void searchOrder(int orderId) {
        System.out.println(orders.stream().filter(order -> order.getId() == orderId).findFirst());
    }

    private static void filterOrdersByStatus(String status) {
        for (Order order : orders) {
            if (order.getStatus().equals(status)) {
                System.out.println(order);
            }
        }
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

    private static void showItems() {
        items.forEach(System.out::println);
    }

    private static void showUnits() {
        unitsBudget.forEach(System.out::println);
    }

    private static void showOrders() {
        orders.forEach(System.out::println);
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

    private static int getUnitIdIfExists() {
        System.out.print("(Unit ID to attach the order to) ");
        int unitId = getInputId();
        if (!unitBudgetExists(unitId) && unitId != -1) {
            System.out.println("Unit does not exists");
            return -1;
        }
        if (unitId == -1) {
            sc.nextLine();
            return -1;
        }
        return unitId;
    }

    private static void addOrder(int unitId) {
        Order order = getOrderInput();
        if (order == null) return;

        unitsOrders.put(order.getId(), unitId);
        orders.add(order);
        for (UnitBudget unitBudget : unitsBudget) {
            if (unitBudget.getId() == unitId) {
                unitBudget.addOrder(order);
                return;
            }
        }
    }

    private static int getOrderIdIfExists() {
        int orderId = getInputId();
        if (!orderExists(orderId) && orderId != -1) {
            System.out.println("Order does not exists");
            return -1;
        }
        if (orderId == -1) {
            sc.nextLine();
            return -1;
        }
        return orderId;
    }

    private static void removeOrder(int orderId) {
        for (Order order : orders) {
            if (order.getId() == orderId && !order.getStatus().equals("Pending")) {
                System.out.println("Cannot cancel order because it is not pending");
                return;
            }
        }

        int unitId = unitsOrders.get(orderId);
        orders.removeIf(order -> order.getId() == orderId);
        unitsOrders.remove(orderId);
        for (UnitBudget unitBudget : unitsBudget) {
            if (unitBudget.getId() == unitId) {
                unitBudget.removeOrder(orderId);
                return;
            }
        }
    }

    private int[] getItemOrderInformation() {
        System.out.print("(Order) ");
        int orderId = getInputId();
        if (!orderExists(orderId) && orderId != -1) {
            System.out.println("Order does not exists");
            return null;
        }
        if (orderId == -1) {
            sc.nextLine();
            return null;
        }

        System.out.print("(Item) ");
        int itemId = getInputId();
        if (!itemExists(itemId) && itemId != -1) {
            System.out.println("Order does not exists");
            return null;
        }
        if (itemId == -1) {
            sc.nextLine();
            return null;
        }

        return new int[] { orderId, itemId };
    }

    private int getAmountToOrder() {
        int amount = getInputAmount();
        if (amount < 1) {
            sc.nextLine();
            return -1;
        }
        return amount;
    }

    private void addItemToOrder(int orderId, int itemId, int amount) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                order.addItemOrder(items.stream().filter(item -> item.getId() == itemId)
                        .findFirst().orElse(null), amount);
            }
        }
    }

    private void removeItemFromOrder(int orderId, int itemId) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                order.removeItemOrder(itemId);
            }
        }
    }

    private void editItemInOrder(int orderId, int itemId, int amount) {
        for (Order order : orders) {
            if (order.getId() == orderId) {
                order.editItemOrder(itemId, amount);
            }
        }
    }

    private static boolean orderExists(int id) {
        return orders.stream().anyMatch(order -> order.getId() == id);
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

    private static Order getOrderInput() {
        int id = getInputId();
        if (id == -1) {
            sc.nextLine();
            return null;
        }
        if (orderExists(id)) {
            System.out.println("Order already exists");
            return null;
        }
        return new Order(id);
    }
}
