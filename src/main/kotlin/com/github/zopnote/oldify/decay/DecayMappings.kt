package com.github.zopnote.oldify.decay

import org.bukkit.Material

/**
 * Definiert welche Blöcke nicht natürlich in der Oberwelt vorkommen können
 * und mappt Spielermaterialien auf passende Zerfallsprodukte.
 *
 * Alle Lookups verwenden HashSet/HashMap für O(1) Zeitkomplexität.
 */
object DecayMappings {

    /** Generischer Rückfall-Zerfallsblock wenn kein spezifisches Mapping existiert */
    val GENERIC_DECAY_BLOCK: Material = Material.COBBLESTONE

    /**
     * Materialien die Zwei-Block-Strukturen bilden und besondere Behandlung brauchen.
     * Bei diesen muss immer der gesamte Doppelblock entfernt werden.
     */
    val DOUBLE_BLOCK_MATERIALS: HashSet<Material> = hashSetOf(
        // Türen (obere und untere Hälfte)
        Material.OAK_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR,
        Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR,
        Material.MANGROVE_DOOR, Material.CHERRY_DOOR, Material.BAMBOO_DOOR,
        Material.CRIMSON_DOOR, Material.WARPED_DOOR, Material.IRON_DOOR,
        Material.COPPER_DOOR, Material.EXPOSED_COPPER_DOOR,
        Material.WEATHERED_COPPER_DOOR, Material.OXIDIZED_COPPER_DOOR,
        // Betten (Kopf und Fuß)
        Material.WHITE_BED, Material.ORANGE_BED, Material.MAGENTA_BED,
        Material.LIGHT_BLUE_BED, Material.YELLOW_BED, Material.LIME_BED,
        Material.PINK_BED, Material.GRAY_BED, Material.LIGHT_GRAY_BED,
        Material.CYAN_BED, Material.PURPLE_BED, Material.BLUE_BED,
        Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED,
        Material.BLACK_BED,
        // Hohe Pflanzen/Blumen (die spielerplatziert sein könnten)
        Material.TALL_SEAGRASS, Material.SUNFLOWER, Material.LILAC,
        Material.ROSE_BUSH, Material.PEONY, Material.LARGE_FERN,
        Material.TALL_GRASS, Material.PITCHER_PLANT
    )

    /**
     * Materialien die Treppen-artige Ausrichtung haben (BlockData: Stairs).
     * Beim Ersetzen muss die Ausrichtung übertragen werden.
     */
    val DIRECTIONAL_STAIR_TYPES: HashSet<Material> = hashSetOf(
        Material.OAK_STAIRS, Material.SPRUCE_STAIRS, Material.BIRCH_STAIRS,
        Material.JUNGLE_STAIRS, Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS,
        Material.MANGROVE_STAIRS, Material.CHERRY_STAIRS, Material.BAMBOO_STAIRS,
        Material.CRIMSON_STAIRS, Material.WARPED_STAIRS,
        Material.STONE_STAIRS, Material.STONE_BRICK_STAIRS,
        Material.COBBLESTONE_STAIRS, Material.MOSSY_COBBLESTONE_STAIRS,
        Material.BRICK_STAIRS, Material.SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS,
        Material.GRANITE_STAIRS, Material.DIORITE_STAIRS, Material.ANDESITE_STAIRS,
        Material.POLISHED_GRANITE_STAIRS, Material.POLISHED_DIORITE_STAIRS,
        Material.POLISHED_ANDESITE_STAIRS,
        Material.NETHER_BRICK_STAIRS, Material.RED_NETHER_BRICK_STAIRS,
        Material.QUARTZ_STAIRS, Material.PURPUR_STAIRS,
        Material.PRISMARINE_STAIRS, Material.PRISMARINE_BRICK_STAIRS,
        Material.DARK_PRISMARINE_STAIRS,
        Material.DEEPSLATE_BRICK_STAIRS, Material.DEEPSLATE_TILE_STAIRS,
        Material.COBBLED_DEEPSLATE_STAIRS, Material.POLISHED_DEEPSLATE_STAIRS,
        Material.MUD_BRICK_STAIRS, Material.SMOOTH_QUARTZ_STAIRS,
        Material.SMOOTH_SANDSTONE_STAIRS, Material.SMOOTH_RED_SANDSTONE_STAIRS,
        Material.CUT_COPPER_STAIRS, Material.EXPOSED_CUT_COPPER_STAIRS,
        Material.WEATHERED_CUT_COPPER_STAIRS, Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.WAXED_CUT_COPPER_STAIRS, Material.WAXED_EXPOSED_CUT_COPPER_STAIRS,
        Material.WAXED_WEATHERED_CUT_COPPER_STAIRS, Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
        Material.BLACKSTONE_STAIRS, Material.POLISHED_BLACKSTONE_STAIRS,
        Material.POLISHED_BLACKSTONE_BRICK_STAIRS,
        Material.END_STONE_BRICK_STAIRS, Material.TUFF_STAIRS,
        Material.POLISHED_TUFF_STAIRS, Material.TUFF_BRICK_STAIRS
    )

    /**
     * Materialien die Slab-artige Ausrichtung haben (BlockData: Slab).
     * Beim Ersetzen muss top/bottom/double übertragen werden.
     */
    val DIRECTIONAL_SLAB_TYPES: HashSet<Material> = hashSetOf(
        Material.OAK_SLAB, Material.SPRUCE_SLAB, Material.BIRCH_SLAB,
        Material.JUNGLE_SLAB, Material.ACACIA_SLAB, Material.DARK_OAK_SLAB,
        Material.MANGROVE_SLAB, Material.CHERRY_SLAB, Material.BAMBOO_SLAB,
        Material.CRIMSON_SLAB, Material.WARPED_SLAB,
        Material.STONE_SLAB, Material.STONE_BRICK_SLAB,
        Material.COBBLESTONE_SLAB, Material.MOSSY_COBBLESTONE_SLAB,
        Material.BRICK_SLAB, Material.SANDSTONE_SLAB, Material.RED_SANDSTONE_SLAB,
        Material.GRANITE_SLAB, Material.DIORITE_SLAB, Material.ANDESITE_SLAB,
        Material.POLISHED_GRANITE_SLAB, Material.POLISHED_DIORITE_SLAB,
        Material.POLISHED_ANDESITE_SLAB, Material.SMOOTH_STONE_SLAB,
        Material.NETHER_BRICK_SLAB, Material.RED_NETHER_BRICK_SLAB,
        Material.QUARTZ_SLAB, Material.PURPUR_SLAB,
        Material.PRISMARINE_SLAB, Material.PRISMARINE_BRICK_SLAB,
        Material.DARK_PRISMARINE_SLAB,
        Material.DEEPSLATE_BRICK_SLAB, Material.DEEPSLATE_TILE_SLAB,
        Material.COBBLED_DEEPSLATE_SLAB, Material.POLISHED_DEEPSLATE_SLAB,
        Material.MUD_BRICK_SLAB, Material.SMOOTH_QUARTZ_SLAB,
        Material.SMOOTH_SANDSTONE_SLAB, Material.SMOOTH_RED_SANDSTONE_SLAB,
        Material.CUT_COPPER_SLAB, Material.EXPOSED_CUT_COPPER_SLAB,
        Material.WEATHERED_CUT_COPPER_SLAB, Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.WAXED_CUT_COPPER_SLAB, Material.WAXED_EXPOSED_CUT_COPPER_SLAB,
        Material.WAXED_WEATHERED_CUT_COPPER_SLAB, Material.WAXED_OXIDIZED_CUT_COPPER_SLAB,
        Material.BLACKSTONE_SLAB, Material.POLISHED_BLACKSTONE_SLAB,
        Material.POLISHED_BLACKSTONE_BRICK_SLAB,
        Material.END_STONE_BRICK_SLAB, Material.TUFF_SLAB,
        Material.POLISHED_TUFF_SLAB, Material.TUFF_BRICK_SLAB
    )

