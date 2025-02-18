package net.silvertide.pmmo_classes.items.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record InsigniaData(String skill, String applicationType, Long applicationValue, String insigniaType, String trimColor) {
    public static final Codec<InsigniaData> CODEC;
    public static final StreamCodec<FriendlyByteBuf, InsigniaData> STREAM_CODEC;

    static {
        CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("skill").forGetter(InsigniaData::skill),
                Codec.STRING.fieldOf("application_type").forGetter(InsigniaData::applicationType),
                Codec.LONG.fieldOf("application_value").forGetter(InsigniaData::applicationValue),
                Codec.STRING.fieldOf("book_color").forGetter(InsigniaData::insigniaType),
                Codec.STRING.fieldOf("trim_color").forGetter(InsigniaData::trimColor))
            .apply(instance, InsigniaData::new));

        STREAM_CODEC = new StreamCodec<>() {
            @Override
            public @NotNull InsigniaData decode(@NotNull FriendlyByteBuf buf) {
                return new InsigniaData(buf.readUtf(), buf.readUtf(), buf.readLong(), buf.readUtf(), buf.readUtf());
            }
            @Override
            public void encode(FriendlyByteBuf buf, InsigniaData insigniaData) {
                buf.writeUtf(insigniaData.skill());
                buf.writeUtf(insigniaData.applicationType());
                buf.writeLong(insigniaData.applicationValue());
                buf.writeUtf(insigniaData.insigniaType());
                buf.writeUtf(insigniaData.trimColor());
            }
        };
    }

//    public ApplicationType getApplicationType() throws IllegalArgumentException {
//        return ApplicationType.valueOf(applicationType().toUpperCase());
//    }
//
//    public String getSkillName() {
//        return Component.translatable("pmmo." + skill()).getString();
//    }
//
//    public SkillBookColor getColor() {
//        try {
//            return SkillBookColor.valueOf(insigniaType().toUpperCase());
//        } catch(IllegalArgumentException exception){
//            return SkillBookColor.BLUE;
//        }
//    }
//
//    public SkillBookTrim getTrim() {
//        try {
//            return SkillBookTrim.valueOf(trimColor().toUpperCase());
//        } catch(IllegalArgumentException exception){
//            return SkillBookTrim.PLAIN;
//        }
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InsigniaData other = (InsigniaData) o;
//        return Objects.equals(skill(), other.skill())
//                && Objects.equals(applicationType(), other.applicationType())
//                && Objects.equals(applicationValue(), other.applicationValue())
//                && Objects.equals(insigniaType(), other.insigniaType())
//                && Objects.equals(trimColor(), other.trimColor());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(skill(), applicationType(), applicationValue(), insigniaType(), trimColor());
//    }
}
