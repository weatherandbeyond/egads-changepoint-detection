/*
 * Copyright 2015, Yahoo Inc.
 * Copyrights licensed under the GPL License.
 * See the accompanying LICENSE file for terms.
 */

// Null model, returns prediction the same as a prediction.

package com.yahoo.egads.models.tsmm;

import com.yahoo.egads.data.TimeSeries;
import com.yahoo.egads.data.TimeSeries.Entry;

import java.util.Properties;

// modified copy of NullModel
public class InitialWindowAverageModel extends TimeSeriesAbstractModel {

    // number of timesteps at the beginning of the timeseries
    // to take the average of
    protected int numInitialTimesteps;

	private static final long serialVersionUID = 1L;
        private TimeSeries.DataSequence data = null;

    public InitialWindowAverageModel(Properties config) {
        super(config);
        if (config.getProperty("NUM_INITIAL_TIMESTEPS") == null) {
            throw new IllegalArgumentException("NUM_INITIAL_TIMESTEPS");
        }

        this.numInitialTimesteps = new Integer(config.getProperty("NUM_INITIAL_TIMESTEPS"));
    }

    public void reset() {}

    public void train(TimeSeries.DataSequence data) {
        this.data = data;
    }

    public void update(TimeSeries.DataSequence data) {
    }

    public String getModelName() {
        return "InitialWindowAverageModel";
    }
    
    public void predict(TimeSeries.DataSequence sequence) throws Exception {
        float averageInitial = 0.0f;
        for (int i = 0; i < this.numInitialTimesteps; i ++) {
            averageInitial += data.get(i).value / (float) this.numInitialTimesteps;
        }

        int n = data.size();
        for (int i = 0; i < n; i++) {
            sequence.set(i, (new Entry(data.get(i).time, averageInitial)));
            logger.info(data.get(i).time + "," + data.get(i).value + "," + data.get(i).value);
        }
    }
}
