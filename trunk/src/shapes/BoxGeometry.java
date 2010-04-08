package shapes;

import javax.media.j3d.IndexedQuadArray;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class BoxGeometry extends IndexedQuadArray {
	public BoxGeometry() {
		super(8, IndexedQuadArray.COORDINATES | IndexedQuadArray.NORMALS, 24);
		setCoordinate(0, new Point3f(-0.4f,-0.4f,0.4f));
		setCoordinate(1, new Point3f(0.4f,-0.4f,0.4f));
		setCoordinate(2, new Point3f(0.4f,0.4f,0.4f));
		setCoordinate(3, new Point3f(-0.4f,0.4f,0.4f));
		setCoordinate(4, new Point3f(-0.4f,0.4f,-0.4f));
		setCoordinate(5, new Point3f(0.4f,0.4f,-0.4f));
		setCoordinate(6, new Point3f(0.4f,-0.4f,-0.4f));
		setCoordinate(7, new Point3f(-0.4f,-0.4f,-0.4f));
		int[] coords = {0,1,2,3, 2,5,4,3, 4,5,6,7, 0,7,6,1, 1,6,5,2, 0,3,4,7};
		setCoordinateIndices(0, coords);
		setNormal(0, new Vector3f(1,1,-1));
		setNormal(1, new Vector3f(1,1,-1));
		setNormal(2, new Vector3f(-1,1,1));
		setNormal(3, new Vector3f(-1,1,1));
		setNormal(4, new Vector3f(1,-1,1));
		setNormal(5, new Vector3f(1,-1,1));
		setNormal(6, new Vector3f(1,-1,-1));
		setNormal(7, new Vector3f(1,-1,-1));
		int[] norms = {0,0,0, 1,1,1, 2,2,2, 3,3,3, 4,4,4, 5,5,5, 6,6,6, 7,7,7};
		setNormalIndices(0, norms);
	}
}