# Warning to anyone who wishes to contribute:
This code has a lot of mixins and dodgy mirror classes,
View at your own risk,
Same goes for anyone who wants to use this code for reference (mit so go at it if you want),

# Features
## Infinite Contraption Construction [(Development Test Demo)](https://www.youtube.com/watch?v=EhZnNdxGKrg&ab_channel=SomeGuyCalledCak)

Pattern Schematics can be placed on trains, gantry carriages or any other contraption to infinitely build iterations of the schematic.

![Schematic Printing Explanation](https://cdn.modrinth.com/data/cpqKG67r/images/f746aa1bb02aee9b78175aab67606b7f92b0e07e.png)

## Building Patterns [(Development Test Demo)](https://www.youtube.com/watch?v=jGvMWfpR8nQ&ab_channel=SomeGuyCalledCak)

These pattern schematics can be used to repeat existing schematics, such as repeating a bridge to make it longer! Print in creative or put into a schematicannon to build!

![Printing Demo](https://cdn.modrinth.com/data/cpqKG67r/images/b3043b120d7f7d16a3e9f6b6646cc8bd69f29d44.png)

## ! Please report any issues to the github !
This is a new mod, and may have some bugs which will need fixing,

# Changelog (as of 1.1.8)
1.1.3:
- Fixed display bug, thanks to discord user @karolofgutovo for reporting
- Created ponder system with 1 ponder about printing (contraption ponder coming soon)
- (Technical) Created datagen

1.1.4:
- Fixed a bug with deployers placing at the wrong offsets
- Created bulk apply system to reduce pain of having to put a schematic on each individual deployer

1.1.5:
- Fixed clone outlines rendering on a non-deployed schematic
- Improved tool usage on pattern schematics (thanks @d4rkfl4sh for the suggestion)
- Added ponder for contraption uses of schematics

1.1.6: (No functional changes)
- Updated mod links in mods.toml and the fabric.mod.json
- Forge now has the update checker configured
- Fixed the copycats not displaying accurately in the ponder (after I failed to cover up my laziness thanks @d4rkfl4sh)

1.1.7:
- Added shift control to the clone tool to help with large schematics

1.1.8:
- Made the schematicannon result in the proper item (previously would give Create's schematic no matter what)
- Added an indicator when applying a non positioned schematic to a contraption deployer
