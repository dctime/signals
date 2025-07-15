package github.dctime.dctimesignals.particle;

import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class GroundPenetratingSignalEmitterParticle extends TextureSheetParticle {

    SpriteSet spriteSet;
    float initXSpeed;
    float initYSpeed;
    float initZSpeed;
    protected GroundPenetratingSignalEmitterParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.setSprite(spriteSet.get(1, 1));
        float speedFactor = 3;
        initXSpeed = random.nextFloat()*speedFactor*(random.nextBoolean() ? 1 : -1);
        initYSpeed = random.nextFloat()*speedFactor*(random.nextBoolean() ? 1 : -1);
        initZSpeed = random.nextFloat()*speedFactor*(random.nextBoolean() ? 1 : -1);
        this.hasPhysics = false;
        this.scale(3);
    }

    @Override
    public void tick() {
        super.tick();
        super.setParticleSpeed(initXSpeed/this.getLifetime(), initYSpeed/this.lifetime, initZSpeed/lifetime);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
