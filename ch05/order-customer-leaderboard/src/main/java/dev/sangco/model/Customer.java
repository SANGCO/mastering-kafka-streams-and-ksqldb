package dev.sangco.model;

public class Customer {

    private String id;
    private String name;
    private String address;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "{\"Customer\":{"
                + "        \"id\":\"" + id + "\""
                + ",         \"name\":\"" + name + "\""
                + ",         \"address\":\"" + address + "\""
                + "}}";
    }

}
