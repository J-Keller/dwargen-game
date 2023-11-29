package de.jkeller.dwargen.creatures;

import com.badlogic.gdx.math.Vector2;
import de.jkeller.dwargen.GameEntity;


//TODO: Think about the inventory class
public abstract class NewCreature extends GameEntity {

    public static final float BASE_WIDTH = 32;
    public static final float BASE_HEIGHT = 48;

    private AnimationManager animationManager;
    private AnimationPlayer animationPlayer;

    private float health = 100;
    private float maxHealth = 100;
    private float speed = 2;

    private Vector2 velocity = new Vector2();

    protected NewCreature(AnimationManager animationManager) {
        this.animationManager = animationManager;
        animationPlayer = new AnimationPlayer(animationManager);
    }

    public float getHealth() {
        return health;
    }

    protected void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    protected void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    protected void setVelocity(float x, float y) {
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.setLength(speed);
    }

    protected void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
        this.velocity.setLength(speed);
    }

    @Override
    public abstract void create();

    @Override
    public abstract void update();

    @Override
    public abstract void dispose();
}
