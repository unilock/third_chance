package cc.unilock.third_chance;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class ThirdChanceConfig extends ReflectiveConfig {
	@Comment({"If an entity loses at least this percentage of their max HP in one hit, leading to their death,", "they will instead be brought to 1 HP (half a heart)."})
	@FloatRange(min = 0F, max = 1F)
	public final TrackedValue<Float> percent = value(0.75F);
}
