package Main.ForeGround.Entities;

import Main.Game;
import org.jsfml.system.Vector2f;

/**
 * This is the super class for all moving entities within the game [enter game title here]
 */
public class MovingEntity extends Entity{

    private final float [] position, velocity;

    public static float GRAVITATIONAL_PULL = (float) 0.020;

    /**
     * The constructor for the moving entity that takes in the starting coordinate of the entity
     *
     * @param id the id of the entity.
     * @param position the Vector2f of the starting position.
     */
    public MovingEntity (int id, Vector2f position)
    {
        super(id);
        this.position = new float[] {position.x, position.y};
        this.velocity = new float[] {(float) 0.0, (float) 0.0};
    }

    /**
     * sets the entity to a new position based on the coordinates inputted
     *
     * @param position the Vector2f of the new position.
     */
    public void setEntityPosition(Vector2f position)
    {
        this.position[0] = position.x;
        this.position[1] = position.y;
    }

    /**
     * Sets the velocity of the entity based on the inputted values.<br>
     * NOTE : MAXIMUM LIMIT SHOULD BE +/- 1 FOR EACH VALUE!
     *
     * @param velocity he Vector2f of the change in velocity.
     */
    public void setVelocity(Vector2f velocity)
    {
        this.velocity[0] = velocity.x;
        this.velocity[1] = velocity.y;
    }

    /**
     * Sets the velocity of the entity when the entity is effected by gravity.<br>
     * NOTE : MAXIMUM LIMIT SHOULD BE +/- 1 FOR EACH VALUE!
     *
     * @param velocity the Vector2f of the change in velocity.
     *                 NOTE : ONLY THE X VALUE WILL BE USED IN THIS METHOD!
     */
    public void setVelocityWithGravity(Vector2f velocity)
    {
        this.velocity[0] = velocity.x;
    }

    /**
     * Gets the position of the entity and outputs it in the form of a Vector2f with the structure of [x, y]
     *
     * @return the position coordinates
     */
    public Vector2f getPosition()
    {
        return new Vector2f(this.position[0], this.position[1]);
    }

    /**
     * Gets the velocity of the entity and outputs it in the form of a Vector2f with the structure of [x, y]
     *
     * @return the velocity values
     */
    public Vector2f getVelocity()
    {
        return new Vector2f(this.velocity[0], this.velocity[1]);
    }

    /**
     * Moves the internal position of the entity based on the velocity inputted.<br>
     * Then resets the X and Y velocities back to zero so fof continues movement the velocity must be added each time.<br>
     * This should be done with setVelocity().<br>
     * Intern this will give the feeling of friction on the ground.
     *
     * @see this.setVelocity
     * @return the position array of the entity in the form [x, y]
     */
    public float[] move()
    {
        move(getVelocity());
        this.position[0] += this.velocity[0];
        this.position[1] += this.velocity[1];

        this.velocity[0] = (float) 0.0;
        this.velocity[1] = (float) 0.0;

        return this.velocity;
    }

    /**
     * Moves the internal position of the entity based on the velocity inputted.<br>
     * This does NOT reset the velocity of the entity after call. The Y velocity will increase till a maximum of 8 and the x velocity will stay the same.<br>
     * If X velocity is wished to be changed then use setVelocityWithGravity() for that purpose.<br>
     * Intern this will give the feeling of moving downwards without friction/air-resistance
     *
     * @see this.setVelocityWithGravity
     * @return the position array of the entity in the form [x, y]
     */
    public float[] moveWithGravity()
    {
        move(getVelocity());
        this.velocity[1] += GRAVITATIONAL_PULL;

        if (this.velocity[1] > 4 )
        {
            this.velocity[1] = (float) 4.0;
        }

        this.position[0] += this.velocity[0];
        this.position[1] += this.velocity[1];

        return this.position;
    }

    public boolean hasMoved(Vector2f position)
    {
        return (position.x == this.position[0] && position.y == this.position[1]);
    }

    public Vector2f inChunk()
    {
        double chunkX = this.getPosition().x/ Game.chunkSizePixels;
        double chunkY = this.getPosition().y/Game.chunkSizePixels;
        return new Vector2f((float)Math.floor(chunkX), (float)Math.floor(chunkY));
    }

    public Vector2f inBlock()
    {
        Vector2f chunkPos = inChunk();
        Vector2f charPos = this.getPosition();
        return new Vector2f((float)Math.floor((charPos.x - chunkPos.x * Game.chunkSizePixels)/Game.blockSize), (float)Math.floor((charPos.y - chunkPos.y * Game.chunkSizePixels)/Game.blockSize));
    }
}
