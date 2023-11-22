import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonHandler {
    private final String itemsPath = "./src/data/items.json";
    private final String ordersPath = "./src/data/orders.json";
    private final String unitsPath = "./src/data/units.json";
    private JSONArray jsonArray;
    private final JSONArray itemsJsonArray = new JSONArray();
    private final JSONArray ordersJsonArray = new JSONArray();
    private final JSONArray unitsJsonArray = new JSONArray();

    private String getJsonDataAsString(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            // Read the entire file into a StringBuilder
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public ArrayList<Item> readJsonItems() {
        jsonArray = new JSONArray(getJsonDataAsString(itemsPath));
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String description = jsonObject.getString("description");
            float price = jsonObject.getFloat("price");
            int amount = jsonObject.getInt("amount");
            items.add(new Item(id, description, price, amount));
        }
        return items;
    }

    public ArrayList<Order> readJsonOrders(ArrayList<Item> items) {
        jsonArray = new JSONArray(getJsonDataAsString(ordersPath));
        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            int unitId = jsonObject.getInt("unitId");
            String status = jsonObject.getString("status");
            JSONArray itemsIds = jsonObject.getJSONArray("itemsIds");
            Order order = new Order(id, unitId);
            order.setStatus(status);
            addItemsToOrder(order, itemsIds, items);
            orders.add(order);
        }
        return orders;
    }

    private void addItemsToOrder(Order order, JSONArray itemsIds, ArrayList<Item> items) {
        for (int i = 0; i < itemsIds.length(); i++) {
            JSONObject jsonObject = itemsIds.getJSONObject(i);
            int id = jsonObject.getInt("id");
            int amount = jsonObject.getInt("amount");
            Item item = items.stream()
                    .filter(it -> it.getId() == id)
                    .findFirst()
                    .orElse(null);
            order.addItemOrder(item, amount);
        }
    }

    public ArrayList<UnitBudget> readJsonUnits(ArrayList<Order> orders) {
        jsonArray = new JSONArray(getJsonDataAsString(unitsPath));
        ArrayList<UnitBudget> unitBudgets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String unitName = jsonObject.getString("unitName");
            float budget = jsonObject.getFloat("budget");
            JSONArray ordersIds = jsonObject.getJSONArray("ordersIds");
            UnitBudget unitBudget = new UnitBudget(id, unitName, budget);
            addOrdersToUnit(unitBudget, ordersIds, orders);
            unitBudgets.add(unitBudget);
        }
        return unitBudgets;
    }

    private void addOrdersToUnit(UnitBudget unitBudget, JSONArray ordersIds,ArrayList<Order> orders) {
        for (int i = 0; i < ordersIds.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            Order order = orders.stream()
                    .filter(it -> it.getId() == id)
                    .findFirst()
                    .orElse(null);
            unitBudget.addOrder(order);
        }
    }

    public void writeItemsToFile(ArrayList<Item> items) {
        this.itemsJsonArray.clear();
        for (Item item : items) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId());
            jsonObject.put("description", item.getDescription());
            jsonObject.put("price", item.getPrice());
            jsonObject.put("amount", item.getAmount());
            this.itemsJsonArray.put(jsonObject);
        }
        try (FileWriter fileWriter = new FileWriter(itemsPath)) {
            // Indentation of 2 spaces for better readability
            fileWriter.write(this.itemsJsonArray.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOrdersToFile(ArrayList<Order> orders) {
        this.ordersJsonArray.clear();
        for (Order order : orders) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", order.getId());
            jsonObject.put("unitId", order.getUnitId());
            jsonObject.put("status", order.getStatus());
            JSONArray jsonArrayOfIds = new JSONArray();
            HashMap<Item, Integer> items = order.getItems();
            for (Item item : items.keySet()) {
                JSONObject itemOrder = new JSONObject();
                itemOrder.put("id", item.getId());
                itemOrder.put("amount", items.get(item));
                jsonArrayOfIds.put(itemOrder);
            }
            jsonObject.put("itemsIds", jsonArrayOfIds);
            this.ordersJsonArray.put(jsonObject);
        }
        try (FileWriter fileWriter = new FileWriter(ordersPath)) {
            fileWriter.write(this.ordersJsonArray.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUnitsToFile(ArrayList<UnitBudget> unitBudgets) {
        this.unitsJsonArray.clear();
        for (UnitBudget unitBudget : unitBudgets) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", unitBudget.getId());
            jsonObject.put("unitName", unitBudget.getUnitName());
            jsonObject.put("budget", unitBudget.getBudget());
            JSONArray jsonArrayOfIds = new JSONArray();
            ArrayList<Order> orders = unitBudget.getOrders();
            for (Order order : orders) {
                jsonArrayOfIds.put(order.getId());
            }
            jsonObject.put("ordersIds", jsonArrayOfIds);
            this.unitsJsonArray.put(jsonObject);
        }
        try (FileWriter fileWriter = new FileWriter(unitsPath)) {
            fileWriter.write(this.unitsJsonArray.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
