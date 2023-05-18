package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.*;
import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private List<River> rivers;
	private PriorityQueue<Flow> queue;
	
	public Model() {
		this.dao = new RiversDAO();
		this.rivers = dao.getAllRivers();
	}

	public List<River> getFiumi() {
		return rivers;
	}
	
	public void setFlows(River r) {
		List<Flow> flows = dao.getFlow(r);
		for(River f : rivers) {
			if(f.equals(r)) {
				f.setFlows(flows);
			}
		}
	}
	
	public LocalDate getStartDate(River r) {
		if(!r.getFlows().isEmpty()) {
			return r.getFlows().get(0).getDay();
		} else {
			return null;
		}
	}
	
	public LocalDate getEndDate(River r) {
		if(!r.getFlows().isEmpty()) {
			return r.getFlows().get(r.getFlows().size()-1).getDay();
		} else {
			return null;
		}
	}
	
	public int getNum(River r) {
		return r.getFlows().size();
	}
	
	public double media(River r) {
		double avg = 0;
		for(Flow f : r.getFlows()) {
			avg+=f.getFlow();
		}
		avg/=r.getFlows().size();
		r.setFlowAvg(avg);
		return avg;
	}
	
	public SimulationResult simulate(River r, double k) {
		this.queue = new PriorityQueue<>();
		this.queue.addAll(r.getFlows());
		
		List<Double> capacity = new ArrayList<>();
		
		double Q = k*30*converter(r.getFlowAvg());
		double C = Q/2;
		double fOutMin = converter(0.8*r.getFlowAvg());
		int numOfDay = 0;
		
		Flow flow;
		while((flow=this.queue.poll())!=null) {
			double fOut = fOutMin;
			if(Math.random()>0.95) {
				fOut = 10*fOutMin;
			}
			C+=converter(flow.getFlow());
			if(C>Q) {
				C=Q;
			}
			if(C<fOut) {
				numOfDay+=1;
				C=0;
			} else {
				C-=fOut;
			}
			
			capacity.add(C);
		}
		double CAvg=0;
		for(Double d : capacity) {
			CAvg+=d;
		}
		CAvg/=capacity.size();
		return new SimulationResult(CAvg, numOfDay);
	}

	private double converter(double flow) {
		return flow*60*60*24;
	}
}