    /**
     * Blöcke die natürlich in der Oberwelt generiert werden können.
     * Alles was NICHT in dieser Menge ist, gilt als Spielerplatziert.
     *
     * HashSet für O(1) contains()-Abfragen.
     */
    val NATURAL_OVERWORLD_BLOCKS: HashSet<Material> = hashSetOf(
        // ========== LUFT & TECHNISCHE BLÖCKE ==========
        Material.AIR, Material.CAVE_AIR, Material.VOID_AIR,
        Material.BARRIER, Material.LIGHT, Material.STRUCTURE_VOID,
        Material.STRUCTURE_BLOCK, Material.JIGSAW,
        Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK, Material.REPEATING_COMMAND_BLOCK,

        // ========== GRUNDGESTEIN ==========
        Material.BEDROCK,

        // ========== STEIN & VARIANTEN ==========
        Material.STONE, Material.GRANITE, Material.DIORITE, Material.ANDESITE,
        Material.DEEPSLATE, Material.COBBLED_DEEPSLATE,
        Material.TUFF, Material.CALCITE, Material.SMOOTH_BASALT,
        Material.DRIPSTONE_BLOCK, Material.POINTED_DRIPSTONE,

        // ========== ERDE & OBERFLÄCHE ==========
        Material.DIRT, Material.GRASS_BLOCK, Material.PODZOL, Material.MYCELIUM,
        Material.COARSE_DIRT, Material.ROOTED_DIRT, Material.MUD,
        Material.MUDDY_MANGROVE_ROOTS, Material.FARMLAND, Material.DIRT_PATH,
        Material.CLAY, Material.PACKED_MUD,

        // ========== SAND & KIES ==========
        Material.SAND, Material.RED_SAND, Material.GRAVEL,
        Material.SANDSTONE, Material.RED_SANDSTONE,
        Material.SUSPICIOUS_SAND, Material.SUSPICIOUS_GRAVEL,

        // ========== ERZE (alle Varianten) ==========
        Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
        Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
        Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE,
        Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
        Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
        Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
        Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
        Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
        Material.NETHER_GOLD_ORE, // Ruined Portals
        Material.RAW_IRON_BLOCK, Material.RAW_COPPER_BLOCK, Material.RAW_GOLD_BLOCK,

        // ========== FLÜSSIGKEITEN ==========
        Material.WATER, Material.LAVA,

        // ========== HOLZ (natürliche Baumstämme, NICHT geschält) ==========
        Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
        Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
        Material.MANGROVE_LOG, Material.CHERRY_LOG,
        // Holz-Blöcke (Bark on all sides, natürlich in Riesenbäumen)
        Material.OAK_WOOD, Material.SPRUCE_WOOD, Material.BIRCH_WOOD,
        Material.JUNGLE_WOOD, Material.ACACIA_WOOD, Material.DARK_OAK_WOOD,
        Material.MANGROVE_WOOD, Material.CHERRY_WOOD,
        Material.MANGROVE_ROOTS,

        // ========== BLÄTTER ==========
        Material.OAK_LEAVES, Material.SPRUCE_LEAVES, Material.BIRCH_LEAVES,
        Material.JUNGLE_LEAVES, Material.ACACIA_LEAVES, Material.DARK_OAK_LEAVES,
        Material.MANGROVE_LEAVES, Material.CHERRY_LEAVES,
        Material.AZALEA_LEAVES, Material.FLOWERING_AZALEA_LEAVES,

        // ========== VEGETATION ==========
        Material.SHORT_GRASS, Material.TALL_GRASS, Material.FERN, Material.LARGE_FERN,
        Material.DEAD_BUSH,
        // Blumen
        Material.DANDELION, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM,
        Material.AZURE_BLUET, Material.RED_TULIP, Material.ORANGE_TULIP,
        Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY,
        Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY,
        Material.TORCHFLOWER, Material.PITCHER_PLANT,
        Material.WITHER_ROSE, Material.PINK_PETALS,
        // Hohe Blumen
        Material.SUNFLOWER, Material.LILAC, Material.ROSE_BUSH, Material.PEONY,
        // Spore Blossom (Lush Caves)
        Material.SPORE_BLOSSOM,
        // Nutzpflanzen (natürliche Vorkommen)
        Material.SUGAR_CANE, Material.CACTUS, Material.BAMBOO,
        Material.SWEET_BERRY_BUSH,
        Material.PUMPKIN, Material.MELON,
        Material.ATTACHED_PUMPKIN_STEM, Material.ATTACHED_MELON_STEM,
        Material.PUMPKIN_STEM, Material.MELON_STEM,
        // Ranken & Hängendes
        Material.VINE, Material.GLOW_LICHEN,
        Material.CAVE_VINES, Material.CAVE_VINES_PLANT,
        Material.HANGING_ROOTS,
        // Seerosen
        Material.LILY_PAD,
        // Dripleaf
        Material.SMALL_DRIPLEAF, Material.BIG_DRIPLEAF, Material.BIG_DRIPLEAF_STEM,
        // Moos & Azaleen
        Material.AZALEA, Material.FLOWERING_AZALEA,
        Material.MOSS_BLOCK, Material.MOSS_CARPET,
        // Unterwasserpflanzen
        Material.KELP, Material.KELP_PLANT,
        Material.SEAGRASS, Material.TALL_SEAGRASS,
        Material.SEA_PICKLE,
        // Pitcher Pod (wächst natürlich in Sniffer-Archäologie-Kontext)
        Material.PITCHER_CROP,
        Material.TORCHFLOWER_CROP,

        // ========== PILZE ==========
        Material.BROWN_MUSHROOM, Material.RED_MUSHROOM,
        Material.BROWN_MUSHROOM_BLOCK, Material.RED_MUSHROOM_BLOCK,
        Material.MUSHROOM_STEM,

        // ========== EIS & SCHNEE ==========
        Material.ICE, Material.PACKED_ICE, Material.BLUE_ICE,
        Material.SNOW, Material.SNOW_BLOCK, Material.POWDER_SNOW,
        Material.FROSTED_ICE,

        // ========== OZEAN & KORALLEN ==========
        Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK,
        Material.BUBBLE_CORAL_BLOCK, Material.FIRE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK,
        Material.DEAD_TUBE_CORAL_BLOCK, Material.DEAD_BRAIN_CORAL_BLOCK,
        Material.DEAD_BUBBLE_CORAL_BLOCK, Material.DEAD_FIRE_CORAL_BLOCK,
        Material.DEAD_HORN_CORAL_BLOCK,
        Material.TUBE_CORAL, Material.BRAIN_CORAL,
        Material.BUBBLE_CORAL, Material.FIRE_CORAL, Material.HORN_CORAL,
        Material.DEAD_TUBE_CORAL, Material.DEAD_BRAIN_CORAL,
        Material.DEAD_BUBBLE_CORAL, Material.DEAD_FIRE_CORAL, Material.DEAD_HORN_CORAL,
        Material.TUBE_CORAL_FAN, Material.BRAIN_CORAL_FAN,
        Material.BUBBLE_CORAL_FAN, Material.FIRE_CORAL_FAN, Material.HORN_CORAL_FAN,
        Material.DEAD_TUBE_CORAL_FAN, Material.DEAD_BRAIN_CORAL_FAN,
        Material.DEAD_BUBBLE_CORAL_FAN, Material.DEAD_FIRE_CORAL_FAN,
        Material.DEAD_HORN_CORAL_FAN,
        Material.TUBE_CORAL_WALL_FAN, Material.BRAIN_CORAL_WALL_FAN,
        Material.BUBBLE_CORAL_WALL_FAN, Material.FIRE_CORAL_WALL_FAN,
        Material.HORN_CORAL_WALL_FAN,
        Material.DEAD_TUBE_CORAL_WALL_FAN, Material.DEAD_BRAIN_CORAL_WALL_FAN,
        Material.DEAD_BUBBLE_CORAL_WALL_FAN, Material.DEAD_FIRE_CORAL_WALL_FAN,
        Material.DEAD_HORN_CORAL_WALL_FAN,

        // ========== SCULK (Deep Dark) ==========
        Material.SCULK, Material.SCULK_CATALYST, Material.SCULK_SENSOR,
        Material.SCULK_SHRIEKER, Material.SCULK_VEIN,

        // ========== AMETHYST (Geoden) ==========
        Material.AMETHYST_BLOCK, Material.BUDDING_AMETHYST,
        Material.AMETHYST_CLUSTER,
        Material.SMALL_AMETHYST_BUD, Material.MEDIUM_AMETHYST_BUD,
        Material.LARGE_AMETHYST_BUD,

        // ========== FOSSILIEN ==========
        Material.BONE_BLOCK,

        // ========== MAGMA ==========
        Material.MAGMA_BLOCK,

        // ========== OBSIDIAN (natürlich in Höhlen und Ruined Portals) ==========
        Material.OBSIDIAN, Material.CRYING_OBSIDIAN,

        // ========== GENERIERTE STRUKTUREN ==========

        // --- Festungen (Strongholds) ---
        Material.STONE_BRICKS, Material.MOSSY_STONE_BRICKS,
        Material.CRACKED_STONE_BRICKS, Material.CHISELED_STONE_BRICKS,
        Material.STONE_BRICK_STAIRS, Material.STONE_BRICK_SLAB,
        Material.IRON_BARS,
        Material.END_PORTAL, Material.END_PORTAL_FRAME,
        Material.INFESTED_STONE, Material.INFESTED_COBBLESTONE,
        Material.INFESTED_STONE_BRICKS, Material.INFESTED_MOSSY_STONE_BRICKS,
        Material.INFESTED_CRACKED_STONE_BRICKS, Material.INFESTED_CHISELED_STONE_BRICKS,
        Material.INFESTED_DEEPSLATE,

        // --- Dungeons ---
        Material.COBBLESTONE, Material.MOSSY_COBBLESTONE,
        Material.SPAWNER,
        Material.CHEST,

        // --- Minenschächte ---
        Material.OAK_PLANKS, Material.OAK_FENCE,
        Material.RAIL, Material.POWERED_RAIL,
        Material.TORCH, Material.WALL_TORCH,
        Material.COBWEB,

        // --- Ozeanmonumente ---
        Material.PRISMARINE, Material.PRISMARINE_BRICKS, Material.DARK_PRISMARINE,
        Material.SEA_LANTERN, Material.SPONGE, Material.WET_SPONGE,
        Material.GOLD_BLOCK,

        // --- Wüstentempel ---
        Material.CUT_SANDSTONE, Material.CHISELED_SANDSTONE, Material.SMOOTH_SANDSTONE,
        Material.SANDSTONE_STAIRS, Material.SANDSTONE_SLAB,
        Material.TNT,
        Material.ORANGE_TERRACOTTA,

        // --- Dschungeltempel ---
        Material.COBBLESTONE_STAIRS,
        Material.MOSSY_COBBLESTONE_STAIRS,
        Material.LEVER, Material.REPEATER, Material.TRIPWIRE_HOOK, Material.TRIPWIRE,
        Material.REDSTONE_WIRE, Material.PISTON, Material.STICKY_PISTON,
        Material.DISPENSER,

        // --- Badlands (natürliche Terrakotta-Schichten) ---
        Material.TERRACOTTA,
        Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA,
        Material.YELLOW_TERRACOTTA, Material.BROWN_TERRACOTTA,
        Material.RED_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA,

        // --- Iglu ---
        Material.SNOW_BLOCK,

        // --- Woodland Mansion ---
        // CI (
        // Woodland Mansions generieren mit Dark Oak Planks, Stairs, Fences usw.
        // Da diese aber auch häufig von Spielern genutzt werden und die Mansion
        // ein seltenes Vorkommen ist, lassen wir Dark Oak hier NICHT als natürlich gelten.
        // Nur OAK_PLANKS und OAK_FENCE sind oben (für Mineshafts) als natürlich markiert.
        // CI )

        // --- Ruined Portals ---
        Material.NETHERRACK,
        Material.NETHER_BRICKS,
        Material.BLACKSTONE,
        Material.GILDED_BLACKSTONE,
        Material.POLISHED_BLACKSTONE,
        Material.POLISHED_BLACKSTONE_BRICKS,
        Material.CRACKED_POLISHED_BLACKSTONE_BRICKS,
        Material.CHISELED_POLISHED_BLACKSTONE,

        // --- Schiffswracks ---
        Material.DARK_OAK_LOG,
        // CI (
        // Schiffswracks enthalten auch Spruce/Dark Oak Planks, Trapdoors, Fences etc.
        // Da der StructureCheck diese ohnehin herausfiltert, markieren wir hier nur
        // die Log-Blöcke die mit hoher Wahrscheinlichkeit natürlich sind.
        // CI )

        // --- Ozeanruinen ---
        Material.STONE_BRICK_WALL,

        // --- Pillager Outpost ---
        // (Verwendet Dark Oak, Cobblestone - bereits oben enthalten)

        // --- Ancient City ---
        Material.DEEPSLATE_BRICKS, Material.CRACKED_DEEPSLATE_BRICKS,
        Material.DEEPSLATE_TILES, Material.CRACKED_DEEPSLATE_TILES,
        Material.CHISELED_DEEPSLATE, Material.POLISHED_DEEPSLATE,
        Material.DEEPSLATE_BRICK_STAIRS, Material.DEEPSLATE_TILE_STAIRS,
        Material.DEEPSLATE_BRICK_SLAB, Material.DEEPSLATE_TILE_SLAB,
        Material.DEEPSLATE_BRICK_WALL, Material.DEEPSLATE_TILE_WALL,
        Material.POLISHED_DEEPSLATE_STAIRS, Material.POLISHED_DEEPSLATE_SLAB,
        Material.POLISHED_DEEPSLATE_WALL,
        Material.COBBLED_DEEPSLATE_STAIRS, Material.COBBLED_DEEPSLATE_SLAB,
        Material.COBBLED_DEEPSLATE_WALL,
        Material.SOUL_LANTERN, Material.SOUL_FIRE,
        Material.CANDLE, Material.BLUE_ICE,
        Material.SOUL_SAND, Material.SOUL_SOIL,

        // --- Trail Ruins ---
        Material.MUD_BRICKS, Material.MUD_BRICK_STAIRS,
        Material.MUD_BRICK_SLAB, Material.MUD_BRICK_WALL,
        Material.PACKED_MUD,

        // --- Tuff-Varianten (Trail Ruins & natürliche Generierung) ---
        Material.TUFF_STAIRS, Material.TUFF_SLAB, Material.TUFF_WALL,
        Material.POLISHED_TUFF, Material.POLISHED_TUFF_STAIRS,
        Material.POLISHED_TUFF_SLAB, Material.POLISHED_TUFF_WALL,
        Material.TUFF_BRICKS, Material.TUFF_BRICK_STAIRS,
        Material.TUFF_BRICK_SLAB, Material.TUFF_BRICK_WALL,
        Material.CHISELED_TUFF, Material.CHISELED_TUFF_BRICKS,

        // --- Bienennester ---
        Material.BEE_NEST,

        // --- Feuer (natürlich auf Netherrack bei Ruined Portals) ---
        Material.FIRE, Material.SOUL_FIRE,

        // ========== SONSTIGE NATÜRLICHE BLÖCKE ==========
        // Schildkröteneier (Strände)
        Material.TURTLE_EGG,
        // Nether-Blöcke die in Ruined Portals vorkommen
        Material.NETHER_WART_BLOCK, Material.CRIMSON_NYLIUM, Material.WARPED_NYLIUM,
        // Endportalrahmen
        Material.END_PORTAL_FRAME, Material.END_PORTAL,
        // Erz-Blöcke (Kupfererzader generiert auch als Block)
        Material.COPPER_BLOCK, // Kupfererz generiert manchmal in Blöcken
        // Moos in Lush Caves
        Material.CLAY,
        // Tropfstein
        Material.DRIPSTONE_BLOCK, Material.POINTED_DRIPSTONE,
        // Reinforced Deepslate (Ancient City)
        Material.REINFORCED_DEEPSLATE
    )

