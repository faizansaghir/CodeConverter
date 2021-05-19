package com.faizan;

import java.util.HashMap;

public class OneOperand {
	public HashMap<String,String> getCommandList(){
		HashMap<String,String> commandsMap=new HashMap<>();
		String[] normalStringCommands= {
			"CMP","INX","DCR","INR","JNZ","JZ","JP","JMP","CPI","STA","LDA","ADD","SUB","JPO","ANI","JC","SBB","DAD","JNC","DCX","PUSH","POP","ADC","OUT","CALL","JM","IN"
		};
		String[] commandComments= {
			"Compare value at accumulator with value at []",
			"Increment value at [] register pair",
			"Decrement value at []",
			"Increment value at []",
			"If result is not zero then jump to []",
			"If result is zero then jump to []",
			"If result is positive then jump to []",
			"Jump to []",
			"Compare value at accumulator with value []",
			"Store value at accumulator into memory location []",
			"Load value at memory location [] into accumulator",
			"Add value at [] to accumulator",
			"Subtract value at [] from accumulator",
			"If result has odd parity then jump to []",
			"And value at accumulator with value []",
			"If carry is generated then jump to []",
			"Subtract value at [] from accumulator with borrow",
			"Add [] register pair into HL register pair",
			"If no carry is generated then jump to []",
			"Decrement [] register pair",
			"Push value at [] register pair to stack",
			"Pop value from stack to [] register pair",
			"Add [] with carry to accumulator",
			"Send value at accumulator to port with address []",
			"Call subroutine []",
			"If result is negative then jump to []",
			"Receive value from port [] at accumulator"
		};
		for(int i=0;i<normalStringCommands.length;i++) {
			commandsMap.put(normalStringCommands[i],commandComments[i]);
		}
		return commandsMap;
	}
}
