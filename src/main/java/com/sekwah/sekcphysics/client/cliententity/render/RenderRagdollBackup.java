package com.sekwah.sekcphysics.client.cliententity.render;

import com.sekwah.sekcphysics.client.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.trackers.Tracker;
import com.sekwah.sekcphysics.ragdoll.ragdolls.vanilla.BipedRagdoll;
import com.sekwah.sekcphysics.ragdoll.ragdolls.vanilla.ZombieRagdoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Ragdoll renderer file
 */
public class RenderRagdollBackup<T extends EntityRagdoll> extends Render<T> {
    // add code to render the lines between the links of nodes, and also the option to render boxes at each node.
    // this entity will never have any rotation from the entity but rather rotations based on the physics positions

    private static final ResourceLocation zombieTexture = new ResourceLocation("textures/entity/zombie/zombie.png");

    private static final ResourceLocation huskTexture = new ResourceLocation("textures/entity/zombie/husk.png");

    private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");

    private ModelBiped bipedModel;
    private ModelBiped bipedModel64;

    private ModelBiped zombieModel;

    private static Minecraft mc = Minecraft.getMinecraft();

    public RenderRagdollBackup(RenderManager renderManager) {
        super(renderManager);
        bipedModel = new ModelBiped();

        bipedModel64 = new ModelBiped(0.0f, 0, 64, 64);

        zombieModel = new ModelBiped(0.0f, 0, 64, 64);
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GL11.glPushMatrix();

        // Sets the position offset for rendering
        GL11.glTranslated(x, y, z);

        ModelBiped currentModel;

        if(entity.ragdoll instanceof BipedRagdoll) {

            if(!entity.ragdoll.trackersRegistered) {
                if(entity.ragdoll instanceof ZombieRagdoll) {
                    entity.ragdoll.initTrackers(zombieModel);
                }
                else{
                    entity.ragdoll.initTrackers(bipedModel64);
                }
            }

            // add husk texure and also some other stuff for rendering properly
            if(entity.ragdoll instanceof ZombieRagdoll) {

                this.bindTexture(zombieTexture);
                currentModel = this.zombieModel;

            }
            else{
                this.bindTexture(steveTextures);
                currentModel = this.bipedModel;
            }

            BipedRagdoll bipedRagdoll = (BipedRagdoll) entity.ragdoll;

            if(mc.gameSettings.showDebugInfo) {
                GL11.glPushMatrix();
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);

                GL11.glColor4f(1,1,1,0.5f);
            }


            for(Tracker tracker : bipedRagdoll.trackerHashmap.values()) {
                //SekCPhysics.logger.info("Test");
                tracker.calcRotation();
                tracker.render();
            }

            if(mc.gameSettings.showDebugInfo) {
                GL11.glColor4f(1, 1, 1, 1);

                GL11.glDepthMask(true);

                GL11.glPopMatrix();
            }
        }

        if(mc.gameSettings.showDebugInfo) {
            entity.ragdoll.skeleton.renderSkeletonDebug(entity.ragdoll.activeStatus());
        }
        GL11.glPopMatrix();
    }

    public void setPartLocation(ModelRenderer trackPart, SkeletonPoint skeletonPart) {
        trackPart.setRotationPoint((float) skeletonPart.posX * 16, (float) skeletonPart.posY * 16, (float) skeletonPart.posZ * 16);
        trackPart.render(0.0625F);
        //trackPart.rotateAngleZ=1;
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }
}
