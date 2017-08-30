package com.tcs.toysimulation.serviceimpl;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tcs.toysimulation.common.constants;
import com.tcs.toysimulation.exception.ToySimulationException;
import com.tcs.toysimulation.common.ToySimulationRecord;
import com.tcs.toysimulation.service.ToySimulationService;

public class ToySimulationServiceImpl implements ToySimulationService {

	

	@Override
	public void writeDataToOutputFile(List<ToySimulationRecord> generatedRecord) throws ToySimulationException{
		FileWriter writer = null;
		try {
			 writer = new FileWriter("output_ToySimulation.txt");
			for (ToySimulationRecord rec : generatedRecord) {
				String formattedRec = formatRecord(rec);
				writer.write(formattedRec);
				writer.write(System.lineSeparator());
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private String formatRecord(ToySimulationRecord rec) {
		StringBuilder recordFormat = new StringBuilder();
		recordFormat.append(rec.getLocation()).append("|").append(rec.getPosition()).append("|").append(rec.getLocaltime()).append("|").append(rec.getConditions()).append("|").append(rec.getTemperature()).append("|").append(rec.getPressure()).append("|").append(rec.getHumidity());
		return recordFormat.toString();
	}

	@Override
	public List<ToySimulationRecord> createSampleData()  throws ToySimulationException{
		List<ToySimulationRecord> toySimulationRecordList = new ArrayList<ToySimulationRecord>();
		Map<String,String> locPos = new HashMap<String,String>();
		locPos = fetchLocationPosition();
		Iterator it = locPos.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry locPosPair = (Map.Entry)it.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	       String isoDate = generateIsoTime(locPosPair.getValue());
	       String condition = generateCondition(locPosPair.getKey());
	       double temperature = generateRandomTemp(condition);
	       double pressure = calculatePressure(locPosPair.getValue(),temperature);
	       int humidity = calculateHumidity(temperature,pressure);
	       
	       ToySimulationRecord tsRecord = new ToySimulationRecord();
	       tsRecord.setLocation(locPosPair.getKey().toString());
	       tsRecord.setPosition(locPosPair.getValue().toString());
	       tsRecord.setLocaltime(isoDate);
	       tsRecord.setConditions(condition);
	       tsRecord.setTemperature(temperature);
	       tsRecord.setPressure(pressure);
	       tsRecord.setHumidity(humidity);
	       toySimulationRecordList.add(tsRecord);
	    }
	    return toySimulationRecordList;
	}
		
	private int calculateHumidity(double temperature, double pressure) {
		//Absolute humidity (AH)  AH, kg/m³ = Pw  ⁄  (Rw × T)
		// Rw is Gas constant which is a constant
		int humidity = (int) ((pressure/((temperature + 273.15) * constants.GAS_CONSTANT)) * 100);
		return humidity;
	}

	private double calculatePressure(Object position, double temperature) {
		//hypsometric formula to calculate pressure
		//p = sealevelPressure_po(1-(0.0065h)/T+0.0065h+273.15)^5.257
		String[] posSplit = position.toString().split(",");
		double altitude = Double.parseDouble(posSplit[2]);
		double atmPres = Math.round(constants.PO * Math.pow(
				(1 - ((0.0065 * altitude) / (temperature + (0.0065 * altitude) + constants.INTOCELSIUS))),
				constants.PRESSURE_CALC) * 100)/100;
		
		return atmPres;
	}

	private double generateRandomTemp(String condition) {
		Random rand = new Random();
		int temp;
		if (condition.toString().equalsIgnoreCase("Summer")) {
			temp = rand.nextInt(constants.MAX_SUMMNER) + constants.MIN_SUMMER;
		} else if (condition.toString().equalsIgnoreCase("Winter")) {
			temp = rand.nextInt((constants.MAX_WINTER - constants.MIN_WINTER) + 1) + constants.MIN_WINTER;
		}else {
			temp = rand.nextInt(constants.MAX_MONSOON) + constants.MIN_MONSOON;
		}
		return temp;
	}

	private String generateCondition(Object cntry) {
		Date date = new Date();
		String country = cntry.toString();
		if (country.equalsIgnoreCase("IND")) {
			return constants.IND_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("AUS")){
			return constants.AUS_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("UK")){
			return constants.UK_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("US")){
			return constants.US_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("GER")){
			return constants.GER_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("SWD")){
			return constants.SWD_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("SA")){
			return constants.SA_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("NZ")){
			return constants.NZ_SEASONS[date.getMonth()];
		}else if (country.equalsIgnoreCase("BEL")){
			return constants.BEL_SEASONS[date.getMonth()];
		}else {
			return constants.CAN_SEASONS[date.getMonth()];
		}

	}


	private String generateIsoTime(Object position) {
		int timeInSec = 0;
		String[] posSplit = position.toString().split(",");
		double longitude = Double.parseDouble(posSplit[1]);
		timeInSec = (int) ((longitude - constants.LONGITUDE_IND) * constants.MIN_CONVERSION * 60);
		Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
		calendar.add(Calendar.SECOND, timeInSec);
		Date date = calendar.getTime();
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date);	
		return formattedDate;
	}	

	private Map<String,String> fetchLocationPosition () {
		Map<String,String> map = new HashMap<String,String>();
		map.put(constants.IND, constants.POS_IND) ;
		map.put(constants.AUS, constants.POS_AUS) ;
		map.put(constants.UK, constants.POS_UK) ;
		map.put(constants.US, constants.POS_US) ;
		map.put(constants.GER, constants.POS_GER) ;
		map.put(constants.SWD, constants.POS_SWD) ;
		map.put(constants.SA, constants.POS_SA) ;
		map.put(constants.NZ, constants.POS_NZ) ;
		map.put(constants.BEL, constants.POS_BEL) ;
		map.put(constants.CAN, constants.POS_CAN) ;
		return map;
	}
	

}
