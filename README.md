# CivElevator
Independent plugin for Civ's private/personal servers for 1.20.4. Designed to be absolutely lightweight and minimal feature but useful at the same time.

All credits goes to the civ developers who contributed to [SimpleAdminHacks](https://github.com/CivMC/SimpleAdminHacks) repo, where GoldElevator resided in!

I (KingColton1; IGN CapColt and CapianColton) modified some portions of codes to be decoupled from the mainline plugins and added config.yml file to customize elevator block and sound for your preference.

I plan to rewrite CivElevator (will be named as BlockElevator in a separate repository) for ReIndev and Better than Adventure, the mods that is complete rewrite of Minecraft Beta 1.7.3. I recommend both to try them out yourself!

# HOW TO USE CONFIG.YML
There are two settings you can use to customize; ELEVATOR MATERIAL and ELEVATOR SOUND. Each of them require Data Value ID (eg GOLD_BLOCK), you must look up on https://minecraft.wiki to find Data Value ID of a block and sound. <br>

Example of a material ID: https://minecraft.wiki/w/Block_of_Gold#Data_values <br>
Example of a sound ID: https://minecraft.wiki/w/Enderman#Sounds <br>

<i>Full upper cases are not required as this will turn from lower case to full upper cases for you. However, you must be sure that your spelling is correct.</i>

ELEVATOR MATERIAL is a block where you would use as an elevator (e.g. using gold block or, in CivMC's case, lodestone as an elevator).
ELEVATOR SOUND is a sound where you will use as a elevator sound whenever you shift down or jump up.

A config.yml that get copied into your plugin folder should look like this;

ELEVATOR MATERIAL: "GOLD_BLOCK"<br>
ELEVATOR SOUND: "ENTITY_ENDERMAN_TELEPORT"

You may modify both of settings to whichever you want for your server.

# FUNCTION
The main function of this plugin is it teleport you upward or downward using a block your server uses them as an elevator. But there are more to that... Anyone can block elevator from anyone trying to go up or down using any block like dirt, etc. You can also use trapdoor or sign to turn two-ways elevator into one-way elevator.
