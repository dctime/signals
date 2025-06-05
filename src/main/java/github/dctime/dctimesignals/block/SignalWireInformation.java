package github.dctime.dctimesignals.block;

import org.jetbrains.annotations.Nullable;

public class SignalWireInformation {
    private int signalValue;
    private boolean noSignal;
    public SignalWireInformation() {
        signalValue = 0;
        noSignal = true;
    }
    public SignalWireInformation(int signalValue) {
        this.signalValue = signalValue;
        noSignal = false;
    }

    public void setNoSignal() {
        this.noSignal = true;
    }

    public void setSignalValue(int signalValue) {
        this.signalValue = signalValue;
        this.noSignal = false;
    }

    @Nullable
    public Integer getSignalValue() {
        if (this.noSignal) {
            return null;
        }
        return this.signalValue;
    }
}
