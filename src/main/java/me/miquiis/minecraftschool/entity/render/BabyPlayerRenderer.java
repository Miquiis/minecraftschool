package me.miquiis.minecraftschool.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.model.BabyPlayerModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
        renderKindaName(entity, new StringTextComponent("Test"), stack, bufferIn, packedLightIn);
        renderChatbox(entity, new StringTextComponent("Heeyy!"), stack, bufferIn, packedLightIn);
        stack.push();
        stack.scale(0.35f,0.35f,0.35f);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.pop();
    }

    protected void renderKindaName(Entity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        double d0 = this.renderManager.squareDistanceTo(entityIn);
        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(entityIn, d0)) {
            boolean flag = !entityIn.isDiscrete();
            float f = entityIn.getHeight() + 0.5F;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double)f, 0.0D);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());
            matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            int j = (int)(f1 * 255.0F) << 24;
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f2 = (float)(-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);

            //fontrenderer.func_243247_a(displayNameIn, f2, (float)i, 553648127, false, matrix4f, bufferIn, flag,j, packedLightIn);

            if (flag) {
                fontrenderer.func_243247_a(displayNameIn, f2, (float)i, -1, false, matrix4f, bufferIn, false, j, packedLightIn);
            }

            matrixStackIn.pop();
        }
    }

    protected void renderChatbox(Entity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (displayNameIn.getString().isEmpty()) return;
        double d0 = this.renderManager.squareDistanceTo(entityIn);
        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(entityIn, d0)) {
            boolean flag = !entityIn.isDiscrete();
            float f = entityIn.getHeight() + 1.0F;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double)f, 0.0D);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());
            matrixStackIn.scale(-0.045F, -0.045F, 0.045F);
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            int j = (int)(f1 * 255.0F) << 24;
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f2 = (float)(-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);

            //fontrenderer.func_243247_a(displayNameIn, f2, (float)i, 12565825, false, matrix4f, bufferIn, flag,j, packedLightIn);

            if (flag) {
                fontrenderer.func_243247_a(displayNameIn, f2, (float)i, 16776279, false, matrix4f, bufferIn, false, j, packedLightIn);
            }

            matrixStackIn.pop();
        }
    }

    @Override
    public ResourceLocation getEntityTexture(BabyPlayerEntity entity) {
        return null;
    }
}
