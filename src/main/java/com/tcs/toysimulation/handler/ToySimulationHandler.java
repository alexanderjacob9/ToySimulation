package com.tcs.toysimulation.handler;

import java.util.List;

import com.tcs.toysimulation.common.ToySimulationRecord;
import com.tcs.toysimulation.exception.ToySimulationException;
import com.tcs.toysimulation.service.ToySimulationService;
import com.tcs.toysimulation.utils.ServiceFactory;

public class ToySimulationHandler {

	/**
	 * Process start
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			ToySimulationHandler toySimulation = new ToySimulationHandler();
			toySimulation.process();
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	private void process() throws ToySimulationException{
		// Generate Random records across different locations
		ToySimulationService toySimulationService = ServiceFactory.getToySimulationServiceInstance();
		List<ToySimulationRecord> createRandomRecord = toySimulationService.createSampleData();
		
		toySimulationService.writeDataToOutputFile(createRandomRecord);
		
	}

}
