package logic;

public enum TestType {
    TRIAL_DIVISION("Trial division"),
    MILLER_RABIN("Miller-Rabin"),
    CUSTOM("Custom TD"),
    AKS("AKS");

    String name;

    private TestType(String name) {
        this.name = name;

    }


    @Override
    public String toString() {
        return name;
    }
}
