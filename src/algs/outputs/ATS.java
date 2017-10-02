package algs.outputs;

import utilities.ICloneable;

/**
 * Created by gvoiron on 18/06/17.
 * Time : 20:44
 */
public final class ATS implements ICloneable<ATS> {

    private final MTS mts;
    private final CTS cts;

    public ATS(MTS mts, CTS cts) {
        this.mts = mts;
        this.cts = cts;
    }

    public MTS getMTS() {
        return mts;
    }

    public CTS getCTS() {
        return cts;
    }

    @Override
    public ATS clone() {
        return new ATS(mts.clone(), cts.clone());
    }

}
