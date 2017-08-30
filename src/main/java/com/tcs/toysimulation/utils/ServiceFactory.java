package com.tcs.toysimulation.utils;

import com.tcs.toysimulation.service.ToySimulationService;
import com.tcs.toysimulation.serviceimpl.ToySimulationServiceImpl;

public class ServiceFactory {
	
	public static ToySimulationService getToySimulationServiceInstance() {
		
		return new ToySimulationServiceImpl();
	}

}
