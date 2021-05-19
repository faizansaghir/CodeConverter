package com.faizan;

import java.util.ArrayList;
import java.util.HashMap;

public class MnemonicsToComments {
	private String getCorrectOperand(String operand) {
		switch(operand) {
		case "A":
			return "accumulator";
		case "B":
			return "register B";
		case "C":
			return "register C";
		case "D":
			return "register D";
		case "E":
			return "register E";
		case "H":
			return "register H";
		case "L":
			return "register L";
		case "M":
			return "memory";
		case "SP":
			return "stack pointer";
		default:
			try{
				Integer.parseInt(operand);
				return operand+"H";
			}
			catch(Exception e) {
				return operand;
			}
		}
	}
	
	private String getCorrectOperandPair(String operand) {
		switch(operand) {
		case "B":
			return "BC";
		case "D":
			return "DE";
		case "H":
			return "HL";
		case "SP":
			return "stack pointer";
		default:
			return operand;
		}
	}
	
	private String[] getTwoOperands(String mnemonicString,String toModify) {
		int startingIndexOfOperand=mnemonicString.indexOf(' ')+1;
		int endingIndexOfOperand=mnemonicString.indexOf(',');
		String firstOperand;
		try{
			if(endingIndexOfOperand==-1){
				endingIndexOfOperand=mnemonicString.length();
			}
			firstOperand=mnemonicString.substring(startingIndexOfOperand, endingIndexOfOperand);
			if(firstOperand.trim().equals(""))
				throw new StringIndexOutOfBoundsException();
		}
		catch(StringIndexOutOfBoundsException e){
			return new String[]{"*****Required 2 operand. Found 0*****"};
			
		}
		startingIndexOfOperand=endingIndexOfOperand+1;
		String secondOperand;
		try{
			secondOperand=mnemonicString.substring(startingIndexOfOperand);
			if(secondOperand.trim().equals(""))
				throw new StringIndexOutOfBoundsException();
		}
		catch(StringIndexOutOfBoundsException e){
			return new String[]{"*****Required 2 operand. Found 1*****"};
		}
		secondOperand=getCorrectOperand(secondOperand);
		if(!toModify.contains("register pair")) {
			firstOperand=getCorrectOperand(firstOperand);
		}
		else {
			firstOperand=getCorrectOperandPair(firstOperand);
		}
		String[] operands= {
				secondOperand,firstOperand
		};
		return operands;
	}
	private String getOneOperand(String mnemonicString,String toModify) {
		try{
			int indexOfOperand=mnemonicString.indexOf(' ');
			if(indexOfOperand==-1){
				throw new StringIndexOutOfBoundsException();
			}
			String operand=mnemonicString.substring(indexOfOperand).trim();
			if(operand.equals(""))
				throw new StringIndexOutOfBoundsException();
			if(!toModify.contains("register pair")) {
				operand=getCorrectOperand(operand);
			}
			else {
				operand=getCorrectOperandPair(operand);
			}
			return operand;
		}
		catch(StringIndexOutOfBoundsException e){
			return "*****Required 1 operand. Found 0*****";
		}
	}
	
	private String commentMnemonic(String mnemonicString,ArrayList<HashMap<String,String>> commandsMappingList) {
		if(mnemonicString.trim().equals(""))
			return "";
		int noOfOperand=0;
		mnemonicString=mnemonicString.toUpperCase();
		int commandEndIndex=mnemonicString.indexOf(" ");
		if(commandEndIndex==-1)
			commandEndIndex=mnemonicString.length();
		String command=mnemonicString.substring(0,commandEndIndex);
		//System.out.println(command);
		for(HashMap<String,String> commandsMapping:commandsMappingList) {
			if(commandsMapping.get(command)!=null) {
				String commandToModify=commandsMapping.get(command);
				//System.out.println(commandToModify);
				if(noOfOperand==1) {
					String operand=getOneOperand(mnemonicString,commandToModify);
					if(operand.startsWith("*"))
						return operand;
					if(operand.compareTo("stack pointer")==0) {
						commandToModify=commandToModify.replace("register pair", " ");
					}
					commandToModify=commandToModify.replaceFirst("\\[]", operand);
				}
				else if(noOfOperand==2) {
					String[] operands=getTwoOperands(mnemonicString,commandToModify);
					if (operands.length==1){
						return operands[0];
					}		
					for(String operand:operands) {
						commandToModify=commandToModify.replaceFirst("\\[]", operand);
						if(operand.compareTo("stack pointer")==0) {
							commandToModify=commandToModify.replace("register pair", " ");
						}
					}
				}
				return commandToModify;
			}
			noOfOperand++;
		}
		return "*****Command Not Found On Our List*****";
	}
	
	public String comment(String mnemonicsString) {
		String commentString="";
		ArrayList<HashMap<String,String>> commandsList=getCommandsList();
		int fromIndex=0;
		int mnemonicEndIndex=0;
		do{
			if(mnemonicsString.indexOf(":",fromIndex)!=-1){
				int nextLineIndex=mnemonicsString.indexOf("\n",fromIndex);
				int colonIndex=mnemonicsString.indexOf(":",fromIndex);
				if(nextLineIndex<colonIndex){
					mnemonicEndIndex=nextLineIndex;
				}
				else{
					fromIndex=colonIndex+1;
					mnemonicEndIndex=nextLineIndex;
				}
			}
			else{
				mnemonicEndIndex=mnemonicsString.indexOf("\n",fromIndex);
			}
			if(mnemonicEndIndex==-1) {
				String mnemonic=mnemonicsString.substring(fromIndex);
				mnemonic=mnemonic.trim();
				//System.out.println(mnemonic);
				String subCommentString=commentMnemonic(mnemonic,commandsList);
				commentString+=subCommentString;
				break;
			}
			String mnemonic=mnemonicsString.substring(fromIndex, mnemonicEndIndex);
			mnemonic=mnemonic.trim();
			//System.out.println(mnemonic);
			String subCommentString=commentMnemonic(mnemonic,commandsList);
			commentString+=subCommentString+"\n";
			fromIndex=mnemonicEndIndex+1;
		}
		while(true);
		return commentString;
	}
	
	private ArrayList<HashMap<String,String>> getCommandsList(){
		NoOperand nOObj=new NoOperand();
		OneOperand oOObj=new OneOperand();
		TwoOperand tOObj=new TwoOperand();
		HashMap<String,String> commandsNoOperandsMapping=nOObj.getCommandList();
		HashMap<String,String> commandsOneOperandMapping=oOObj.getCommandList();
		HashMap<String,String> commandsTwoOperandMapping=tOObj.getCommandList();
		ArrayList<HashMap<String,String>> commandsList=new ArrayList<>();
		commandsList.add(commandsNoOperandsMapping);
		commandsList.add(commandsOneOperandMapping);
		commandsList.add(commandsTwoOperandMapping);
		return commandsList;
	}
}
