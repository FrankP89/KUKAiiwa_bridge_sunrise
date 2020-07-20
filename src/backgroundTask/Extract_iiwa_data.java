package backgroundTask;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import de.re.easymodbus.exceptions.ModbusException;
import de.re.easymodbus.server.*;
import de.re.easymodbus.modbusclient.*;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.JointEnum;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;


public class Extract_iiwa_data extends RoboticsAPICyclicBackgroundTask {
	@Inject
	private Controller kUKA_Sunrise_Cabinet_1;
	private LBR kuka_iiwa_7;
	
	private ModbusServer modbusServer;
	private ModbusClient modbusClient;
	
	private int[] robotAxesModbusMap = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};	
	
	@Override
	public void initialize() {
		// initialize your task here
		initializeCyclic(0, 500, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
		
		// Instantiate the KUKA iiwa data object and initialize the server
		Extract_iiwa_data ext_comm = new Extract_iiwa_data();
		try {
			modbusServer = ext_comm.startServer(modbusServer);
			ext_comm.startClient(modbusClient);
		} catch (UnknownHostException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	protected void runCyclic() {
		
		readRobotData(modbusClient);
		
		getLogger().info(modbusServer.holdingRegisters.toString());
		//br.readLine();
        
        modbusServer.StopListening();
		
	}		
	
	public ModbusServer startServer(ModbusServer modbusServer) throws IOException
    {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
        modbusServer.setPort(10030);
        modbusServer.Listen();
        
		return modbusServer;         
    }
	
	public ModbusClient startClient(ModbusClient modbusClient) throws IOException
	{
		modbusClient = new ModbusClient("127.0.0.1", 10030);
		modbusClient.Connect();
		
		return modbusClient;
	}

	public void readRobotData(ModbusClient modbusClient)
    {   	
		double axis1 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J1);
		double axis2 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J2);
		double axis3 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J3);
		double axis4 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J4);
		double axis5 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J5);
		double axis6 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J6);
		double axis7 = kuka_iiwa_7.getCurrentJointPosition().get(JointEnum.J7);
		
    	int[] axis1_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis1);
    	int[] axis2_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis2);
    	int[] axis3_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis3);
    	int[] axis4_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis4);
    	int[] axis5_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis5);
    	int[] axis6_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis6);
    	int[] axis7_mb = ModbusClient.ConvertFloatToTwoRegisters((float) axis7);
    	
    	try {
    		modbusClient.WriteMultipleRegisters(0, axis1_mb);
    		modbusClient.WriteMultipleRegisters(2, axis2_mb);
    		modbusClient.WriteMultipleRegisters(4, axis3_mb);
    		modbusClient.WriteMultipleRegisters(6, axis4_mb);
    		modbusClient.WriteMultipleRegisters(8, axis5_mb);
    		modbusClient.WriteMultipleRegisters(10, axis6_mb);
    		modbusClient.WriteMultipleRegisters(12, axis7_mb);
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(0, 2)));
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(2, 2)));
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(4, 2)));
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(6, 2)));
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(8, 2)));
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(10, 2)));
    		System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(12, 2)));
    	}    	
		catch (Exception e)
		{		
			System.out.println("Impossible to transport data. Modbus Error.");
			getLogger().error("Impossible to transport data. Modbus Error.");
		}	

    	
    }




}
