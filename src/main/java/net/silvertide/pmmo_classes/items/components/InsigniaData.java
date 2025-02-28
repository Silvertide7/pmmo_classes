package net.silvertide.pmmo_classes.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.silvertide.pmmo_classes.data.ApplicationType;
import net.silvertide.pmmo_classes.data.InsigniaColor;
import net.silvertide.pmmo_classes.data.InsigniaRank;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record InsigniaData(String name, List<String> skills, String applicationType, Long applicationValue, int experienceCost, String rank, String color) {
    public static final Codec<InsigniaData> CODEC;
    public static final StreamCodec<FriendlyByteBuf, InsigniaData> STREAM_CODEC;

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(InsigniaData::name),
                Codec.list(Codec.STRING).fieldOf("skills").forGetter(InsigniaData::skills),
                Codec.STRING.fieldOf("application_type").forGetter(InsigniaData::applicationType),
                Codec.LONG.fieldOf("application_value").forGetter(InsigniaData::applicationValue),
                Codec.INT.fieldOf("experience_cost").forGetter(InsigniaData::experienceCost),
                Codec.STRING.fieldOf("rank").forGetter(InsigniaData::rank),
                Codec.STRING.fieldOf("color").forGetter(InsigniaData::color))
            .apply(instance, InsigniaData::new));

        STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull InsigniaData decode(@NotNull FriendlyByteBuf buf) {
                String name = buf.readUtf();
                List<String> skillsFromBuf = new ArrayList<>();
                int numSkills = buf.readVarInt();

                for(int i = 0; i < numSkills; i++) {
                    skillsFromBuf.add(buf.readUtf());
                }

                return new InsigniaData(name, skillsFromBuf, buf.readUtf(), buf.readLong(), buf.readInt(), buf.readUtf(), buf.readUtf());
            }
            @Override
            public void encode(FriendlyByteBuf buf, InsigniaData insigniaData) {
                buf.writeUtf(insigniaData.name());

                buf.writeVarInt(insigniaData.skills().size());
                for(String skill : insigniaData.skills()) {
                    buf.writeUtf(skill);
                }

                buf.writeUtf(insigniaData.applicationType());
                buf.writeLong(insigniaData.applicationValue());
                buf.writeInt(insigniaData.experienceCost());
                buf.writeUtf(insigniaData.rank());
                buf.writeUtf(insigniaData.color());
            }
        };
    }

    public ApplicationType getApplicationType() throws IllegalArgumentException {
        return ApplicationType.valueOf(applicationType().toUpperCase());
    }

    public InsigniaColor getColor() {
        try {
            return InsigniaColor.valueOf(rank().toUpperCase());
        } catch(IllegalArgumentException exception){
            return InsigniaColor.RED;
        }
    }

    public InsigniaRank getRank() {
        try {
            return InsigniaRank.valueOf(color().toUpperCase());
        } catch(IllegalArgumentException exception){
            return InsigniaRank.PLAIN;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsigniaData other = (InsigniaData) o;
        return Objects.equals(skills(), other.skills())
                && Objects.equals(name(), other.name())
                && Objects.equals(applicationType(), other.applicationType())
                && Objects.equals(applicationValue(), other.applicationValue())
                && Objects.equals(experienceCost(), other.experienceCost())
                && Objects.equals(rank(), other.rank())
                && Objects.equals(color(), other.color());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), skills(), applicationType(), applicationValue(), experienceCost(), rank(), color());
    }
}
