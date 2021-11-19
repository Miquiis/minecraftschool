package me.miquiis.minecraftschool.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.model.BabyPlayerModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import software.bernie.example.client.model.entity.ExampleEntityModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BabyPlayerRenderer extends GeoEntityRenderer<BabyPlayerEntity> {

    public BabyPlayerRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new BabyPlayerModel());
        this.shadowSize = 0.2F; //change 0.7 to the desired shadow size.
    }

    @Override
    public void render(BabyPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        stack.push();
        stack.scale(0.35f,0.35f,0.35f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(BabyPlayerEntity entity) {
        return null;
    }
}
