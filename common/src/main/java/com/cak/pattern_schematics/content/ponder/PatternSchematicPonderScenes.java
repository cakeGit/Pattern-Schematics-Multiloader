package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
            .text("Then, using the clone tool in placement, the structure can be repeated");
        scene.idle(60);
        
        Vec3i sectionOffset = new Vec3i(3, 0, 0);
        BlockPos sectionMin = new BlockPos(1, 1, 11);
        BlockPos sectionMax = new BlockPos(3, 3, 12);
        
        scene.overlay.showText(60)
            .placeNearTarget()
            .pointAt(new Vec3(2.5, 2, 12))
            .text("Starting from the origin, use the clone tool to repeat");
        
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
    
}
