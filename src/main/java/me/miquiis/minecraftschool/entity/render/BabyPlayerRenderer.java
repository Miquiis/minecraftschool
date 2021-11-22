package me.miquiis.minecraftschool.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.model.BabyPlayerModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.client.event.RenderLivingEvent;
import software.bernie.example.client.model.entity.ExampleEntityModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BabyPlayerRenderer extends GeoEntityRenderer<BabyPlayerEntity> {

    public BabyPlayerRenderer(EntityRendererManager renderManager)
    {
        super(renderManager, new BabyPlayerModel());
        this.shadowSize = 0.2F; //change 0.7 to the desired shadow size.
    }

    @Override
    public void render(BabyPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        renderKindaName(entity, new StringTextComponent("Test"), stack, bufferIn, packedLightIn);
//        renderChatbox(entity, new StringTextComponent("Heeyy!"), stack, bufferIn, packedLightIn);
        draw(entity, stack, bufferIn, packedLightIn, true, true);
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

    public static void draw(Entity entityIn, MatrixStack stack, IRenderTypeBuffer buffers, int packedLightIn, final boolean drawText, final boolean drawLittleBubbles) {
        final int bubbleSize = 5;
        final float size = -0.02f - bubbleSize * 0.001f;
        final float f = entityIn.getHeight() + 1.0f + bubbleSize * 0.01f;
        stack.push();
        stack.translate(0.0, (double)f, 0.0);
        stack.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
        stack.scale(size, size, size);
        RenderSystem.enableDepthTest();
        final FontRenderer renderer = Minecraft.getInstance().fontRenderer;
        final java.util.List<String> lines = (java.util.List<String>)renderer.getCharacterManager().func_238365_g_("Hello, World!", 100, Style.EMPTY).stream().map(ITextProperties::getString).collect(Collectors.toList());
        final int linesHeight = 10 * lines.size();
        int biggestLineWidth = 0;
        for (final String line : lines) {
            final int lineWidth = renderer.getStringWidth(line);
            if (biggestLineWidth < lineWidth) {
                biggestLineWidth = lineWidth;
            }
        }
        drawBigBubble(stack, biggestLineWidth, linesHeight, Color.BLACK.getRGB(), Color.WHITE.getRGB());
//        final float yTranslate = (float)(lines.size() + 15);
//        final int i2 = biggestLineWidth / 2;
//        stack.translate((double)(i2 + 20), (double)(-yTranslate), 0.0);
//        final float scale = 1.0f;
//        stack.scale(scale, scale, scale);
//        stack.translate(0.0, (double)(-linesHeight / 2.0f - 15.0f), 0.0);
//        if (true) {
//            int distanceBetween = -10;
//            stack.translate(0.0, (double)(-distanceBetween), 0.0);
//            drawBubble(i2, -linesHeight / 2, Color.BLUE.getRGB(), Color.CYAN.getRGB(), stack);
//        }
//        if (drawLittleBubbles) {
//            drawLittleBubble(i2, -linesHeight / 2, Color.BLUE.getRGB(), Color.CYAN.getRGB(), stack);
//            drawMediumBubble(i2, -linesHeight / 2, Color.RED.getRGB(), Color.YELLOW.getRGB(), stack);
//        }
//        if (drawText) {
//            stack.translate(0.0, 0.0, 0.1);
//            if (Color.WHITE.getAlpha() > 3) {
//                final boolean flag = !entityIn.isDiscrete();
//                int lineY = -linesHeight / 2 + 3;
//                for (final String line2 : lines) {
//                    renderer.renderString(line2, -renderer.getStringWidth(line2) / 2.0f, (float)lineY, Color.WHITE.getRGB(), false, stack.getLast().getMatrix(), buffers, false, 0, packedLightIn);
//                    final int n = lineY;
//                    renderer.getClass();
//                    lineY = n + 9 + 1;
//                }
//            }
//        }
        stack.pop();
    }

    public static void fill(final MatrixStack matrixStack, final int p_fill_0_, final int p_fill_1_, final int p_fill_2_, final int p_fill_3_, final int p_fill_4_) {
        AbstractGui.fill(matrixStack, p_fill_0_, p_fill_1_, p_fill_2_, p_fill_3_, p_fill_4_);
    }

    public static void drawBubble(final int i, final int size, final int outlineColor, final int insideColor, final MatrixStack matrix) {
        fill(matrix, -i - 1, size + 1, i + 1, size + 2, outlineColor);
        fill(matrix, -i - 1, -size + 4, i + 1, -size + 5, outlineColor);
        fill(matrix, -i - 1, size + 2, -i - 2, size + 3, outlineColor);
        fill(matrix, -i - 1, -size + 4, -i - 2, -size + 3, outlineColor);
        fill(matrix, -i - 3, size + 3, -i - 2, -size + 3, outlineColor);
        fill(matrix, i + 3, size + 3, i + 2, -size + 3, outlineColor);
        fill(matrix, i + 1, -size + 4, i + 2, -size + 3, outlineColor);
        fill(matrix, i + 1, size + 2, i + 2, size + 3, outlineColor);
        fill(matrix, -i - 1, size + 2, i + 1, -size + 4, insideColor);
        fill(matrix, -i - 2, size + 3, -i - 1, -size + 3, insideColor);
        fill(matrix, i + 2, size + 3, i + 1, -size + 3, insideColor);
    }

    public static void drawMediumBubble(final int i, final int size, final int outlineColor, final int insideColor, final MatrixStack stack) {
        fill(stack, -i + 1, -size + 11, -i - 6, -size + 10, outlineColor);
        fill(stack, -i + 2, -size + 12, -i + 1, -size + 11, outlineColor);
        fill(stack, -i + 2, -size + 18, -i + 1, -size + 17, outlineColor);
        fill(stack, -i - 7, -size + 12, -i - 6, -size + 11, outlineColor);
        fill(stack, -i - 7, -size + 18, -i - 6, -size + 17, outlineColor);
        fill(stack, -i + 1, -size + 19, -i - 6, -size + 18, outlineColor);
        fill(stack, -i + 2, -size + 12, -i + 3, -size + 17, outlineColor);
        fill(stack, -i - 7, -size + 12, -i - 8, -size + 17, outlineColor);
        fill(stack, -i + 1, -size + 11, -i - 6, -size + 18, insideColor);
        fill(stack, -i - 7, -size + 12, -i - 6, -size + 17, insideColor);
        fill(stack, -i + 1, -size + 12, -i + 2, -size + 17, insideColor);
    }

    public static void drawLittleBubble(final int i, final int size, final int outlineColor, final int insideColor, final MatrixStack stack) {
        fill(stack, -i + 1 - 11, -size + 11 + 17, -i - 6 - 9, -size + 10 + 17, outlineColor);
        fill(stack, -i + 2 - 11, -size + 12 + 17, -i - 10, -size + 11 + 17, outlineColor);
        fill(stack, -i + 2 - 11, -size + 18 + 16, -i - 10, -size + 17 + 16, outlineColor);
        fill(stack, -i - 7 - 8, -size + 12 + 17, -i - 6 - 10, -size + 11 + 17, outlineColor);
        fill(stack, -i - 7 - 9, -size + 18 + 16, -i - 5 - 10, -size + 17 + 16, outlineColor);
        fill(stack, -i + 1 - 11, -size + 19 + 16, -i - 5 - 10, -size + 18 + 16, outlineColor);
        fill(stack, -i + 2 - 11, -size + 12 + 17, -i + 2 - 10, -size + 17 + 16, outlineColor);
        fill(stack, -i - 6 - 11, -size + 12 + 17, -i - 6 - 10, -size + 17 + 16, outlineColor);
        fill(stack, -i + 1 - 11, -size + 11 + 17, -i - 6 - 9, -size + 18 + 16, insideColor);
        fill(stack, -i - 7 - 9, -size + 12 + 17, -i - 6 - 9, -size + 17 + 16, insideColor);
        fill(stack, -i + 1 - 11, -size + 12 + 17, -i + 2 - 11, -size + 17 + 16, insideColor);
    }

    public static void drawBigBubble(final MatrixStack stack, int width, int height, final int borderColor, final int insideColor)
    {
        fill(stack, 0, 0, 3, 1, borderColor);
        fill(stack, 0, 0, 1, -1, borderColor);
        fill(stack, 3, 0, 5, -1, borderColor);
        fill(stack, 3, 0, 5, -1, borderColor);
        fill(stack, 1, -1, 2, -3, borderColor);
        fill(stack, 5, -1, 6, -3, borderColor);
        fill(stack, 0, -3, 1, -4 -height, borderColor);
        fill(stack, 1, -4 -height, 2, -5 -height, borderColor);
        fill(stack, 2, -5 -height, 3 + 3 + (width), -6 -height, borderColor);
        fill(stack, 6, -2, 6 + width, -3, borderColor);
        fill(stack, 6 + width, -3, 7 + width, -4, borderColor);
        fill(stack, 7 + width, -4, 8 + width, -4 - height, borderColor);
        fill(stack, 6 + width, -4 - height, 7 + width, -5 - height, borderColor);
        fill(stack, 1, 0, 3, -1, insideColor);
        fill(stack, 2, -1, 5, -3, insideColor);
        fill(stack, 1, -4, 7 + width, -4 -height, insideColor);
        fill(stack, 1, -3, 6 + width, -4, insideColor);
        fill(stack, 2, -4 - height, 6 + width, -5 - height, insideColor);

    }

    @Override
    public ResourceLocation getEntityTexture(BabyPlayerEntity entity) {
        return null;
    }
}
