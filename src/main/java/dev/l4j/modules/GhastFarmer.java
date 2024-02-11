package dev.l4j.modules;

import dev.l4j.GhastFarmerAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;

import java.util.stream.StreamSupport;

public class GhastFarmer extends Module {

    private Vec3d startPosVector, targetPosVector, targetDropPosVector;
    private ItemEntity targetDrop;
    private GhastEntity targetEntity;

    public GhastFarmer() {
        super(GhastFarmerAddon.CATEGORY, "GhastFarmer", "Farm Ghasts while u beat your meat");
    }

    @Override
    public void onActivate() {
        this.startPosVector = mc.player.getPos();
        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        mc.getNetworkHandler().sendChatMessage("#stop");
        super.onDeactivate();
    }

    @EventHandler
    public void onPreTick(TickEvent.Pre event) {
        this.targetEntity = StreamSupport.stream(mc.world.getEntities().spliterator(), false)
            .filter(entity -> entity instanceof GhastEntity)
            .filter(entity -> mc.player.distanceTo(entity) <= 100)
            .map(entity -> ((GhastEntity) entity))
            .findFirst()
            .orElse(null);

        this.targetDrop = StreamSupport.stream(mc.world.getEntities().spliterator(), false)
            .filter(entity -> entity instanceof ItemEntity)
            .filter(entity -> ((ItemEntity) entity).getStack().getItem() == Items.GHAST_TEAR)
            .map(entity -> ((ItemEntity) entity))
            .findFirst()
            .orElse(null);

        if(targetEntity != null) {
            this.targetPosVector = this.targetEntity.getPos();
            mc.getNetworkHandler().sendChatMessage("#goto " + this.targetPosVector.x + " " + this.targetPosVector.y + " " + this.targetPosVector.z);
        } else if(targetDrop != null) {
            this.targetDropPosVector = this.targetDrop.getPos();
            mc.getNetworkHandler().sendChatMessage("#goto " + this.targetDropPosVector.x + " " + this.targetDropPosVector.y + " " + this.targetDropPosVector.z);
        } else {
            mc.getNetworkHandler().sendChatMessage("#goto " + this.startPosVector.x + " " + this.startPosVector.y + " " + this.startPosVector.z);
        }
    }
}
