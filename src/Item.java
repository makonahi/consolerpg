import java.util.Random;

abstract class Item {
    public int requiredLVL;
    public int weight;
    public int price;
    public String name;
    public enum Rarity {
        ORDINARY("Ordinary", 50D),
        COMMON("Common", 30D),
        UNUSUAL("Unusual", 10D),
        RARE("Rare", 5D),
        SUPERIOR("Superior", 2.5D),
        EPIC("Epic", 1.5D),
        LEGENDARY("The \u001B[33mLegendary", 0.5D),
        MYTHICAL("The \u001B[0;92mMythical", 0.35D),
        CELESTIAL("The \u001B[35mCelestial", 0.08D),
        TRANSCENDENT("The \u001B[0;96mTranscendent", 0.04D),
        DIVINE("The \u001B[0;95mDivine", 0.02D),
        ETERNAL("The \u001B[31mEternal", 0.01D);

        private final String name;
        private final double chance;

        Rarity(String name, Double chance) {
            this.name = name;
            this.chance = chance;
        }

        public String getName() {
            return name;
        }

        public Double getChance() {
            return chance;
        }
    }
    Rarity rarity;

    protected static final Random random = new Random();

    protected abstract String generateItemName(Rarity rarity);

    protected static String getRandomElement(String[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }

    protected static Rarity getRandomRarity() {
        double totalWeight = 0.0;

        for (Rarity rarity : Rarity.values()) {
            totalWeight += rarity.getChance();
        }

        double randomNumber = random.nextDouble() * totalWeight;

        double cumulativeWeight = 0.0;

        for (Rarity rarity : Rarity.values()) {
            cumulativeWeight += rarity.getChance();
            if (randomNumber <= cumulativeWeight) {
                return rarity;
            }
        }

        return Rarity.ETERNAL;
    }

}
