package de.jkeller.dwargen.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class AnimationManager implements Disposable {

    private final String FILENAME;

    /**
     * standardised constants for creature animations consisting of 3 columns and 4 rows of texture regions
     * Columns:
     *  -Left: Standing
     *  -Middle: Right leg in front
     *  -Right: Left leg in front
     */
    private static final int FRAME_COLS = 3;

    /**
     * Rows:
     *  -First: Facing downwards
     *  -Second: Facing upwards
     *  -Third: Facing Left
     *  -Fourth: Facing Right
     */
    private static final int FRAME_ROWS = 4;

    //Constants to make it easier to point to each direction when making an animation
    public static final int DOWN = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int STANDING = 4;

    Animation<TextureRegion> walkDown;
    Animation<TextureRegion> walkUp;
    Animation<TextureRegion> walkLeft;
    Animation<TextureRegion> walkRight;

    //Textures used for animations
    private Texture walkSheet;
    private TextureRegion[] walkDownFrames;
    private TextureRegion[] walkUpFrames;
    private TextureRegion[] walkLeftFrames;
    private TextureRegion[] walkRightFrames;

    //Directions the player could be facing when standing
    private TextureRegion faceDown;
    private TextureRegion faceUp;
    private TextureRegion faceLeft;
    private TextureRegion faceRight;

    /**
     * Constructor for animation giving the path to the SpriteSheet
     * @param filename
     * Filename of the SpriteSheet
     */
    AnimationManager(String filename) {
        this.FILENAME = filename;
    }

    /**
     * Creates walk animations for each direction and assigns them to the corresponding variables
     */

    @SuppressWarnings("all")
    public void createWalkAnimation() {
        walkSheet = new Texture(Gdx.files.internal("characters/" + FILENAME));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        walkDownFrames = new TextureRegion[FRAME_COLS];
        walkUpFrames = new TextureRegion[FRAME_COLS];
        walkLeftFrames = new TextureRegion[FRAME_COLS];
        walkRightFrames = new TextureRegion[FRAME_COLS];
        faceDown = tmp[DOWN][0];
        faceUp = tmp[UP][0];
        faceLeft = tmp[LEFT][0];
        faceRight = tmp[RIGHT][0];

        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                switch(i) {
                    case DOWN:
                        walkDownFrames[j] = tmp[i][j];
                        break;
                    case UP:
                        walkUpFrames[j] = tmp[i][j];
                        break;
                    case LEFT:
                        walkLeftFrames[j] = tmp[i][j];
                        break;
                    case RIGHT:
                        walkRightFrames[j] = tmp[i][j];
                        break;
                }
            }
        }

        //One AnimationManager for each possible direction (No diagonal animations)
        walkDown = new Animation(0.2f, walkDownFrames);
        walkUp = new Animation(0.2f, walkUpFrames);
        walkLeft = new Animation(0.2f, walkLeftFrames);
        walkRight = new Animation(0.2f, walkRightFrames);

    }

    @Override
    public void dispose() {
        walkSheet.dispose();
    }

}
