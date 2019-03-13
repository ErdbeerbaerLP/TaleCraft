package talecraft.client.environment;


import java.util.Arrays;
import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

public class Environment {
	public Conditions predicate = null;
	public String lighting = null;
	public Fog fog = null;
	public List<CloudLayer> clouds = null;
	public List<SkyLayer> sky = null;
	
	public Environment() {}
	
	@Override
	public String toString() {
		return String.format("{lighting: %s, fog: %s, clouds: %s, sky: %s}", lighting, fog, clouds, sky);
	}
	
	public static class Conditions {
		public List<String> weather = null;
		public ConditionScore score = null;
		public ConditionHealth health = null;
		public ConditionEntity entity = null;
		
		public static class ConditionScore {
			public String name = null;
			public String comp = "below";
			public int data = 1;
		}
		
		public static class ConditionHealth {
			public String comp = "below";
			public float data = 100.0f;
		}
		
		public static class ConditionEntity {
			public String type = null;
			public String name = null;
			public float range = 50.f;
		}
	}
	
	public static class Fog {
		public float start = 1.0f;
		public float end = 100.0f;
		public float density = 0.1f;
		public float[] color = new float[]{1f, 1f, 1f};
		
		@Override
		public String toString() {
			return String.format("{start: %s, end: %s, density: %s, color: %s}", start, end, density, Arrays.toString(color));
		}
	}
	
	public static class CloudLayer {
		public float  angle   = 360.0f;
		public float  speed   = 1.0f;
		public int    height  = 255;
		public String texture = "talecraft:environments/example/clouds.png";
		
		@Override
		public String toString() {
			return String.format("{angle: %s, speed: %s, height: %s, texture: %s}", angle, speed, height, texture);
		}
	}
	
	public static class SkyLayer {
		public String  blend = "add";
		public boolean depth = false;
		public boolean cull = false;
		public String  shape = "plane";
		public String  origin = "player";
		public String  texture = "talecraft:environment/noise.png";
		public float[] offset = new float[]{0f, 0f, 0f};
		public float   scale  = 1.0f;
		public List<SkyLayerRotation> rotation = null;
		
		@Override
		public String toString() {
			return String.format("{blend: %s, depth: %s, shape: %s, origin: %s, offset: %s, scale: %s, rotation: %s}", blend, depth, shape, origin, Arrays.toString(offset), scale, rotation);
		}
		
		public void getMatrix(Matrix4f matrix, float time) {
			matrix.setIdentity();
			
			Matrix4f rot = new Matrix4f();
			for(SkyLayerRotation roti : rotation) {
				roti.getMatrix(rot, time);
				rot.mul(matrix, matrix);
			}
			
			Vector3f offsetVec = new Vector3f();
			offsetVec.x = offset[0];
			offsetVec.y = offset[1];
			offsetVec.z = offset[2];
			matrix.setTranslation(offsetVec);

			Vector3f scaleVec = new Vector3f();
			scaleVec.x = scale;
			scaleVec.y = scale;
			scaleVec.z = scale;
			scale(scaleVec, matrix, matrix);
		}
		/**
		 * Scales the source matrix and put the result in the destination matrix
		 * @param vec The vector to scale by
		 * @param src The source matrix
		 * @param dest The destination matrix, or null if a new matrix is to be created
		 * @return The scaled matrix
		 */
		private static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
			if (dest == null)
				dest = new Matrix4f();
			dest.m00 = src.m00 * vec.x;
			dest.m01 = src.m01 * vec.x;
			dest.m02 = src.m02 * vec.x;
			dest.m03 = src.m03 * vec.x;
			dest.m10 = src.m10 * vec.y;
			dest.m11 = src.m11 * vec.y;
			dest.m12 = src.m12 * vec.y;
			dest.m13 = src.m13 * vec.y;
			dest.m20 = src.m20 * vec.z;
			dest.m21 = src.m21 * vec.z;
			dest.m22 = src.m22 * vec.z;
			dest.m23 = src.m23 * vec.z;
			return dest;
		}
		/**
		 * Rotates the source matrix around the given axis the specified angle and
		 * put the result in the destination matrix.
		 * @param angle the angle, in radians.
		 * @param axis The vector representing the rotation axis. Must be normalized.
		 * @param src The matrix to rotate
		 * @param dest The matrix to put the result, or null if a new matrix is to be created
		 * @return The rotated matrix
		 */
		private static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest) {
			if (dest == null)
				dest = new Matrix4f();
			float c = (float) Math.cos(angle);
			float s = (float) Math.sin(angle);
			float oneminusc = 1.0f - c;
			float xy = axis.x*axis.y;
			float yz = axis.y*axis.z;
			float xz = axis.x*axis.z;
			float xs = axis.x*s;
			float ys = axis.y*s;
			float zs = axis.z*s;

			float f00 = axis.x*axis.x*oneminusc+c;
			float f01 = xy*oneminusc+zs;
			float f02 = xz*oneminusc-ys;
			// n[3] not used
			float f10 = xy*oneminusc-zs;
			float f11 = axis.y*axis.y*oneminusc+c;
			float f12 = yz*oneminusc+xs;
			// n[7] not used
			float f20 = xz*oneminusc+ys;
			float f21 = yz*oneminusc-xs;
			float f22 = axis.z*axis.z*oneminusc+c;

			float t00 = src.m00 * f00 + src.m10 * f01 + src.m20 * f02;
			float t01 = src.m01 * f00 + src.m11 * f01 + src.m21 * f02;
			float t02 = src.m02 * f00 + src.m12 * f01 + src.m22 * f02;
			float t03 = src.m03 * f00 + src.m13 * f01 + src.m23 * f02;
			float t10 = src.m00 * f10 + src.m10 * f11 + src.m20 * f12;
			float t11 = src.m01 * f10 + src.m11 * f11 + src.m21 * f12;
			float t12 = src.m02 * f10 + src.m12 * f11 + src.m22 * f12;
			float t13 = src.m03 * f10 + src.m13 * f11 + src.m23 * f12;
			dest.m20 = src.m00 * f20 + src.m10 * f21 + src.m20 * f22;
			dest.m21 = src.m01 * f20 + src.m11 * f21 + src.m21 * f22;
			dest.m22 = src.m02 * f20 + src.m12 * f21 + src.m22 * f22;
			dest.m23 = src.m03 * f20 + src.m13 * f21 + src.m23 * f22;
			dest.m00 = t00;
			dest.m01 = t01;
			dest.m02 = t02;
			dest.m03 = t03;
			dest.m10 = t10;
			dest.m11 = t11;
			dest.m12 = t12;
			dest.m13 = t13;
			return dest;
		}
		public static class SkyLayerRotation {
			public float[] axis = new float[]{0f, 1f, 0f};
			public float offset = 0f;
			public float speed  = 1f;
			
			@Override
			public String toString() {
				return String.format("{axis: %s, offset: %s, speed: %s}", Arrays.toString(axis), offset, speed);
			}
			
			/**
			 * @param matrix Matrix to set with the correct transform.
			 * @param time Float between 0 and 1 representing the time of day.
			 **/
			public void getMatrix(Matrix4f matrix, float time) {
				double angleDeg = (offset + time * speed) * 360.0;
				matrix.setIdentity();
				rotate((float)Math.toRadians(angleDeg), new Vector3f(axis[0], axis[1], axis[2]), matrix, matrix);
			}
		}
	}
}