    /**
     * Zerfallsprodukt-Mappings: Material → Zerfallsprodukt.
     * Ähnliche Materialien werden gruppiert (z.B. alle Holzarten → gleicher Zerfall).
     *
     * HashMap für O(1) get()-Abfragen.
     */
    val DECAY_PRODUCTS: HashMap<Material, Material> = hashMapOf(
        // === 1. Holzbretter (alle Arten) → Moosiger Bruchstein ===
        Material.SPRUCE_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.BIRCH_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.JUNGLE_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.ACACIA_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.DARK_OAK_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.MANGROVE_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.CHERRY_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.BAMBOO_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.CRIMSON_PLANKS to Material.MOSSY_COBBLESTONE,
        Material.WARPED_PLANKS to Material.MOSSY_COBBLESTONE,
        // OAK_PLANKS ist natürlich (Mineshafts), daher nicht hier

        // === 2. Holztreppen → Bruchsteintreppen (Ausrichtung wird im Processor übertragen) ===
        Material.OAK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.SPRUCE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.BIRCH_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.JUNGLE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.ACACIA_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.DARK_OAK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.MANGROVE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.CHERRY_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.BAMBOO_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.CRIMSON_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.WARPED_STAIRS to Material.COBBLESTONE_STAIRS,

        // === 3. Holzstufen → Bruchsteinstufen (Ausrichtung wird im Processor übertragen) ===
        Material.OAK_SLAB to Material.COBBLESTONE_SLAB,
        Material.SPRUCE_SLAB to Material.COBBLESTONE_SLAB,
        Material.BIRCH_SLAB to Material.COBBLESTONE_SLAB,
        Material.JUNGLE_SLAB to Material.COBBLESTONE_SLAB,
        Material.ACACIA_SLAB to Material.COBBLESTONE_SLAB,
        Material.DARK_OAK_SLAB to Material.COBBLESTONE_SLAB,
        Material.MANGROVE_SLAB to Material.COBBLESTONE_SLAB,
        Material.CHERRY_SLAB to Material.COBBLESTONE_SLAB,
        Material.BAMBOO_SLAB to Material.COBBLESTONE_SLAB,
        Material.CRIMSON_SLAB to Material.COBBLESTONE_SLAB,
        Material.WARPED_SLAB to Material.COBBLESTONE_SLAB,

        // === 4. Holzzäune → Bruchsteinmauern ===
        Material.SPRUCE_FENCE to Material.COBBLESTONE_WALL,
        Material.BIRCH_FENCE to Material.COBBLESTONE_WALL,
        Material.JUNGLE_FENCE to Material.COBBLESTONE_WALL,
        Material.ACACIA_FENCE to Material.COBBLESTONE_WALL,
        Material.DARK_OAK_FENCE to Material.COBBLESTONE_WALL,
        Material.MANGROVE_FENCE to Material.COBBLESTONE_WALL,
        Material.CHERRY_FENCE to Material.COBBLESTONE_WALL,
        Material.BAMBOO_FENCE to Material.COBBLESTONE_WALL,
        Material.CRIMSON_FENCE to Material.COBBLESTONE_WALL,
        Material.WARPED_FENCE to Material.COBBLESTONE_WALL,

        // === 5. Geschälte Stämme → Normale Stämme ===
        Material.STRIPPED_OAK_LOG to Material.OAK_LOG,
        Material.STRIPPED_SPRUCE_LOG to Material.SPRUCE_LOG,
        Material.STRIPPED_BIRCH_LOG to Material.BIRCH_LOG,
        Material.STRIPPED_JUNGLE_LOG to Material.JUNGLE_LOG,
        Material.STRIPPED_ACACIA_LOG to Material.ACACIA_LOG,
        Material.STRIPPED_DARK_OAK_LOG to Material.DARK_OAK_LOG,
        Material.STRIPPED_MANGROVE_LOG to Material.MANGROVE_LOG,
        Material.STRIPPED_CHERRY_LOG to Material.CHERRY_LOG,
        Material.STRIPPED_BAMBOO_BLOCK to Material.BAMBOO_BLOCK,
        Material.STRIPPED_OAK_WOOD to Material.OAK_WOOD,
        Material.STRIPPED_SPRUCE_WOOD to Material.SPRUCE_WOOD,
        Material.STRIPPED_BIRCH_WOOD to Material.BIRCH_WOOD,
        Material.STRIPPED_JUNGLE_WOOD to Material.JUNGLE_WOOD,
        Material.STRIPPED_ACACIA_WOOD to Material.ACACIA_WOOD,
        Material.STRIPPED_DARK_OAK_WOOD to Material.DARK_OAK_WOOD,
        Material.STRIPPED_MANGROVE_WOOD to Material.MANGROVE_WOOD,
        Material.STRIPPED_CHERRY_WOOD to Material.CHERRY_WOOD,

        // === 6. Ziegel → Bruchstein ===
        Material.BRICKS to Material.COBBLESTONE,
        Material.BRICK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.BRICK_SLAB to Material.COBBLESTONE_SLAB,
        Material.BRICK_WALL to Material.COBBLESTONE_WALL,

        // === 7. Glas → Luft (zerbrochen) ===
        Material.GLASS to Material.AIR,
        Material.GLASS_PANE to Material.AIR,
        Material.WHITE_STAINED_GLASS to Material.AIR,
        Material.ORANGE_STAINED_GLASS to Material.AIR,
        Material.MAGENTA_STAINED_GLASS to Material.AIR,
        Material.LIGHT_BLUE_STAINED_GLASS to Material.AIR,
        Material.YELLOW_STAINED_GLASS to Material.AIR,
        Material.LIME_STAINED_GLASS to Material.AIR,
        Material.PINK_STAINED_GLASS to Material.AIR,
        Material.GRAY_STAINED_GLASS to Material.AIR,
        Material.LIGHT_GRAY_STAINED_GLASS to Material.AIR,
        Material.CYAN_STAINED_GLASS to Material.AIR,
        Material.PURPLE_STAINED_GLASS to Material.AIR,
        Material.BLUE_STAINED_GLASS to Material.AIR,
        Material.BROWN_STAINED_GLASS to Material.AIR,
        Material.GREEN_STAINED_GLASS to Material.AIR,
        Material.RED_STAINED_GLASS to Material.AIR,
        Material.BLACK_STAINED_GLASS to Material.AIR,
        Material.WHITE_STAINED_GLASS_PANE to Material.AIR,
        Material.ORANGE_STAINED_GLASS_PANE to Material.AIR,
        Material.MAGENTA_STAINED_GLASS_PANE to Material.AIR,
        Material.LIGHT_BLUE_STAINED_GLASS_PANE to Material.AIR,
        Material.YELLOW_STAINED_GLASS_PANE to Material.AIR,
        Material.LIME_STAINED_GLASS_PANE to Material.AIR,
        Material.PINK_STAINED_GLASS_PANE to Material.AIR,
        Material.GRAY_STAINED_GLASS_PANE to Material.AIR,
        Material.LIGHT_GRAY_STAINED_GLASS_PANE to Material.AIR,
        Material.CYAN_STAINED_GLASS_PANE to Material.AIR,
        Material.PURPLE_STAINED_GLASS_PANE to Material.AIR,
        Material.BLUE_STAINED_GLASS_PANE to Material.AIR,
        Material.BROWN_STAINED_GLASS_PANE to Material.AIR,
        Material.GREEN_STAINED_GLASS_PANE to Material.AIR,
        Material.RED_STAINED_GLASS_PANE to Material.AIR,
        Material.BLACK_STAINED_GLASS_PANE to Material.AIR,
        Material.TINTED_GLASS to Material.AIR,

        // === 8. Wolle → Spinnennetze ===
        Material.WHITE_WOOL to Material.COBWEB,
        Material.ORANGE_WOOL to Material.COBWEB,
        Material.MAGENTA_WOOL to Material.COBWEB,
        Material.LIGHT_BLUE_WOOL to Material.COBWEB,
        Material.YELLOW_WOOL to Material.COBWEB,
        Material.LIME_WOOL to Material.COBWEB,
        Material.PINK_WOOL to Material.COBWEB,
        Material.GRAY_WOOL to Material.COBWEB,
        Material.LIGHT_GRAY_WOOL to Material.COBWEB,
        Material.CYAN_WOOL to Material.COBWEB,
        Material.PURPLE_WOOL to Material.COBWEB,
        Material.BLUE_WOOL to Material.COBWEB,
        Material.BROWN_WOOL to Material.COBWEB,
        Material.GREEN_WOOL to Material.COBWEB,
        Material.RED_WOOL to Material.COBWEB,
        Material.BLACK_WOOL to Material.COBWEB,

        // === 9. Beton → Betonpulver (Verwitterung) ===
        Material.WHITE_CONCRETE to Material.WHITE_CONCRETE_POWDER,
        Material.ORANGE_CONCRETE to Material.ORANGE_CONCRETE_POWDER,
        Material.MAGENTA_CONCRETE to Material.MAGENTA_CONCRETE_POWDER,
        Material.LIGHT_BLUE_CONCRETE to Material.LIGHT_BLUE_CONCRETE_POWDER,
        Material.YELLOW_CONCRETE to Material.YELLOW_CONCRETE_POWDER,
        Material.LIME_CONCRETE to Material.LIME_CONCRETE_POWDER,
        Material.PINK_CONCRETE to Material.PINK_CONCRETE_POWDER,
        Material.GRAY_CONCRETE to Material.GRAY_CONCRETE_POWDER,
        Material.LIGHT_GRAY_CONCRETE to Material.LIGHT_GRAY_CONCRETE_POWDER,
        Material.CYAN_CONCRETE to Material.CYAN_CONCRETE_POWDER,
        Material.PURPLE_CONCRETE to Material.PURPLE_CONCRETE_POWDER,
        Material.BLUE_CONCRETE to Material.BLUE_CONCRETE_POWDER,
        Material.BROWN_CONCRETE to Material.BROWN_CONCRETE_POWDER,
        Material.GREEN_CONCRETE to Material.GREEN_CONCRETE_POWDER,
        Material.RED_CONCRETE to Material.RED_CONCRETE_POWDER,
        Material.BLACK_CONCRETE to Material.BLACK_CONCRETE_POWDER,

        // === 10. Betonpulver → Sand ===
        Material.WHITE_CONCRETE_POWDER to Material.SAND,
        Material.ORANGE_CONCRETE_POWDER to Material.SAND,
        Material.MAGENTA_CONCRETE_POWDER to Material.SAND,
        Material.LIGHT_BLUE_CONCRETE_POWDER to Material.SAND,
        Material.YELLOW_CONCRETE_POWDER to Material.SAND,
        Material.LIME_CONCRETE_POWDER to Material.SAND,
        Material.PINK_CONCRETE_POWDER to Material.SAND,
        Material.GRAY_CONCRETE_POWDER to Material.SAND,
        Material.LIGHT_GRAY_CONCRETE_POWDER to Material.SAND,
        Material.CYAN_CONCRETE_POWDER to Material.SAND,
        Material.PURPLE_CONCRETE_POWDER to Material.SAND,
        Material.BLUE_CONCRETE_POWDER to Material.SAND,
        Material.BROWN_CONCRETE_POWDER to Material.SAND,
        Material.GREEN_CONCRETE_POWDER to Material.SAND,
        Material.RED_CONCRETE_POWDER to Material.SAND,
        Material.BLACK_CONCRETE_POWDER to Material.SAND,

        // === 11. Glasierte Terrakotta → Terrakotta ===
        Material.WHITE_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.ORANGE_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.MAGENTA_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.LIGHT_BLUE_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.YELLOW_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.LIME_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.PINK_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.GRAY_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.LIGHT_GRAY_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.CYAN_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.PURPLE_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.BLUE_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.BROWN_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.GREEN_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.RED_GLAZED_TERRACOTTA to Material.TERRACOTTA,
        Material.BLACK_GLAZED_TERRACOTTA to Material.TERRACOTTA,

        // === 12. Farbige Terrakotta (nicht in Badlands natürlich) → Terrakotta ===
        Material.MAGENTA_TERRACOTTA to Material.TERRACOTTA,
        Material.LIGHT_BLUE_TERRACOTTA to Material.TERRACOTTA,
        Material.LIME_TERRACOTTA to Material.TERRACOTTA,
        Material.PINK_TERRACOTTA to Material.TERRACOTTA,
        Material.GRAY_TERRACOTTA to Material.TERRACOTTA,
        Material.CYAN_TERRACOTTA to Material.TERRACOTTA,
        Material.PURPLE_TERRACOTTA to Material.TERRACOTTA,
        Material.BLUE_TERRACOTTA to Material.TERRACOTTA,
        Material.GREEN_TERRACOTTA to Material.TERRACOTTA,
        Material.BLACK_TERRACOTTA to Material.TERRACOTTA,

        // === 13. Polierte Steinvarianten → Rohe Varianten ===
        Material.POLISHED_GRANITE to Material.GRANITE,
        Material.POLISHED_DIORITE to Material.DIORITE,
        Material.POLISHED_ANDESITE to Material.ANDESITE,

        // === 14. Glatter Stein → Bruchstein ===
        Material.SMOOTH_STONE to Material.COBBLESTONE,
        Material.SMOOTH_STONE_SLAB to Material.COBBLESTONE_SLAB,

        // === 15. Quarz → Kalzit ===
        Material.QUARTZ_BLOCK to Material.CALCITE,
        Material.QUARTZ_BRICKS to Material.CALCITE,
        Material.QUARTZ_PILLAR to Material.CALCITE,
        Material.CHISELED_QUARTZ_BLOCK to Material.CALCITE,
        Material.SMOOTH_QUARTZ to Material.CALCITE,
        Material.QUARTZ_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.QUARTZ_SLAB to Material.COBBLESTONE_SLAB,
        Material.SMOOTH_QUARTZ_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.SMOOTH_QUARTZ_SLAB to Material.COBBLESTONE_SLAB,

        // === 16. Purpur → Endstein ===
        Material.PURPUR_BLOCK to Material.END_STONE,
        Material.PURPUR_PILLAR to Material.END_STONE,
        Material.PURPUR_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.PURPUR_SLAB to Material.COBBLESTONE_SLAB,

        // === 17. Kupferblöcke → Oxidiertes Kupfer ===
        Material.EXPOSED_COPPER to Material.OXIDIZED_COPPER,
        Material.WEATHERED_COPPER to Material.OXIDIZED_COPPER,
        Material.CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.EXPOSED_CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.WEATHERED_CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.EXPOSED_CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.WEATHERED_CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.EXPOSED_CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.WEATHERED_CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.WAXED_COPPER_BLOCK to Material.OXIDIZED_COPPER,
        Material.WAXED_EXPOSED_COPPER to Material.OXIDIZED_COPPER,
        Material.WAXED_WEATHERED_COPPER to Material.OXIDIZED_COPPER,
        Material.WAXED_CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.WAXED_EXPOSED_CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.WAXED_WEATHERED_CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.WAXED_OXIDIZED_COPPER to Material.OXIDIZED_COPPER,
        Material.WAXED_OXIDIZED_CUT_COPPER to Material.OXIDIZED_CUT_COPPER,
        Material.WAXED_CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.WAXED_EXPOSED_CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.WAXED_WEATHERED_CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.WAXED_OXIDIZED_CUT_COPPER_STAIRS to Material.OXIDIZED_CUT_COPPER_STAIRS,
        Material.WAXED_CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.WAXED_EXPOSED_CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.WAXED_WEATHERED_CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,
        Material.WAXED_OXIDIZED_CUT_COPPER_SLAB to Material.OXIDIZED_CUT_COPPER_SLAB,

        // === 18. Eisenblock → Roheisenblock (Rost) ===
        Material.IRON_BLOCK to Material.RAW_IRON_BLOCK,

        // === 19. Diamant/Smaragd/Lapis-Block → Stein (geplündert) ===
        Material.DIAMOND_BLOCK to Material.STONE,
        Material.EMERALD_BLOCK to Material.STONE,
        Material.LAPIS_BLOCK to Material.STONE,

        // === 20. Bücherregal → Eichenbretter ===
        Material.BOOKSHELF to Material.OAK_PLANKS,
        Material.CHISELED_BOOKSHELF to Material.OAK_PLANKS,

        // === 21. Werkbank → Eichenbretter ===
        Material.CRAFTING_TABLE to Material.OAK_PLANKS,

        // === 22. Ofen → Bruchstein ===
        Material.FURNACE to Material.COBBLESTONE,
        Material.BLAST_FURNACE to Material.COBBLESTONE,
        Material.SMOKER to Material.COBBLESTONE,

        // === 23. Amboss → Beschädigter Amboss ===
        Material.ANVIL to Material.DAMAGED_ANVIL,
        Material.CHIPPED_ANVIL to Material.DAMAGED_ANVIL,

        // === 24. Polierte Stein-Treppen → Bruchsteintreppen ===
        Material.STONE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.POLISHED_GRANITE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.POLISHED_DIORITE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.POLISHED_ANDESITE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.GRANITE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.DIORITE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.ANDESITE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.RED_SANDSTONE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.SMOOTH_SANDSTONE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.SMOOTH_RED_SANDSTONE_STAIRS to Material.COBBLESTONE_STAIRS,

        // === 25. Polierte Stein-Stufen → Bruchsteinstufen ===
        Material.STONE_SLAB to Material.COBBLESTONE_SLAB,
        Material.POLISHED_GRANITE_SLAB to Material.COBBLESTONE_SLAB,
        Material.POLISHED_DIORITE_SLAB to Material.COBBLESTONE_SLAB,
        Material.POLISHED_ANDESITE_SLAB to Material.COBBLESTONE_SLAB,
        Material.GRANITE_SLAB to Material.COBBLESTONE_SLAB,
        Material.DIORITE_SLAB to Material.COBBLESTONE_SLAB,
        Material.ANDESITE_SLAB to Material.COBBLESTONE_SLAB,
        Material.RED_SANDSTONE_SLAB to Material.COBBLESTONE_SLAB,
        Material.SMOOTH_SANDSTONE_SLAB to Material.COBBLESTONE_SLAB,
        Material.SMOOTH_RED_SANDSTONE_SLAB to Material.COBBLESTONE_SLAB,

        // === 26. Steinmauern → Bruchsteinmauer ===
        Material.GRANITE_WALL to Material.COBBLESTONE_WALL,
        Material.DIORITE_WALL to Material.COBBLESTONE_WALL,
        Material.ANDESITE_WALL to Material.COBBLESTONE_WALL,
        Material.SANDSTONE_WALL to Material.COBBLESTONE_WALL,
        Material.RED_SANDSTONE_WALL to Material.COBBLESTONE_WALL,
        Material.BRICK_WALL to Material.COBBLESTONE_WALL,

        // === 27. Türen → Luft (komplett, beide Hälften) ===
        Material.OAK_DOOR to Material.AIR,
        Material.SPRUCE_DOOR to Material.AIR,
        Material.BIRCH_DOOR to Material.AIR,
        Material.JUNGLE_DOOR to Material.AIR,
        Material.ACACIA_DOOR to Material.AIR,
        Material.DARK_OAK_DOOR to Material.AIR,
        Material.MANGROVE_DOOR to Material.AIR,
        Material.CHERRY_DOOR to Material.AIR,
        Material.BAMBOO_DOOR to Material.AIR,
        Material.CRIMSON_DOOR to Material.AIR,
        Material.WARPED_DOOR to Material.AIR,
        Material.IRON_DOOR to Material.AIR,
        Material.COPPER_DOOR to Material.AIR,
        Material.EXPOSED_COPPER_DOOR to Material.AIR,
        Material.WEATHERED_COPPER_DOOR to Material.AIR,
        Material.OXIDIZED_COPPER_DOOR to Material.AIR,

        // === 28. Falltüren → Luft ===
        Material.OAK_TRAPDOOR to Material.AIR,
        Material.SPRUCE_TRAPDOOR to Material.AIR,
        Material.BIRCH_TRAPDOOR to Material.AIR,
        Material.JUNGLE_TRAPDOOR to Material.AIR,
        Material.ACACIA_TRAPDOOR to Material.AIR,
        Material.DARK_OAK_TRAPDOOR to Material.AIR,
        Material.MANGROVE_TRAPDOOR to Material.AIR,
        Material.CHERRY_TRAPDOOR to Material.AIR,
        Material.BAMBOO_TRAPDOOR to Material.AIR,
        Material.CRIMSON_TRAPDOOR to Material.AIR,
        Material.WARPED_TRAPDOOR to Material.AIR,
        Material.IRON_TRAPDOOR to Material.AIR,
        Material.COPPER_TRAPDOOR to Material.AIR,
        Material.EXPOSED_COPPER_TRAPDOOR to Material.AIR,
        Material.WEATHERED_COPPER_TRAPDOOR to Material.AIR,
        Material.OXIDIZED_COPPER_TRAPDOOR to Material.AIR,

        // === 29. Zauntore → Luft ===
        Material.OAK_FENCE_GATE to Material.AIR,
        Material.SPRUCE_FENCE_GATE to Material.AIR,
        Material.BIRCH_FENCE_GATE to Material.AIR,
        Material.JUNGLE_FENCE_GATE to Material.AIR,
        Material.ACACIA_FENCE_GATE to Material.AIR,
        Material.DARK_OAK_FENCE_GATE to Material.AIR,
        Material.MANGROVE_FENCE_GATE to Material.AIR,
        Material.CHERRY_FENCE_GATE to Material.AIR,
        Material.BAMBOO_FENCE_GATE to Material.AIR,
        Material.CRIMSON_FENCE_GATE to Material.AIR,
        Material.WARPED_FENCE_GATE to Material.AIR,

        // === 30. Beleuchtung → Luft (erloschen/gefallen) ===
        Material.LANTERN to Material.AIR,
        Material.SOUL_TORCH to Material.AIR,
        Material.SOUL_WALL_TORCH to Material.AIR,
        Material.WHITE_CANDLE to Material.AIR,
        Material.ORANGE_CANDLE to Material.AIR,
        Material.MAGENTA_CANDLE to Material.AIR,
        Material.LIGHT_BLUE_CANDLE to Material.AIR,
        Material.YELLOW_CANDLE to Material.AIR,
        Material.LIME_CANDLE to Material.AIR,
        Material.PINK_CANDLE to Material.AIR,
        Material.GRAY_CANDLE to Material.AIR,
        Material.LIGHT_GRAY_CANDLE to Material.AIR,
        Material.CYAN_CANDLE to Material.AIR,
        Material.PURPLE_CANDLE to Material.AIR,
        Material.BLUE_CANDLE to Material.AIR,
        Material.BROWN_CANDLE to Material.AIR,
        Material.GREEN_CANDLE to Material.AIR,
        Material.RED_CANDLE to Material.AIR,
        Material.BLACK_CANDLE to Material.AIR,
        Material.REDSTONE_LAMP to Material.AIR,
        Material.GLOWSTONE to Material.AIR,
        Material.SHROOMLIGHT to Material.AIR,
        Material.END_ROD to Material.AIR,

        // === 31. Schilder → Luft ===
        Material.OAK_SIGN to Material.AIR,
        Material.SPRUCE_SIGN to Material.AIR,
        Material.BIRCH_SIGN to Material.AIR,
        Material.JUNGLE_SIGN to Material.AIR,
        Material.ACACIA_SIGN to Material.AIR,
        Material.DARK_OAK_SIGN to Material.AIR,
        Material.MANGROVE_SIGN to Material.AIR,
        Material.CHERRY_SIGN to Material.AIR,
        Material.BAMBOO_SIGN to Material.AIR,
        Material.CRIMSON_SIGN to Material.AIR,
        Material.WARPED_SIGN to Material.AIR,
        Material.OAK_WALL_SIGN to Material.AIR,
        Material.SPRUCE_WALL_SIGN to Material.AIR,
        Material.BIRCH_WALL_SIGN to Material.AIR,
        Material.JUNGLE_WALL_SIGN to Material.AIR,
        Material.ACACIA_WALL_SIGN to Material.AIR,
        Material.DARK_OAK_WALL_SIGN to Material.AIR,
        Material.MANGROVE_WALL_SIGN to Material.AIR,
        Material.CHERRY_WALL_SIGN to Material.AIR,
        Material.BAMBOO_WALL_SIGN to Material.AIR,
        Material.CRIMSON_WALL_SIGN to Material.AIR,
        Material.WARPED_WALL_SIGN to Material.AIR,
        Material.OAK_HANGING_SIGN to Material.AIR,
        Material.SPRUCE_HANGING_SIGN to Material.AIR,
        Material.BIRCH_HANGING_SIGN to Material.AIR,
        Material.JUNGLE_HANGING_SIGN to Material.AIR,
        Material.ACACIA_HANGING_SIGN to Material.AIR,
        Material.DARK_OAK_HANGING_SIGN to Material.AIR,
        Material.MANGROVE_HANGING_SIGN to Material.AIR,
        Material.CHERRY_HANGING_SIGN to Material.AIR,
        Material.BAMBOO_HANGING_SIGN to Material.AIR,
        Material.CRIMSON_HANGING_SIGN to Material.AIR,
        Material.WARPED_HANGING_SIGN to Material.AIR,
        Material.OAK_WALL_HANGING_SIGN to Material.AIR,
        Material.SPRUCE_WALL_HANGING_SIGN to Material.AIR,
        Material.BIRCH_WALL_HANGING_SIGN to Material.AIR,
        Material.JUNGLE_WALL_HANGING_SIGN to Material.AIR,
        Material.ACACIA_WALL_HANGING_SIGN to Material.AIR,
        Material.DARK_OAK_WALL_HANGING_SIGN to Material.AIR,
        Material.MANGROVE_WALL_HANGING_SIGN to Material.AIR,
        Material.CHERRY_WALL_HANGING_SIGN to Material.AIR,
        Material.BAMBOO_WALL_HANGING_SIGN to Material.AIR,
        Material.CRIMSON_WALL_HANGING_SIGN to Material.AIR,
        Material.WARPED_WALL_HANGING_SIGN to Material.AIR,

        // === 32. Banner → Luft ===
        Material.WHITE_BANNER to Material.AIR,
        Material.ORANGE_BANNER to Material.AIR,
        Material.MAGENTA_BANNER to Material.AIR,
        Material.LIGHT_BLUE_BANNER to Material.AIR,
        Material.YELLOW_BANNER to Material.AIR,
        Material.LIME_BANNER to Material.AIR,
        Material.PINK_BANNER to Material.AIR,
        Material.GRAY_BANNER to Material.AIR,
        Material.LIGHT_GRAY_BANNER to Material.AIR,
        Material.CYAN_BANNER to Material.AIR,
        Material.PURPLE_BANNER to Material.AIR,
        Material.BLUE_BANNER to Material.AIR,
        Material.BROWN_BANNER to Material.AIR,
        Material.GREEN_BANNER to Material.AIR,
        Material.RED_BANNER to Material.AIR,
        Material.BLACK_BANNER to Material.AIR,
        Material.WHITE_WALL_BANNER to Material.AIR,
        Material.ORANGE_WALL_BANNER to Material.AIR,
        Material.MAGENTA_WALL_BANNER to Material.AIR,
        Material.LIGHT_BLUE_WALL_BANNER to Material.AIR,
        Material.YELLOW_WALL_BANNER to Material.AIR,
        Material.LIME_WALL_BANNER to Material.AIR,
        Material.PINK_WALL_BANNER to Material.AIR,
        Material.GRAY_WALL_BANNER to Material.AIR,
        Material.LIGHT_GRAY_WALL_BANNER to Material.AIR,
        Material.CYAN_WALL_BANNER to Material.AIR,
        Material.PURPLE_WALL_BANNER to Material.AIR,
        Material.BLUE_WALL_BANNER to Material.AIR,
        Material.BROWN_WALL_BANNER to Material.AIR,
        Material.GREEN_WALL_BANNER to Material.AIR,
        Material.RED_WALL_BANNER to Material.AIR,
        Material.BLACK_WALL_BANNER to Material.AIR,

        // === 33. Betten → Luft (beide Hälften) ===
        Material.WHITE_BED to Material.AIR,
        Material.ORANGE_BED to Material.AIR,
        Material.MAGENTA_BED to Material.AIR,
        Material.LIGHT_BLUE_BED to Material.AIR,
        Material.YELLOW_BED to Material.AIR,
        Material.LIME_BED to Material.AIR,
        Material.PINK_BED to Material.AIR,
        Material.GRAY_BED to Material.AIR,
        Material.LIGHT_GRAY_BED to Material.AIR,
        Material.CYAN_BED to Material.AIR,
        Material.PURPLE_BED to Material.AIR,
        Material.BLUE_BED to Material.AIR,
        Material.BROWN_BED to Material.AIR,
        Material.GREEN_BED to Material.AIR,
        Material.RED_BED to Material.AIR,
        Material.BLACK_BED to Material.AIR,

        // === 34. Teppiche → Luft ===
        Material.WHITE_CARPET to Material.AIR,
        Material.ORANGE_CARPET to Material.AIR,
        Material.MAGENTA_CARPET to Material.AIR,
        Material.LIGHT_BLUE_CARPET to Material.AIR,
        Material.YELLOW_CARPET to Material.AIR,
        Material.LIME_CARPET to Material.AIR,
        Material.PINK_CARPET to Material.AIR,
        Material.GRAY_CARPET to Material.AIR,
        Material.LIGHT_GRAY_CARPET to Material.AIR,
        Material.CYAN_CARPET to Material.AIR,
        Material.PURPLE_CARPET to Material.AIR,
        Material.BLUE_CARPET to Material.AIR,
        Material.BROWN_CARPET to Material.AIR,
        Material.GREEN_CARPET to Material.AIR,
        Material.RED_CARPET to Material.AIR,
        Material.BLACK_CARPET to Material.AIR,

        // === 35. Leitern → Luft ===
        Material.LADDER to Material.AIR,

        // === 36. Schienen (nicht-natürliche) → Luft ===
        Material.DETECTOR_RAIL to Material.AIR,
        Material.ACTIVATOR_RAIL to Material.AIR,

        // === 37. Ketten → Luft ===
        // CI (
        // Material.CHAIN existiert nicht in allen PaperMC Versionen.
        // Stattdessen versuchen wir den Block dynamisch zu finden.
        // Falls CHAIN nicht vorhanden ist, wird er einfach ignoriert.
        // CI )

        // === 38. Redstone-Mechanik → Luft (außer natürlich vorkommende) ===
        Material.COMPARATOR to Material.AIR,
        Material.OBSERVER to Material.AIR,
        Material.DROPPER to Material.AIR,
        Material.HOPPER to Material.AIR,
        Material.DAYLIGHT_DETECTOR to Material.AIR,
        Material.TARGET to Material.AIR,
        Material.PISTON_HEAD to Material.AIR,
        Material.MOVING_PISTON to Material.AIR,

        // === 39. Knöpfe → Luft ===
        Material.OAK_BUTTON to Material.AIR,
        Material.SPRUCE_BUTTON to Material.AIR,
        Material.BIRCH_BUTTON to Material.AIR,
        Material.JUNGLE_BUTTON to Material.AIR,
        Material.ACACIA_BUTTON to Material.AIR,
        Material.DARK_OAK_BUTTON to Material.AIR,
        Material.MANGROVE_BUTTON to Material.AIR,
        Material.CHERRY_BUTTON to Material.AIR,
        Material.BAMBOO_BUTTON to Material.AIR,
        Material.CRIMSON_BUTTON to Material.AIR,
        Material.WARPED_BUTTON to Material.AIR,
        Material.STONE_BUTTON to Material.AIR,
        Material.POLISHED_BLACKSTONE_BUTTON to Material.AIR,

        // === 40. Druckplatten → Luft ===
        Material.OAK_PRESSURE_PLATE to Material.AIR,
        Material.SPRUCE_PRESSURE_PLATE to Material.AIR,
        Material.BIRCH_PRESSURE_PLATE to Material.AIR,
        Material.JUNGLE_PRESSURE_PLATE to Material.AIR,
        Material.ACACIA_PRESSURE_PLATE to Material.AIR,
        Material.DARK_OAK_PRESSURE_PLATE to Material.AIR,
        Material.MANGROVE_PRESSURE_PLATE to Material.AIR,
        Material.CHERRY_PRESSURE_PLATE to Material.AIR,
        Material.BAMBOO_PRESSURE_PLATE to Material.AIR,
        Material.CRIMSON_PRESSURE_PLATE to Material.AIR,
        Material.WARPED_PRESSURE_PLATE to Material.AIR,
        Material.STONE_PRESSURE_PLATE to Material.AIR,
        Material.POLISHED_BLACKSTONE_PRESSURE_PLATE to Material.AIR,
        Material.LIGHT_WEIGHTED_PRESSURE_PLATE to Material.AIR,
        Material.HEAVY_WEIGHTED_PRESSURE_PLATE to Material.AIR,

        // === 41. Verzauberungstisch → Obsidian ===
        Material.ENCHANTING_TABLE to Material.OBSIDIAN,

        // === 42. Braustand → Luft ===
        Material.BREWING_STAND to Material.AIR,

        // === 43. Kessel → Bruchstein ===
        Material.CAULDRON to Material.COBBLESTONE,
        Material.WATER_CAULDRON to Material.MOSSY_COBBLESTONE,
        Material.LAVA_CAULDRON to Material.COBBLESTONE,
        Material.POWDER_SNOW_CAULDRON to Material.COBBLESTONE,

        // === 44. Notenblock/Jukebox → Eichenbretter ===
        Material.NOTE_BLOCK to Material.OAK_PLANKS,
        Material.JUKEBOX to Material.OAK_PLANKS,

        // === 45. Gerüst → Luft ===
        Material.SCAFFOLDING to Material.AIR,

        // === 46. Blumentöpfe → Luft ===
        Material.FLOWER_POT to Material.AIR,
        Material.POTTED_OAK_SAPLING to Material.AIR,
        Material.POTTED_SPRUCE_SAPLING to Material.AIR,
        Material.POTTED_BIRCH_SAPLING to Material.AIR,
        Material.POTTED_JUNGLE_SAPLING to Material.AIR,
        Material.POTTED_ACACIA_SAPLING to Material.AIR,
        Material.POTTED_DARK_OAK_SAPLING to Material.AIR,
        Material.POTTED_CHERRY_SAPLING to Material.AIR,
        Material.POTTED_MANGROVE_PROPAGULE to Material.AIR,
        Material.POTTED_FERN to Material.AIR,
        Material.POTTED_DANDELION to Material.AIR,
        Material.POTTED_POPPY to Material.AIR,
        Material.POTTED_BLUE_ORCHID to Material.AIR,
        Material.POTTED_ALLIUM to Material.AIR,
        Material.POTTED_AZURE_BLUET to Material.AIR,
        Material.POTTED_RED_TULIP to Material.AIR,
        Material.POTTED_ORANGE_TULIP to Material.AIR,
        Material.POTTED_WHITE_TULIP to Material.AIR,
        Material.POTTED_PINK_TULIP to Material.AIR,
        Material.POTTED_OXEYE_DAISY to Material.AIR,
        Material.POTTED_CORNFLOWER to Material.AIR,
        Material.POTTED_LILY_OF_THE_VALLEY to Material.AIR,
        Material.POTTED_WITHER_ROSE to Material.AIR,
        Material.POTTED_RED_MUSHROOM to Material.AIR,
        Material.POTTED_BROWN_MUSHROOM to Material.AIR,
        Material.POTTED_DEAD_BUSH to Material.AIR,
        Material.POTTED_CACTUS to Material.AIR,
        Material.POTTED_BAMBOO to Material.AIR,
        Material.POTTED_AZALEA_BUSH to Material.AIR,
        Material.POTTED_FLOWERING_AZALEA_BUSH to Material.AIR,
        Material.POTTED_CRIMSON_FUNGUS to Material.AIR,
        Material.POTTED_WARPED_FUNGUS to Material.AIR,
        Material.POTTED_CRIMSON_ROOTS to Material.AIR,
        Material.POTTED_WARPED_ROOTS to Material.AIR,
        Material.POTTED_TORCHFLOWER to Material.AIR,

        // === 47. Netherziegel (Spielerplatziert) → Netherrack ===
        Material.RED_NETHER_BRICKS to Material.NETHERRACK,
        Material.NETHER_BRICK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.NETHER_BRICK_SLAB to Material.COBBLESTONE_SLAB,
        Material.NETHER_BRICK_WALL to Material.COBBLESTONE_WALL,
        Material.NETHER_BRICK_FENCE to Material.COBBLESTONE_WALL,
        Material.RED_NETHER_BRICK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.RED_NETHER_BRICK_SLAB to Material.COBBLESTONE_SLAB,
        Material.RED_NETHER_BRICK_WALL to Material.COBBLESTONE_WALL,

        // === 48. End-Steinziegel → Endstein ===
        Material.END_STONE_BRICKS to Material.END_STONE,
        Material.END_STONE_BRICK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.END_STONE_BRICK_SLAB to Material.COBBLESTONE_SLAB,
        Material.END_STONE_BRICK_WALL to Material.COBBLESTONE_WALL,

        // === 49. Prismarin-Varianten (Spielerplatziert, nicht in Monument) ===
        Material.PRISMARINE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.PRISMARINE_SLAB to Material.COBBLESTONE_SLAB,
        Material.PRISMARINE_WALL to Material.COBBLESTONE_WALL,
        Material.PRISMARINE_BRICK_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.PRISMARINE_BRICK_SLAB to Material.COBBLESTONE_SLAB,
        Material.DARK_PRISMARINE_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.DARK_PRISMARINE_SLAB to Material.COBBLESTONE_SLAB,

        // === 50. Sonstige Spielerblöcke ===
        Material.NETHERITE_BLOCK to Material.STONE,
        Material.LODESTONE to Material.STONE,
        Material.RESPAWN_ANCHOR to Material.OBSIDIAN,
        Material.BEACON to Material.GLASS,
        Material.CONDUIT to Material.AIR,
        Material.SHULKER_BOX to Material.AIR,
        Material.ENDER_CHEST to Material.OBSIDIAN,
        Material.TRAPPED_CHEST to Material.AIR,
        Material.BARREL to Material.OAK_PLANKS,
        Material.LECTERN to Material.OAK_PLANKS,
        Material.COMPOSTER to Material.OAK_PLANKS,
        Material.SMITHING_TABLE to Material.OAK_PLANKS,
        Material.FLETCHING_TABLE to Material.OAK_PLANKS,
        Material.CARTOGRAPHY_TABLE to Material.OAK_PLANKS,
        Material.LOOM to Material.OAK_PLANKS,
        Material.STONECUTTER to Material.COBBLESTONE,
        Material.GRINDSTONE to Material.COBBLESTONE,
        Material.BEEHIVE to Material.OAK_PLANKS,
        Material.BAMBOO_BLOCK to Material.BAMBOO,
        Material.BAMBOO_MOSAIC to Material.MOSSY_COBBLESTONE,
        Material.BAMBOO_MOSAIC_STAIRS to Material.COBBLESTONE_STAIRS,
        Material.BAMBOO_MOSAIC_SLAB to Material.COBBLESTONE_SLAB
    )

    /**
     * Prüft ob ein Material als nicht-natürlich in der Oberwelt gilt.
     * O(1) Lookup durch HashSet.
     */
    fun isNonNatural(material: Material): Boolean {
        if (material.isAir) return false
        if (!material.isBlock) return false
        return material !in NATURAL_OVERWORLD_BLOCKS
    }

    init {
        // CI (
        // CHAIN existiert möglicherweise nicht in allen API-Versionen.
        // Dynamisches Hinzufügen um Compile-Fehler zu vermeiden.
        // CI )
        try {
            val chain = Material.valueOf("CHAIN")
            DECAY_PRODUCTS[chain] = Material.AIR
        } catch (_: IllegalArgumentException) {
            // CHAIN existiert nicht in dieser Version - ignorieren
        }
    }
}
