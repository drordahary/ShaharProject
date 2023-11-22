import java.util.HashMap;

public class Order {
    private final int id;
    private final int unitId;
    private final HashMap<Item, Integer> items; // Item ID -> amount to order
    private String status;

    public Order(int id, int unitId) {
        this.id = id;
        this.unitId = unitId;
        this.items = new HashMap<>();
        this.status = "Pending";
    }

    public int getId() {
        return this.id;
    }

    public int getUnitId() {
        return this.unitId;
    }

    public String getStatus() {
        return this.status;
    }

    public HashMap<Item, Integer> getItems() {
        return this.items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addItemOrder(Item item, int amount) {
        this.items.put(item, amount);
    }

    public void removeItemOrder(int searchItemId) {
        for (Item item : items.keySet()) {
            if (item.getId() == searchItemId) {
                items.remove(item);
                return;
            }
        }
    }

    public void editItemOrder(int searchItemId, int newAmount) {
        for (Item item : items.keySet()) {
            if (item.getId() == searchItemId) {
                items.put(item, newAmount);
                return;
            }
        }
    }

    public float calculateCost() {
        float totalCost = 0;
        for (Item item : this.items.keySet()) {
            totalCost += item.getPrice() * this.items.get(item);
        }
        return totalCost;
    }

    public boolean isBudgetSufficient(float budget) {
        return calculateCost() <= budget;
    }

    // Show all the items that deny the order from completion
    public void showBlockingOrderItems(float budget) {
        float currentCost = 0;
        for (Item item : this.items.keySet()) {
            if (currentCost > budget) {
                System.out.println(item);
                System.out.println("Amount ordering: " + this.items.get(item));
            }
            currentCost += item.getPrice() * this.items.get(item);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order ID: %d\nStatus: %s\nCost: %.2f\nItems:\n",
                this.id, this.status, calculateCost()));
        for (Item item : this.items.keySet()) {
            sb.append(item);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.id == ((Order) obj).getId();
    }
}
