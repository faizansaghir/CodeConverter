package com.faizan;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getcode")
public class ConvertClass extends HttpServlet {
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException{
		res.setHeader("Access-Control-Allow-Origin", "*");
		String receivedCode=req.getParameter("code");
		MnemonicsToComments mTCObj=new MnemonicsToComments();
		String outputString=mTCObj.comment(receivedCode);
		PrintWriter out=res.getWriter();
		out.print(outputString);
	}
}
