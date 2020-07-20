package communication;
import de.re.easymodbus.modbusclient.*;
 
public class Client {
	public static void main(String[] args)
	{
		ModbusClient modbusClient = new ModbusClient("127.0.0.1",10030);
		try
		{
			modbusClient.Connect();
			/*
			 * modbusClient.WriteSingleCoil(0, true); modbusClient.WriteSingleRegister(0,
			 * 1234); modbusClient.WriteMultipleRegisters(11,
			 * ModbusClient.ConvertFloatToTwoRegisters((float) 154.56));
			 * System.out.println(modbusClient.ReadCoils(0, 1)[0]);
			 */
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
		}	
	}
}