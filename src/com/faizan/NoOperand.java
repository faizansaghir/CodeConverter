package com.faizan;

import java.util.HashMap;

public class NoOperand {
	public HashMap<String,String> getCommandList(){
		HashMap<String,String> commandsMap=new HashMap<>();
		String[] normalStringCommands= {
			"STC","CMC","RAL","RAR","HLT","RET"
		};
		String[] commandComments= {
			"Set carry flag",
			"Compliment carry flag",
			"Rotate accumulator left with carry",
			"Rotate accumulator right with carry",
			"Stop Execution",
			"Return execution to calling routine"
		};
		for(int i=0;i<normalStringCommands.length;i++) {
			commandsMap.put(normalStringCommands[i],commandComments[i]);
		}
		return commandsMap;
	}
}
