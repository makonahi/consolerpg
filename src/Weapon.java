
public class Weapon extends Item {

    {
        this.rarity = getRandomRarity();
        this.name = generateItemName(this.rarity);
    }

    private static final String[] PREFIXES = {
            "Battle", "Guardian", "Shadow", "Storm", "Blood", "Arcane", "Doom",
            "Crimson", "Iron", "Frost", "Spectral", "Wraith", "Sunfire", "Thunder", "Venom",
            "Nightfall", "Steel", "Dragon", "Eclipse", "Chaos", "Eagle"
    };

    private static final String[] SUFFIXES = {
            "breaker", "blade", "fang", "strike", "smasher", "biter", "claw", "edge", "rend", "maul",
            "shard", "song", "bane", "reaver", "hammer", "fury"
    };

    private static final String[] BASE_NAMES = {
            "Broadsword\u001B[0m", "Axe\u001B[0m", "Dagger\u001B[0m", "Spear\u001B[0m", "Mace\u001B[0m", "Bow\u001B[0m"
    };

    private static final String[] TITLES = {
            "Masterful", "Fierce", "Ancient", "Strange", "War", "Dwarven", "Elven", "Corrupted", "Blessed",
            "Sinister", "Mirthful", "Serene", "Monk", "Temple", "Forgotten", "Historical"
    };

    private static final String[] MODIFIERS = {
            "Blessed by ", "Infused with power of ", "Cursed by ", "Awakened by ", "Forged by ", "Shrouded by ",
            "Bound to ", "Carved by ", "Sung by ", "Whispered by "
    };

    private static final String[] MODIFIERS_NAMES = {
        "Darkness God", "Moon Goddess", "Spirits of Flame", "Lightning God", "Forces of Dark Caves", "Forces of Elven Forests",
            "Forces of Evil", "Forces of Gods",  "Forces of Good", "Forces of Mountain Peaks",
            "Dwarven Spirit", "Elven Spirit", "Wind Spirit", "Earth Spirit", "Primal Forces of Nature",
            "Death", "Force of Wisdom", "Force of Warfare", "Unknown Deity", "Force of Craftsmanship",
            "Force of Alcohol", "Force of Snow", "Sun God", "Interdimensional beings", "Ancients"
    };

    private static final String[] ADORNMENTS = {
            "Inlaid with ", "Adorned with ", "Embellished with ", "Encrusted with ", "Set with ",
            "Decorated with "
    };

    private static final String[] MATERIALS = {
            "Rubies", "Emeralds", "Gold", "Diamonds", "Single Large Ruby", "Single Large Emerald",
            "Single Large Diamond", "Brass", "Bronze", "Pink Gold", "Black Diamond", "Yellow Diamond",
            "Amber Opal", "Moss Opal", "Milk Opal", "Rainbow Opal", "Fire Opal", "Scarlet Opal", "Jelly Opal",
            "Alexandrite", "Amethyst", "Aquamarine", "Cat's Eye Opal", "Crystal Opal", "Topaz", "Sapphire", "Zircon",
            "Copper", "Platinum", "Steel", "Silver", "Mithril", "Adamantium", "Divine Metal", "Fine Silk", "Pearls",
            "Obsidian", "Moonstone"
    };

    @Override
    protected String generateItemName(Rarity rarity) {
        String prefix = getRandomElement(PREFIXES);
        String suffix = getRandomElement(SUFFIXES);
        String baseName = getRandomElement(BASE_NAMES);
        String title = getRandomElement(TITLES);
        String modifier = getRandomElement(MODIFIERS);
        String modifier_name = getRandomElement(MODIFIERS_NAMES);
        String adornment = getRandomElement(ADORNMENTS);
        String materials = getRandomElement(MATERIALS);
        return switch (rarity) {
            case ORDINARY, COMMON, UNUSUAL, RARE -> String.format("%s %s", rarity.getName(), baseName);
            case SUPERIOR, EPIC, LEGENDARY -> {
                if (random.nextInt(3) == 0)
                    yield String.format("%s%s, %s %s %s, %s%s", prefix, suffix, rarity.getName(),
                            title, baseName, adornment, materials);
                yield String.format("%s%s, %s %s %s", prefix, suffix, rarity.getName(),
                        title, baseName);
            }
            default -> String.format("%s%s, %s %s %s, %s%s, %s%s",
                    prefix,
                    suffix,
                    rarity.getName(),
                    title,
                    baseName,
                    modifier,
                    modifier_name,
                    adornment,
                    materials);
        };
    }


}
