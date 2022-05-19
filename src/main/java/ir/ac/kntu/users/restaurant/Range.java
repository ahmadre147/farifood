package ir.ac.kntu.users.restaurant;

public enum Range {
    ECONOMIC("$"),MEDIUM("$$"),LUXURY("$$$");

    private final String model;

    Range(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}
