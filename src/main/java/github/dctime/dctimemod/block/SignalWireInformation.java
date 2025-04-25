package github.dctime.dctimemod.block;

public class SignalWireInformation {
    private int signalValue;
    public SignalWireInformation(int signalValue) {
        this.signalValue = signalValue;
    }
    public void setSignalValue(int signalValue) {
        this.signalValue = signalValue;
    }

    public int getSignalValue() {
        return this.signalValue;
    }
}
