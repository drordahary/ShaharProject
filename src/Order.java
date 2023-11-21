import java.util.HashMap;

public class Order {
    private final int id;
    private final HashMap<Item, Integer> items; // Item ID -> amount to order
    private final String status;

    public Order(int id) {
        this.id = id;
        this.items = new HashMap<>();
        this.status = "Pending";
    }

    public int getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
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
        for (Item item : items.keySet()) {
            totalCost += item.getPrice() * items.get(item);
        }
        return totalCost;
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
