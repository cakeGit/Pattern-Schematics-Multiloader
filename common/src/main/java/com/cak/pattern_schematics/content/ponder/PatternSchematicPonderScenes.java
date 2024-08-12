package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.ponder.instruction.DisplayWorldSectionInstruction;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class PatternSchematicPonderScenes {
    
    public static void schematicPrinting(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("schematic_printing", "Printing patterns with pattern schematics");
        scene.scaleSceneView(0.75f);
        scene.configureBasePlate(0, 0, 14);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(20);
        
        //Obtaining schematic for printing
        BlockPos schematicSavingFrom = new BlockPos(5, 1, 4);
        BlockPos schematicSavingTo = new BlockPos(7, 3, 5);
        
        Selection schematicSavingSelection = util.select.fromTo(
            schematicSavingFrom, schematicSavingTo);
        
        scene.world.showSection(schematicSavingSelection, Direction.DOWN);
        
        scene.overlay.showText(80)
            .placeNearTarget()
            .pointAt(new Vec3(6, 3, 5))
            .text("To use pattern schematics, you must first save a schematic to use as a source");
        
        scene.idle(50);
        
        ItemStack schematicAndQuillStack = AllItems.SCHEMATIC_AND_QUILL.asStack();
        
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.BLUE, "schematic_saving",
            new AABB(schematicSavingFrom, schematicSavingTo.offset(1, 1, 1)), 100);
        
        scene.overlay.showControls(
            new InputWindowElement(Vec3.atCenterOf(schematicSavingFrom), Pointing.DOWN)
                .rightClick().withItem(schematicAndQuillStack), 50
        );
        scene.idle(30);
        scene.overlay.showControls(
            new InputWindowElement(Vec3.atCenterOf(schematicSavingTo), Pointing.DOWN)
                .rightClick().withItem(schematicAndQuillStack), 30
        );
        scene.idle(50);
        
        //Creating the pattern schematic item
        
        scene.addKeyframe();
        
        scene.world.showSection(util.select.position(2, 1, 2), Direction.DOWN);
        
        scene.overlay.showText(70)
            .placeNearTarget()
            .pointAt(new Vec3(2.5, 1, 2.5))
            .text("Then, use a schematic table with an empty pattern schematic");
        scene.idle(40);
        
        ItemStack emptyPatternSchematicItem = PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC.asStack();
        ItemStack patternSchematicItem = PatternSchematicsRegistry.PATTERN_SCHEMATIC.asStack();
        ElementLink<EntityElement> currentItem;
        currentItem = scene.world.createItemEntity(
            new Vec3(2.5, 4, 2.5),
            new Vec3(0, 0, 0),
            emptyPatternSchematicItem
        );
        scene.idle(40);
        
        scene.overlay.showText(60)
            .placeNearTarget()
            .pointAt(new Vec3(2.5, 1, 2.5))
            .text("Use the table to upload the schematic");
        
        scene.idle(40);
        scene.world.modifyEntity(currentItem, Entity::kill);
        currentItem = scene.world.createItemEntity(
            new Vec3(2.5, 2, 2.5),
            new Vec3(0, 0.25, 0),
            patternSchematicItem
        );
        scene.idle(50);
        
        //Usage
        
        scene.addKeyframe();
        
        scene.overlay.showText(70)
            .placeNearTarget()
            .pointAt(new Vec3(2.5, 2, 2.5))
            .text("Then, using the clone tool in the options, the structure can be repeated");
        scene.idle(80);
        
        Vec3i sectionOffset = new Vec3i(3, 0, 0);
        BlockPos sectionMin = new BlockPos(1, 1, 11);
        BlockPos sectionMax = new BlockPos(3, 3, 12);
        
        scene.overlay.showText(60)
            .placeNearTarget()
            .pointAt(new Vec3(2.5, 2, 12))
            .text("Clones get placed next to each other");
        
        for (int i = 0; i < 4; i++) {
            scene.world.showSection(util.select.fromTo(
                sectionMin.offset(sectionOffset.multiply(i)),
                sectionMax.offset(sectionOffset.multiply(i))
            ), Direction.DOWN);
            
            scene.idle(2);
        }
        scene.idle(62);
        
        //Usage 2
        
        scene.addKeyframe();
        
        scene.overlay.showText(80)
            .placeNearTarget()
            .pointAt(new Vec3(2.5, 2, 2.5))
            .text("Additionally, the schematic can be rotated");
        scene.idle(60);
        
        Vec3i sectionOffset2 = new Vec3i(0, 0, 3);
        BlockPos sectionMin2 = new BlockPos(11, 1, 1);
        BlockPos sectionMax2 = new BlockPos(12, 3, 3);
        
        scene.overlay.showText(80)
            .placeNearTarget()
            .pointAt(new Vec3(12, 2, 2.5))
            .text("This rotation will then apply to all clones");
        
        for (int i = 0; i < 3; i++) {
            scene.world.showSection(util.select.fromTo(
                sectionMin2.offset(sectionOffset2.multiply(i)),
                sectionMax2.offset(sectionOffset2.multiply(i))
            ), Direction.DOWN);
            
            scene.idle(2);
        }
        scene.idle(50);
        
        scene.markAsFinished();
    }
    
    public static void trainSchematicPrinting(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("train_schematic_printing", "Infinite schematic printing with trains");
        scene.scaleSceneView(0.75f);
        scene.configureBasePlate(0, 0, 12);
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(20);
        
        //Fill in train tracks
        BlockPos
            sectionFrom = new BlockPos(11, 1, 6),
            selectionTo = new BlockPos(11, 2, 8);
        
        for (int i = 0; i <= 12; i++) {
            scene.world.showSection(util.select.fromTo(sectionFrom, selectionTo), Direction.DOWN);
            scene.idle(2);
            
            sectionFrom = sectionFrom.offset(-1, 0, 0);
            selectionTo = selectionTo.offset(-1, 0, 0);
        }
        
        scene.idle(10);
        
        //Place train
        scene.world.showSection(util.select.fromTo(10, 3, 7, 11, 4, 7), Direction.DOWN);
        scene.idle(20);
        scene.world.showSection(util.select.fromTo(10, 3, 2, 10, 4, 6), Direction.SOUTH);
        scene.idle(20);
        
        //Applying schematic
        scene.addKeyframe();
        
        scene.overlay.showSelectionWithText(util.select.fromTo(10, 3, 2, 10, 3, 4), 80)
            .placeNearTarget()
            .pointAt(new Vec3(10.5, 3.5, 3.5))
            .text("A single pattern schematic can be applied to a group of assembled deployers with Shift + Right Click");
        
        scene.idle(80);
        
        scene.overlay.showControls(
            new InputWindowElement(new Vec3(10.5, 3.5, 3.5), Pointing.DOWN)
                .withItem(PatternSchematicsRegistry.PATTERN_SCHEMATIC.get().getDefaultInstance())
                .rightClick()
                .whileSneaking(), 50
        );
        scene.idle(60);
        
        //Train moves 2 blocks to show original schematic
        scene.addKeyframe();
        
        ElementLink<WorldSectionElement> trainSection = scene.world.makeSectionIndependent(
            util.select.fromTo(10, 3, 2, 11, 4, 7)
        );
        
        scene.world.moveSection(trainSection, new Vec3(-1, 0, 0), 10);
        scene.world.animateBogey(util.grid.at(10, 3, 7), 1f, 10);
        
        for (int x = 10; x >= 9; x--) {
            for (int z = 2; z <= 4; z++) {
                scene.world.moveDeployer(util.grid.at(10, 3, z), 1, 4);
            }
            scene.idle(4);
            scene.addInstruction(new DisplayWorldSectionInstruction(
                0, Direction.DOWN,
                util.select.fromTo(x, 1, 2, x, 2, 4),
                Optional.empty()
            ));
            scene.idle(1);
            for (int z = 2; z <= 4; z++) {
                scene.world.moveDeployer(util.grid.at(10, 3, z), -1, 4);
            }
            scene.idle(5);
        }
        scene.idle(30);
        
        scene.overlay.showSelectionWithText(util.select.fromTo(9, 1, 2, 10, 2, 4), 80)
            .placeNearTarget()
            .pointAt(new Vec3(10, 2, 3.5))
            .text("Here, the schematic has been placed as usual");
        scene.idle(90);
        
        scene.overlay.showText(80)
            .placeNearTarget()
            .pointAt(new Vec3(8.5, 3.5, 7.5))
            .text("But moving the train further will result in the schematic repeating");
        scene.idle(90);
        
        scene.world.moveSection(trainSection, new Vec3(-8, 0, 0), 80);
        scene.world.animateBogey(util.grid.at(10, 3, 7), 8f, 80);
        
        scene.idle(5);
        for (int x = 8; x >= 1; x--) {
            for (int z = 2; z <= 4; z++) {
                scene.world.moveDeployer(util.grid.at(10, 3, z), 1, 4);
            }
            scene.idle(4);
            scene.addInstruction(new DisplayWorldSectionInstruction(
                0, Direction.DOWN,
                util.select.fromTo(x, 1, 2, x, 2, 4),
                Optional.empty()
            ));
            scene.idle(1);
            for (int z = 2; z <= 4; z++) {
                scene.world.moveDeployer(util.grid.at(10, 3, z), -1, 4);
            }
            scene.idle(5);
        }
        
        scene.idle(20);
        scene.markAsFinished();
    }
    
}
