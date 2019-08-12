package talecraft.client.environment;

import net.minecraft.client.renderer.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Arrays;
import java.util.List;

public class Environment {
    public Conditions predicate = null;
    public final String lighting = null;
    public final Fog fog = null;
    public final List<CloudLayer> clouds = null;
    public final List<SkyLayer> sky = null;

    public Environment() {
    }

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
        public final float start = 1.0f;
        public final float end = 100.0f;
        public final float density = 0.1f;
        public final float[] color = new float[]{1f, 1f, 1f};

        @Override
        public String toString() {
            return String.format("{start: %s, end: %s, density: %s, color: %s}", start, end, density, Arrays.toString(color));
        }
    }

    public static class CloudLayer {
        public final float angle = 360.0f;
        public final float speed = 1.0f;
        public final int height = 255;
        public final String texture = "talecraft:environments/example/clouds.png";

        @Override
        public String toString() {
            return String.format("{angle: %s, speed: %s, height: %s, texture: %s}", angle, speed, height, texture);
        }
    }

    public static class SkyLayer {
        public final String blend = "add";
        public final boolean depth = false;
        public final boolean cull = false;
        public final String shape = "plane";
        public final String origin = "player";
        public String texture = "talecraft:environment/noise.png";
        public final float[] offset = new float[]{0f, 0f, 0f};
        public final float scale = 1.0f;
        public final List<SkyLayerRotation> rotation = null;

        @Override
        public String toString() {
            return String.format("{blend: %s, depth: %s, shape: %s, origin: %s, offset: %s, scale: %s, rotation: %s}", blend, depth, shape, origin, Arrays.toString(offset), scale, rotation);
        }

        public void getMatrix(Matrix4f matrix, float time) {
            matrix.setIdentity();

            Matrix4f rot = new Matrix4f();
            for (SkyLayerRotation roti : rotation) {
                roti.getMatrix(rot, time);
                Matrix4f.mul(matrix, rot, matrix);
            }

            Vector3f offsetVec = new Vector3f();
            offsetVec.x = offset[0];
            offsetVec.y = offset[1];
            offsetVec.z = offset[2];
            matrix.translate(offsetVec);

            Vector3f scaleVec = new Vector3f();
            scaleVec.x = scale;
            scaleVec.y = scale;
            scaleVec.z = scale;
            matrix.scale(scaleVec);
        }

        public static class SkyLayerRotation {
            public final float[] axis = new float[]{0f, 1f, 0f};
            public final float offset = 0f;
            public final float speed = 1f;

            @Override
            public String toString() {
                return String.format("{axis: %s, offset: %s, speed: %s}", Arrays.toString(axis), offset, speed);
            }

            /**
             * @param matrix Matrix to set with the correct transform.
             * @param time   Float between 0 and 1 representing the time of day.
             **/
            public void getMatrix(Matrix4f matrix, float time) {
                double angleDeg = (offset + time * speed) * 360.0;
                matrix.setIdentity();
                matrix.rotate((float) Math.toRadians(angleDeg), new Vector3f(axis[0], axis[1], axis[2]));
            }
        }
    }
}
