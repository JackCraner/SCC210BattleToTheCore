package Main.ForeGround.Entities;

import org.jsfml.system.Vector2f;

/**
 * This is the super class for all moving entities within the game [enter game title here]
 */
public class MovingEntity{

    private final float [] position, velocity;

    public static float GRAVITATIONAL_PULL = (float) 0.25;

    /**
     * The constructor for the moving entity that takes in the starting coordinate of the entity
     * @param x the coordinates x value with the increasing positive numbers being to the right
     * @param y the coordinates y value with the increasing positive numbers being to the bottom
     */
    public MovingEntity (float x, float y)
    {
        this.position = new float[] {x, y};
        this.velocity = new float[] {(float) 0.0, (float) 0.0};
    }

    /**
     * Moves the internal position of the entity based on the velocity inputted.<br>
     * Then resets the X and Y velocities back to zero so fof continues movement the velocity must be added each time.<br>
     * This should be done with setVelocity().<br>
     * Intern this will give the feeling of friction on the ground.
     *
     * @see setVelocity
     * @return the position array of the entity in the form [x, y]
     */
    public float[] move()
    {
        this.position[0] += this.velocity[0];
        this.position[1] += this.velocity[1];

        this.velocity[0] = (float) 0.0;
        this.velocity[1] = (float) 0.0;

        return this.velocity;
    }

    /**
     * Moves the internal position of the entity based on the velocity inputted.<br>
     * This does NOT reset the velocity of the entity after call. The Y velocity will increase till a maximum of 2 and the x velocity will stay the same.<br>
     * If X velocity is wished to be changed then use setVelocityWithGravity() for that purpose.<br>
     * Intern this will give the feeling of moving downwards without friction/air-resistance
     *
     * @see setVelocityWithGravity
     * @return the position array of the entity in the form [x, y]
     */
    public float[] moveWithGravity()
    {
        this.velocity[1] += GRAVITATIONAL_PULL;

        if (this.velocity[1] > 2 )
        {
            this.velocity[1] = (float) 2.0;
        }

        this.position[0] += this.velocity[0];
        this.position[1] += this.velocity[1];

        return this.position;
    }

    /**
     * sets the entity to a new position based on the coordinates inputted
     *
     * @param x the coordinates x value with the increasing positive numbers being to the right
     * @param y the coordinates y value with the increasing positive numbers being to the bottom
     */
    public void setPosition(float x, float y)
    {
        this.position[0] = x;
        this.position[1] = y;
    }

    /**
     * Sets the velocity of the entity based on the inputted values.<br>
     * NOTE : MAXIMUM LIMIT SHOULD BE +/- 1 FOR EACH VALUE!
     *
     * @param x the x value with the increasing positive numbers being to the right
     * @param y the y value with the increasing positive numbers being to the bottom
     */
    public void setVelocity(float x, float y)
    {
        this.velocity[0] = x;
        this.velocity[1] = y;
    }

    /**
     * Sets the velocity of the entity when the entity is effected by gravity.<br>
     * NOTE : MAXIMUM LIMIT SHOULD BE +/- 1 FOR EACH VALUE!
     *
     * @param x the x value with the increasing positive numbers being to the right
     */
    public void setVelocityWithGravity(float x)
    {
        this.velocity[0] = x;
    }

    /**
     * Gets the position of the entity and outputs it in the form of a float[] with the structure of [x, y]
     *
     * @return the position coordinates
     */
    public float[] getPosition()
    {
        return this.position;
    }

    /**
     * Gets the velocity of the entity and outputs it in the form of a float[] with the structure of [x, y]
     *
     * @return the velocity values
     */
    public float[] getVelocity()
    {
        return this.velocity;
    }

    /**
     * This is a static function that will take a float input of length 2 and return a Vector2f variable based on it
     *
     * @param input the float[] of length 2
     * @return Vector2f based on the input
     */
    public static Vector2f floatArrayToVector2F(float[] input)
    {
        return new Vector2f(input[0],input[1]);
    }
}
