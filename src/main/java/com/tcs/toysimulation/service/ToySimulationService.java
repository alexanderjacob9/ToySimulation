package com.tcs.toysimulation.service;

import java.util.List;

import com.tcs.toysimulation.common.ToySimulationRecord;
import com.tcs.toysimulation.exception.ToySimulationException;

/* ToySimulationService Interface */

public interface ToySimulationService {
	
	/* Generates Sample Data for a given List of Countries */
	public List<ToySimulationRecord> createSampleData() throws ToySimulationException;
	/* Writes the Generated resultant list to a output file in the current Working directory*/
	public void writeDataToOutputFile(List<ToySimulationRecord> createRandomRecord) throws ToySimulationException;

}
