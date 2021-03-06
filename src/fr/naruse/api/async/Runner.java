package fr.naruse.api.async;

public abstract class Runner {

    private boolean isCancelled = false;

    public void start(){
        Runnable runnable = () -> {
            if(isCancelled){
                return;
            }

            this.start();
            this.run();
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
    }

    public abstract void run();

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
