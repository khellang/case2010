package shapes;

import javax.media.j3d.QuadArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;

public class Wall extends QuadArray {
	
	public Wall(Point3f p, Color3f color) {
		
		super(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2 | QuadArray.COLOR_3);
        setCoordinate(0, new Point3d(p.x-1.5,p.y+1,p.z));
        setCoordinate(1, new Point3d(p.x-1.5,p.y-1,p.z));
        setCoordinate(2, new Point3d(p.x+1.5,p.y-1,p.z));
        setCoordinate(3, new Point3d(p.x+1.5,p.y+1,p.z));
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
