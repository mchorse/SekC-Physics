package com.sekwah.sekcphysics.ragdoll.parts;

import com.sekwah.sekcphysics.cliententity.EntityRagdoll;
import com.sekwah.sekcphysics.ragdoll.Point;

/**
 * Created by sekawh on 8/6/2015.
 *
 * Needs a different name but is currently a placeholder for stuff
 *
 * May need complete changing
 *
 */
public class AngleLimitConstraint {

    // so far will only be working with rigid constraints,
    //public boolean isRigid = true;

    // if using a non rigid then
    // public float[] length = new float[2];

    // but till they are needed

    // TODO add rotation constraints to single links which arnt triangles, would be good for simulating shoulders
    // but for now just add some stuff so that way its sorta rough physics(shoulders will have no leeway due to constraints
    //public boolean hasAngleConstraint = false;

    /**
     * The points the constraint is attached to so at each end of the constraint.
     */
    public SkeletonPoint[] end = new SkeletonPoint[2];

    public AngleLimitConstraint(SkeletonPoint base, SkeletonPoint left, SkeletonPoint right){
        this.end[0] = base;
        this.end[1] = left;
        this.end[2] = right;
    }


    public void apply(EntityRagdoll entity) {
        // Center between left and right
        Point averageLoc = new Point((end[1].posX + end[2].posX) / 2F,(end[1].posY + end[2].posY) / 2F,(end[1].posZ + end[2].posZ) / 2F);

        double currentLength = Math.sqrt(Math.pow(end[0].posX - averageLoc.getX(), 2) + Math.pow(end[0].posY - averageLoc.getY(), 2) + Math.pow(end[0].posZ - averageLoc.getZ(), 2));
        // Direction from the base directly down the center of the triangle
        Point direction = new Point((end[0].posX - averageLoc.getX()) / (float) currentLength,
                (end[0].posY - averageLoc.getY()) / (float) currentLength, (end[0].posZ - averageLoc.getZ()) / (float) currentLength);

        // Calculate angle around the direction, may be best way to calculate orentation and make basic constraints on
        // but using the direction and getting an x and y(or whichever 2) to get the direction aligned before rotation
        // would be great for rendering :)
    }
}
