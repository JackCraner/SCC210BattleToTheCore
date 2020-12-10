package Main.Physics;

import org.jsfml.system.Vector2f;

public class CollisionBox {
    private float [] TLPoint = new float [2];
    private float [] BRPoint = new float [2];

    /**
     * Constructor for the collision box that will take in the top left point and the bottom right point
     * and store them for later use.
     *
     * @param TLPoint the top left point on the form (x,y)
     * @param BRPoint the bottom right point on the form (x,y)
     */
    public CollisionBox(Vector2f TLPoint, Vector2f BRPoint)
    {
        this.TLPoint = new float[]{TLPoint.x, TLPoint.y};
        this.BRPoint = new float[]{BRPoint.x, BRPoint.y};
    }

    /**
     * Constructor for the collision box that will take in the top left point and the bottom right point
     * and store them for later use.
     *
     * @param left the X value of the top left point
     * @param top the Y value of the top left point
     * @param right the X value of the bottom right point
     * @param bottom the Y value of the bottom right point
     */
    public CollisionBox(float left, float top, float right, float bottom)
    {
        this.TLPoint[0] = left;
        this.TLPoint[1] = top;
        this.BRPoint[0] = right;
        this.BRPoint[1] = bottom;
    }

    /**
     * This checks if another instance of the CollisionBox is overlapping or touching this CollisionBox
     * @param box another CollisionBox
     * @return true if they are overlapping or touching and false if not
     */
    public boolean checkCollistion(CollisionBox box)
    {
        if (this.TLPoint[0] > box.getBRPoint()[0] || box.getTLPoint()[0] > this.BRPoint[0]) return false;
        if (this.TLPoint[1] < box.getBRPoint()[1] || box.getTLPoint()[1] < this.BRPoint[1]) return false;
        return true;
    }

    /**
     * this function will move the box by the specified amount
     * @param movement the amount of movement that the object being tracked takes with right bing positive x and
     *                 down being positive y
     */
    public void moveBox(Vector2f movement)
    {
        this.TLPoint[0] += movement.x;
        this.TLPoint[1] += movement.y;
        this.BRPoint[0] += movement.x;
        this.BRPoint[1] += movement.y;
    }

    /**
     * Sets the poition of the collistion box and allows the movement of the box along with rescaling
     *
     * @param TLPoint the top left coordinate of the box
     * @param BRPoint the bottom right coordinate of the box
     */
    public void setPosition(Vector2f TLPoint, Vector2f BRPoint)
    {
        this.TLPoint = new float[]{TLPoint.x, TLPoint.y};
        this.BRPoint = new float[]{BRPoint.x, BRPoint.y};
    }

    /**
     * gives the top left point
     * @return gives the top left point in the form of a float[] with 2 elements
     */
    public float[] getTLPoint()
    {
        return TLPoint;
    }

    /**
     * gives the bottom right point
     * @return gives the bottom right point in the form of a float[] with 2 elements
     */
    public float[] getBRPoint()
    {
        return BRPoint;
    }
}
