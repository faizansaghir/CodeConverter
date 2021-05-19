package com.faizan;

import java.util.HashMap;

public class TwoOperand {
	public HashMap<String,String> getCommandList(){
		HashMap<String,String> commandsMap=new HashMap<>();
		String[] normalStringCommands= {
			"MOV","MVI","LXI"	
		};
		String[] commandComments= {
			"Copy value at [] into []",
			"Copy value [] into []",
			"Copy value [] into [] register pair"
		};
		for(int i=0;i<normalStringCommands.length;i++) {
			commandsMap.put(normalStringCommands[i],commandComments[i]);
		}
		return commandsMap;
	}
}
