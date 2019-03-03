package game.render;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A class for loading in a spritesheet
 */
public class SpriteLoader {
    private BufferedImage[] sprites;
    private BufferedImage[] scaledSprites;

    /**
     * Creates a loader with pixels of width <code>width</code>
     * and of height <code>height</code>,
     * in an image with <code>columns</code> columns
     * and <code>rows</code> rows, in <code>path</code>
     * @param width the width of each pixel
     * @param height the height of each pixel
     * @param rows the number of rows in the image
     * @param columns the number of columns in the image
     * @param path the path to the image
     * @param override_scale if this is >0 then will overwrite the scale from <code>ViewThings.SCALE_FACTOR</code>
     * @throws IOException if <code>path</code> cannot be found or accessed
     */
    public SpriteLoader(int width, int height, int rows, int columns, String path, int override_scale) throws IOException {
        int scalefac = (override_scale > 0) ? override_scale : 4;

        BufferedImage spriteSheet = ImageIO.read(new File(path));
        BufferedImage scaledSpriteSheet = new BufferedImage(spriteSheet.getWidth() * scalefac, spriteSheet.getHeight() * scalefac,
                BufferedImage.TYPE_INT_ARGB);

        AffineTransform scale = AffineTransform.getScaleInstance(scalefac, scalefac);
        AffineTransformOp op = new AffineTransformOp(scale, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        scaledSpriteSheet = op.filter(spriteSheet, scaledSpriteSheet);

        sprites = new BufferedImage[rows * columns];
        scaledSprites = new BufferedImage[rows * columns];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                int index = (i * columns) + j;

                sprites[index] = spriteSheet.getSubimage(i * width, j * height, width, height);
                scaledSprites[index] = scaledSpriteSheet.getSubimage((i * width) * scalefac, (j * height) * scalefac,
                        width * scalefac, height * scalefac);
            }
        }
    }

    /**
     * Returns the sprite of ID <code>sprite</code> in the spritesheet
     * @param sprite the ID of the sprite wanted
     * @return a <code>BufferedImage</code> of that sprite
     */
    @Deprecated
    public BufferedImage getSprites(int sprite) {
        return sprites[sprite];
    }

    /**
     * Returns a scaled version of the original sprite
     * The scale factor is that in <code>ViewThings.SCALE_FACTOR</code>
     * @param sprite the sprite
     * @return a scaled version of that sprite
     */
    public BufferedImage getScaledSprites(int sprite) {
        return scaledSprites[sprite];
    }
}