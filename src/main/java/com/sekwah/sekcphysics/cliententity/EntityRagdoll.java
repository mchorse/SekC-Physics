package com.sekwah.sekcphysics.cliententity;

import com.sekwah.sekcphysics.ragdoll.BaseRagdoll;
import com.sekwah.sekcphysics.ragdoll.Point;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by sekawh on 8/1/2015.
 */
public class EntityRagdoll extends Entity {

    public BaseRagdoll ragdoll;

    public int ragdollLife = 600;

    public int ragdollUpdate = 20;

    public EntityRagdoll(World p_i1582_1_) {
        super(p_i1582_1_);
        this.setSize(0.15F, 0.15F);

        // the actual entity instance will be used mostly for lighting and a reference,
        //  the entities position will follow the first point added to the skeleton.

        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {

    }

    public void onUpdate()
    {
        super.onUpdate();
        if(ragdoll == null){
            this.setDead();
            return;
        }
        if(ragdollLife-- < 0){
            this.setDead();
        }

        /*if(ragdollUpdate-- < 0){
            ragdollUpdate = 20;
            ragdoll.update(this);
        }*/
        // Possibly change to update every render rather than entity update and add alpha time
        ragdoll.update(this);

        Point ragdollPos = ragdoll.skeleton.points.get(0).toPoint();

        this.setPosition(this.posX + ragdollPos.getX(), this.posY + ragdollPos.getY(), this.posZ + ragdollPos.getZ());

        ragdoll.shiftPos(-ragdollPos.getX(), -ragdollPos.getY(), -ragdollPos.getZ());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    public void setSpawnPosition(double posX, double posY, double posZ) {
        posY += (ragdoll.centerHeightOffset / 16f);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        float f = this.width / 2.0F;
        float f1 = this.height;
        this.boundingBox.setBounds(posX - (double)f, posY - (double)this.yOffset + (double)this.ySize, posZ - (double)f, posX + (double)f, posY - (double)this.yOffset + (double)this.ySize + (double)f1, posZ + (double)f);

        // the entity position will probably follow the simulated ragdoll position and not the other way.
        //this.ragdoll.setRagdollPos(this.posX, this.posY, this.posZ);
    }

    /**
     * Sets the rotation of the entity
     */
   public void setRotation(float rotYaw/*, float p_70101_2_*/)
    {
        this.ragdoll.rotateRagdoll(rotYaw);
        //this.rotationPitch = p_70101_2_ % 360.0F;
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRender3d(double p_145770_1_, double p_145770_3_, double p_145770_5_)
    {
        double d3 = this.posX - p_145770_1_;
        double d4 = this.posY - p_145770_3_;
        double d5 = this.posZ - p_145770_5_;
        double d6 = d3 * d3 + d4 * d4 + d5 * d5;
        return this.isInRangeToRenderDist(d6);
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_70112_1_)
    {
        /*double d1 = this.boundingBox.getAverageEdgeLength();
        d1 *= 64.0D * this.renderDistanceWeight;*/
        double d1 = 64;
        //SekCPhysics.LOGGER.info(d1);
        return p_70112_1_ < d1 * d1;
    }

    // TODO create objects for the entities containing the physics skeleton, positions and velocities
    // How the ragdolls will be rendered will be down to the render file to track the skeleton
    // So there needs to be a way to link specific boxes to parts of the skeleton and keep the model
    // on the ragdoll.

    // Also add code somewhere to render the links between nodes to view where the skeleton is.
}
