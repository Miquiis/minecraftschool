//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.miquiis.minecraftschool.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.model.FakePlayerModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.ParrotVariantLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.entity.Entity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FakePlayerRenderer extends LivingRenderer<FakePlayerEntity, FakePlayerModel<FakePlayerEntity>> {
    public FakePlayerRenderer(EntityRendererManager renderManager) {
        this(renderManager, false);
    }

    @Override
    public ResourceLocation getEntityTexture(FakePlayerEntity entity) {
        return new ResourceLocation(MinecraftSchool.MOD_ID, "textures/entity/fake_player.png");
    }

    public FakePlayerRenderer(EntityRendererManager renderManager, boolean useSmallArms) {
        super(renderManager, new FakePlayerModel(0.0F, useSmallArms), 0.5F);
        this.addLayer(new BipedArmorLayer(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new HeldItemLayer(this));
        this.addLayer(new ArrowLayer(this));
        this.addLayer(new HeadLayer(this));
        this.addLayer(new ElytraLayer(this));
        this.addLayer(new SpinAttackEffectLayer(this));
        this.addLayer(new BeeStingerLayer(this));
    }

    public Vector3d getRenderOffset(FakePlayerEntity entityIn, float partialTicks) {
        return entityIn.isCrouching() ? new Vector3d(0.0D, -0.125D, 0.0D) : super.getRenderOffset(entityIn, partialTicks);
    }

    protected void preRenderCallback(FakePlayerEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = 0.9375F;
        matrixStackIn.scale(0.9375F, 0.9375F, 0.9375F);
    }

    protected void renderName(FakePlayerEntity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        double d0 = this.renderManager.squareDistanceTo(entityIn);
        matrixStackIn.push();
        if (d0 < 100.0D) {
            Scoreboard scoreboard = entityIn.world.getScoreboard();
            ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
            if (scoreobjective != null) {
                Score score = scoreboard.getOrCreateScore(entityIn.getScoreboardName(), scoreobjective);
                super.renderName(entityIn, (new StringTextComponent(Integer.toString(score.getScorePoints()))).appendString(" ").appendSibling(scoreobjective.getDisplayName()), matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.translate(0.0D, 0.25874999165534973D, 0.0D);
            }
        }

        super.renderName(entityIn, displayNameIn, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
    }

    protected void applyRotations(FakePlayerEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        float f = entityLiving.getSwimAnimation(partialTicks);
        float f3;
        float f2;
        if (entityLiving.isElytraFlying()) {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
            f3 = (float)entityLiving.getTicksElytraFlying() + partialTicks;
            f2 = MathHelper.clamp(f3 * f3 / 100.0F, 0.0F, 1.0F);
            if (!entityLiving.isSpinAttacking()) {
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entityLiving.rotationPitch)));
            }

            Vector3d vector3d = entityLiving.getLook(partialTicks);
            Vector3d vector3d1 = entityLiving.getMotion();
            double d0 = Entity.horizontalMag(vector3d1);
            double d1 = Entity.horizontalMag(vector3d);
            if (d0 > 0.0D && d1 > 0.0D) {
                double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / Math.sqrt(d0 * d1);
                double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
                matrixStackIn.rotate(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
            f3 = entityLiving.isInWater() ? -90.0F - entityLiving.rotationPitch : -90.0F;
            f2 = MathHelper.lerp(f, 0.0F, f3);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f2));
            if (entityLiving.isActualySwimming()) {
                matrixStackIn.translate(0.0D, -1.0D, 0.30000001192092896D);
            }
        } else {
            super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        }

    }
}
