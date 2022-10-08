package fr.naruse.api.effect.particle;

import fr.naruse.api.async.Runner;

public abstract class ParticleEffect {

    private Runner runner;

    public ParticleEffect() {
        this.runner = new Runner() {

            @Override
            public void run() {
                ParticleEffect.this.run();
            }

        };
    }

    protected abstract void init();

    protected abstract void run();

    public abstract void kill();

    public void start(){
        this.startRunner();
    }

    protected void startRunner(){
        if(this.runner != null){
            this.runner.start();
        }
    }

    protected void killRunner(){
        if(this.runner != null){
            this.runner.setCancelled(true);
        }
    }

    protected void disableRunner(){
        this.runner = null;
    }
}
