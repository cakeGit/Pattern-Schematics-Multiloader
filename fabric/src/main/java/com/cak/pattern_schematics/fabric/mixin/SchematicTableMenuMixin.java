package com.cak.pattern_schematics.fabric.mixin;

import com.cak.pattern_schematics.foundation.mixin_accessors.SchematicTableMenuMixinAccessor;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import com.simibubi.create.content.schematics.table.SchematicTableMenu;
import com.simibubi.create.foundation.gui.menu.MenuBase;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlotItemHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SchematicTableMenu.class, remap = false)
public class SchematicTableMenuMixin extends MenuBase<SchematicTableBlockEntity> implements SchematicTableMenuMixinAccessor {
  
  @Shadow
  private Slot inputSlot;
  
  @Override
  public void setInputSlot(Slot slot) {
    inputSlot = slot;
  }
  
  protected SchematicTableMenuMixin(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
    super(type, id, inv, extraData);
  }
  
  @Redirect(method = "addSlots", remap = false, at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lcom/simibubi/create/content/schematics/table/SchematicTableMenu;inputSlot:Lnet/minecraft/world/inventory/Slot;", remap = true))
  private void addSlots_inputSlot(SchematicTableMenu instance, Slot value) {
    ((SchematicTableMenuMixinAccessor) instance).setInputSlot(new SlotItemHandler(contentHolder.inventory, 0, 21, 57) {
      @Override
      public boolean mayPlace(ItemStack stack) {
        return AllItems.EMPTY_SCHEMATIC.isIn(stack)
            || AllItems.SCHEMATIC_AND_QUILL.isIn(stack)
            || AllItems.SCHEMATIC.isIn(stack)
            || PatternSchematicsItems.EMPTY_PATTERN_SCHEMATIC.isIn(stack)
            || PatternSchematicsItems.PATTERN_SCHEMATIC.isIn(stack);
      }
    });
  }
  
  @Shadow
  protected SchematicTableBlockEntity createOnClient(FriendlyByteBuf extraData) { return null; }
  
  @Shadow
  protected void initAndReadInventory(SchematicTableBlockEntity contentHolder) { }
  
  @Shadow
  protected void addSlots() { }
  
  @Shadow
  protected void saveData(SchematicTableBlockEntity contentHolder) { }
  
  @Shadow
  public ItemStack quickMoveStack(Player playerIn, int index) {return null;}
  
}
