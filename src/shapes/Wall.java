package shapes;

import javax.media.j3d.QuadArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.TexCoord2f;

public class Wall extends QuadArray {
	
	public Wall(Color3f color) {
		
		super(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.COLOR_3);
        setCoordinate(0, new Point3d(-1.5,1,-2));
        setCoordinate(1, new Point3d(-1.5,-1,-2));
        setCoordinate(2, new Point3d(1.5,-1,-2));
        setCoordinate(3, new Point3d(1.5,1,-2));
        setTextureCoordinate(0,0, new TexCoord2f(0f, 1f));
        setTextureCoordinate(0,1, new TexCoord2f(0f, 0f));
        setTextureCoordinate(0,2, new TexCoord2f(1f, 0f));
        setTextureCoordinate(0,3, new TexCoord2f(1f, 1f));
        setColor(0, color);
        setColor(1, color);
        setColor(2, color);
        setColor(3, color);
		
	}
}
